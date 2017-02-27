package solutions.oneguard.impassable.core.storage.secure;

import org.junit.Before;
import org.junit.Test;
import solutions.oneguard.impassable.core.storage.TestSerializable;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;
import solutions.oneguard.impassable.core.storage.secure.key.KeyAndSalt;
import solutions.oneguard.impassable.core.util.CryptoUtil;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SecureSymmetricStorageTest {
    private SecureSymmetricStorage<TestSerializable> storage;
    private UUID id;
    private byte[] key;
    private byte[] salt;

    @Before
    public void setUp() throws Exception {
        storage = new SecureSymmetricStorage<>(TestSerializable.class, new InMemoryByteArrayStorage());
        key = CryptoUtil.generateKey();
        salt = CryptoUtil.generateSalt();
        id = UUID.randomUUID();
    }

    @Test
    public void storeAndRetrieve() throws Exception {
        TestSerializable testObject = TestSerializable.generate();

        storage.store(id, testObject, key, salt);

        assertEquals(testObject, storage.retrieve(id, key, salt));
    }

    @Test
    public void retrieveNotStored() throws Exception {
        assertNull(storage.retrieve(id, key, salt));
    }
}