package solutions.oneguard.impassable.core.storage.secure;

import org.junit.Before;
import org.junit.Test;
import solutions.oneguard.impassable.core.storage.TestSerializable;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;
import solutions.oneguard.impassable.core.util.AESCryptoUtil;
import solutions.oneguard.impassable.core.util.RSACryptoUtil;

import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class SecureAsymmetricStorageTest {
    private SecureAsymmetricStorage<TestSerializable> storage;

    @Before
    public void setUp() throws Exception {
        storage = new SecureAsymmetricStorage<>(TestSerializable.class, new InMemoryByteArrayStorage());
    }

    @Test
    public void storeAndRetrieve() throws Exception {
        KeyPair keyPair = RSACryptoUtil.generateKey();
        UUID id = UUID.randomUUID();
        TestSerializable testObject = TestSerializable.generate();

        storage.store(id, testObject, keyPair.getPublic());

        assertEquals(testObject, storage.retrieve(id, keyPair.getPrivate()));
    }
}
