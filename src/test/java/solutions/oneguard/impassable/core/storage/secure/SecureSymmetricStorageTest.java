package solutions.oneguard.impassable.core.storage.secure;

import org.junit.Before;
import org.junit.Test;
import solutions.oneguard.impassable.core.storage.TestSerializable;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;
import solutions.oneguard.impassable.core.util.AESCryptoUtil;

import java.security.Key;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SecureSymmetricStorageTest {
    private SecureSymmetricStorage<TestSerializable> storage;

    @Before
    public void setUp() throws Exception {
        storage = new SecureSymmetricStorage<>(TestSerializable.class, new InMemoryByteArrayStorage());
    }

    @Test
    public void storeAndRetrieve() throws Exception {
        Key key = AESCryptoUtil.generateKey();
        UUID id = UUID.randomUUID();
        TestSerializable testObject = TestSerializable.generate();

        storage.store(id, testObject, key);

        assertEquals(testObject, storage.retrieve(id, key));
    }

    @Test
    public void retrieveNotStored() throws Exception {
        Key key = AESCryptoUtil.generateKey();
        UUID id = UUID.randomUUID();

        assertNull(storage.retrieve(id, key));
    }
}