package solutions.oneguard.impassable.core.util;

import org.uncommons.maths.random.AESCounterRNG;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

public class CryptoUtil {
    private static Random random;

    static {
        try {
            random = new AESCounterRNG();
        } catch (GeneralSecurityException ignored) {}
    }

    public static Key generateKey(String cipher, int keyLength) throws NoSuchAlgorithmException {
        byte[] key = new byte[keyLength];
        random.nextBytes(key);

        return new SecretKeySpec(key, cipher);
    }

    public static byte[] encrypt(String cipherDefinition, Key secret, byte[] initVector, byte[] raw) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(cipherDefinition, secret, new IvParameterSpec(initVector), raw);
    }

    public static byte[] encrypt(String cipherDefinition, Key secret, AlgorithmParameterSpec parameterSpec, byte[] raw) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherDefinition);
        if (parameterSpec == null) {
            cipher.init(Cipher.ENCRYPT_MODE, secret);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secret, parameterSpec);
        }

        return cipher.doFinal(raw);
    }

    public static byte[] decrypt(String cipherDefinition, Key secret, byte[] initVector, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(cipherDefinition, secret, new IvParameterSpec(initVector), encrypted);
    }

    public static byte[] decrypt(String cipherDefinition, Key secret, AlgorithmParameterSpec parameterSpec, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherDefinition);
        if (parameterSpec == null) {
            cipher.init(Cipher.DECRYPT_MODE, secret);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secret, parameterSpec);
        }

        return cipher.doFinal(encrypted);
    }
}
