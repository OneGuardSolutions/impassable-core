package solutions.oneguard.impassable.core.storage.secure.resource;

import org.junit.Test;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;
import solutions.oneguard.impassable.core.util.AESCryptoUtil;

import java.security.Key;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SecureResourceStorageTest {
    @Test
    public void constructor() throws Exception {
        SecureResourceStorage storage = new SecureResourceStorage(new InMemoryByteArrayStorage());
        assertNotNull(storage);
    }

    @Test
    public void storeAndRetrieve() throws Exception {
        SecureResourceStorage storage = new SecureResourceStorage(new InMemoryByteArrayStorage());
        Key key = AESCryptoUtil.generateKey();
        Resource resource = new TestResource();

        storage.store(resource, key);

        assertEquals(resource, storage.retrieve(resource.getId(), key));
    }

    private static class TestResource implements Resource {
        private final UUID id;

        TestResource() {
            this.id = UUID.randomUUID();
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestResource that = (TestResource) o;

            return id.equals(that.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}