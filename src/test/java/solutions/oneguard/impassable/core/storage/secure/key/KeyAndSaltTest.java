package solutions.oneguard.impassable.core.storage.secure.key;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class KeyAndSaltTest {
    @Test
    public void getId() throws Exception {
        KeyAndSalt keyAndSalt = new KeyAndSalt(new byte[0], new byte[0]);

        assertNotNull(keyAndSalt.getId());
    }

    @Test
    public void getSecret() throws Exception {
        byte[] secret = new byte[16];
        new Random().nextBytes(secret);
        KeyAndSalt keyAndSalt = new KeyAndSalt(secret, new byte[0]);

        assertArrayEquals(secret, keyAndSalt.getSecret());
    }

    @Test
    public void getSalt() throws Exception {
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        KeyAndSalt keyAndSalt = new KeyAndSalt(new byte[0], salt);

        assertArrayEquals(salt, keyAndSalt.getSalt());
    }
}
