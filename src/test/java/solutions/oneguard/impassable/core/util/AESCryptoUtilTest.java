package solutions.oneguard.impassable.core.util;

import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class AESCryptoUtilTest {
    private Key secret;
    private byte[] raw;

    @Before
    public void setUp() throws Exception {
        secret = AESCryptoUtil.generateKey();

        Random random = new Random();
        raw = new byte[1024];
        random.nextBytes(raw);
    }

    @Test
    public void encryptAndDecrypt() throws Exception {
        byte[] encrypted = AESCryptoUtil.encrypt(secret, raw);
        byte[] decrypted = AESCryptoUtil.decrypt(secret, encrypted);

        assertArrayEquals(raw, decrypted);
    }
}
