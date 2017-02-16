package solutions.oneguard.impassable.core.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

public class RSACryptoUtil {
    private static final String cipherDescription = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String cipher = "RSA";
    private static final int defaultKeySize = 4096;

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

    public static byte[] encrypt(PublicKey secret, byte[] raw) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            return CryptoUtil.encrypt(cipherDescription, secret, (AlgorithmParameterSpec) null, raw);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException|InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(PrivateKey secret, byte[] encrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        try {
            return CryptoUtil.decrypt(cipherDescription, secret, (AlgorithmParameterSpec) null, encrypted);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException|InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
