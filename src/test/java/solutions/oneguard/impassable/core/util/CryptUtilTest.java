package solutions.oneguard.impassable.core.util;

import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.*;

public class CryptUtilTest {
    @Test
    public void generateKey() throws Exception {
        byte[] key = CryptUtil.generateKey();

        assertEquals(32, key.length); // 256 bits
    }

    @Test
    public void generateSalt() throws Exception {
        byte[] salt = CryptUtil.generateSalt();

        assertNotEquals(0, salt.length);
    }

    @Test
    public void generateKeyPair() throws Exception {
        KeyPair keyPair = CryptUtil.generateKeyPair();

        assertNotNull(keyPair);
    }
}
