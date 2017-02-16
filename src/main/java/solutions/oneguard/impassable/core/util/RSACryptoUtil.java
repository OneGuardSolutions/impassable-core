package solutions.oneguard.impassable.core.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;

public class RSACryptoUtil {
    private static final String cipherDescription = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String cipher = "RSA";
    private static final int defaultKeySize = 4096;
    private static final int digestLength = 256; // SHA-256

    public static final int INT_SIZE = Integer.SIZE / Byte.SIZE;

    public static KeyPair generateKey() {
        final int maxKeyLength;
        try {
            maxKeyLength = Cipher.getMaxAllowedKeyLength(cipherDescription);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return doGenerateKey(maxKeyLength < defaultKeySize ? maxKeyLength : defaultKeySize);
    }

    public static KeyPair generateKey(int keyLength) {
        final int maxKeyLength;
        try {
            maxKeyLength = Cipher.getMaxAllowedKeyLength(cipherDescription);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (maxKeyLength < keyLength) {
            throw new IllegalArgumentException("key length too large");
        }

        return doGenerateKey(keyLength);
    }

    private static KeyPair doGenerateKey(int keyLength) {
        final KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(cipher);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(keyLength);

        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(PublicKey secret, byte[] raw) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        return isMessageTooLong(secret, raw) ? encapsulatedEncryption(secret, raw) : directEncryption(secret, raw);
    }

    public static byte[] decrypt(PrivateKey secret, byte[] encrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            return CryptoUtil.decrypt(cipherDescription, secret, (AlgorithmParameterSpec) null, encrypted);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            return encapsulatedDecryption(secret, encrypted);
        }
    }

    private static byte[] directEncryption(PublicKey secret, byte[] raw) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        try {
            return CryptoUtil.encrypt(cipherDescription, secret, (AlgorithmParameterSpec) null, raw);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] encapsulatedEncryption(PublicKey secret, byte[] raw) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Key key = AESCryptoUtil.generateKey();

        byte[] encryptedKey = directEncryption(secret, key.getEncoded());
        byte[] encryptedMessage = AESCryptoUtil.encrypt(key, raw);

        return ByteBuffer.allocate(INT_SIZE + encryptedKey.length + encryptedMessage.length)
            .putInt(encryptedKey.length)
            .put(encryptedKey)
            .put(encryptedMessage)
            .array()
        ;
    }

    private static byte[] encapsulatedDecryption(PrivateKey secret, byte[] encrypted) throws InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException {
        int keyLength = 0;
        for (int i = INT_SIZE - 1; i > 0; i--) {
            keyLength |= encrypted[i] << i*4;
        }

        byte[] encryptedKey = new byte[keyLength];
        System.arraycopy(encrypted, INT_SIZE, encryptedKey, 0, encryptedKey.length);

        Key key = new SecretKeySpec(decrypt(secret, encryptedKey), "AES");

        byte[] encryptedMessage = new byte[encrypted.length - encryptedKey.length - INT_SIZE];
        System.arraycopy(encrypted, INT_SIZE + encryptedKey.length, encryptedMessage, 0, encryptedMessage.length);

        return AESCryptoUtil.decrypt(key, encryptedMessage);
    }

    private static boolean isMessageTooLong(PublicKey secret, byte[] raw) {
        int keyLength = ((RSAPublicKey) secret).getModulus().bitLength();
        int maxMsgLength = keyLength / 8 - 2 * ((int) Math.ceil(digestLength / 8.0)) - 2;

        return maxMsgLength < raw.length;
    }
}
