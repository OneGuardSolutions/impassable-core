package solutions.oneguard.impassable.core.util;

import org.junit.Test;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RSACryptoUtilTest {
    @Test
    public void generateKey() throws Exception {
        int keySize = 4096;
        KeyPair keyPair = RSACryptoUtil.generateKey(keySize);

        assertEquals(keySize, ((RSAPrivateKey) keyPair.getPrivate()).getModulus().bitLength());
        assertEquals(keySize, ((RSAPublicKey) keyPair.getPublic()).getModulus().bitLength());
    }

    @Test
    public void encrypt() throws Exception {
        KeyPair keyPair = RSACryptoUtil.generateKey();

        byte[] raw = new byte[20];
        Random random = new Random();
        random.nextBytes(raw);

        byte[] encrypted = RSACryptoUtil.encrypt(keyPair.getPublic(), raw);
        byte[] decrypted = RSACryptoUtil.decrypt(keyPair.getPrivate(), encrypted);

        assertArrayEquals(raw, decrypted);
    }

    @Test
    public void encryptEncapsulated() throws Exception {
        KeyPair keyPair = RSACryptoUtil.generateKey();

        byte[] raw = new byte[1024];
        Random random = new Random();
        random.nextBytes(raw);

        byte[] encrypted = RSACryptoUtil.encrypt(keyPair.getPublic(), raw);
        byte[] decrypted = RSACryptoUtil.decrypt(keyPair.getPrivate(), encrypted);

        assertArrayEquals(raw, decrypted);
    }
}
