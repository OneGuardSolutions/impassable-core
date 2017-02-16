package solutions.oneguard.impassable.core.util;

import org.uncommons.maths.random.AESCounterRNG;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Random;

public class AESCryptoUtil {
    private static final String cipherDescription = "AES/CBC/PKCS5PADDING";
    private static final String cipher = "AES";
    private static final int ivLength = 16;

    private static Random random;

    static {
        try {
            random = new AESCounterRNG();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static Key generateKey() {
        try {
            return CryptoUtil.generateKey(cipher, getKeyLength());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getKeyLength() {
        final int keyLength;
        try {
            keyLength = Cipher.getMaxAllowedKeyLength(cipherDescription);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return keyLength > 32 ? 32 : keyLength; // max 256 bits
    }

    public static byte[] encrypt(Key secret, byte[] raw) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] initVector = new byte[ivLength];
        random.nextBytes(initVector);

        byte[] encrypted;
        try {
            encrypted = CryptoUtil.encrypt(cipherDescription, secret, initVector, raw);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return ByteBuffer.allocate(initVector.length + encrypted.length)
            .put(initVector)
            .put(encrypted)
            .array()
        ;
    }

    public static byte[] decrypt(Key secret, byte[] encrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] initVector = new byte[ivLength];
        System.arraycopy(encrypted, 0, initVector, 0, initVector.length);

        byte[] encryptedBytes = new byte[encrypted.length - initVector.length];
        System.arraycopy(encrypted, initVector.length, encryptedBytes, 0, encrypted.length - initVector.length);

        try {
            return CryptoUtil.decrypt(cipherDescription, secret, initVector, encryptedBytes);
        } catch (NoSuchPaddingException|NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
