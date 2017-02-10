package solutions.oneguard.impassable.core.util;

import org.junit.Before;
import org.junit.Test;
import org.uncommons.maths.random.AESCounterRNG;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class CryptoUtilTest {
    private static final String cipherDescription = "AES/CBC/PKCS5PADDING";
    private static final String cipher = "AES";

    private Key secret;
    private byte[] raw;
    private byte[] initVector;

    @Before
    public void setUp() throws Exception {
        Random random = new AESCounterRNG();

        int keyLength = Cipher.getMaxAllowedKeyLength(cipherDescription);
        secret = CryptoUtil.generateKey(cipher, keyLength > 32 ? 32 : keyLength); // max 256 bits

        initVector = new byte[16];
        random.nextBytes(initVector);

        random = new Random();
        raw = new byte[1024];
        random.nextBytes(raw);
    }

    @Test
    public void encryptAndDecrypt() throws Exception {
        byte[] encrypted = CryptoUtil.encrypt(cipherDescription, secret, initVector, raw);
        byte[] decrypted = CryptoUtil.decrypt(cipherDescription, secret, initVector, encrypted);

        assertArrayEquals(raw, decrypted);
    }
}
