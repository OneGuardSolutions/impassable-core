package solutions.oneguard.impassable.core.util;

import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.*;

public class CryptoUtilTest {
    @Test
    public void generateKey() throws Exception {
        byte[] key = CryptoUtil.generateKey();

        assertEquals(32, key.length); // 256 bits
    }

    @Test
    public void generateSalt() throws Exception {
        byte[] salt = CryptoUtil.generateSalt();

        assertNotEquals(0, salt.length);
    }

    @Test
    public void generateKeyPair() throws Exception {
        KeyPair keyPair = CryptoUtil.generateKeyPair();

        assertNotNull(keyPair);
    }
}
