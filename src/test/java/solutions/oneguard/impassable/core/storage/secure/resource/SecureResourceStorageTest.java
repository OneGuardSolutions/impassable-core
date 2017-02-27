package solutions.oneguard.impassable.core.storage.secure.resource;

import org.junit.Test;
import org.springframework.security.crypto.keygen.KeyGenerators;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;

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
        byte[] key = KeyGenerators.secureRandom(16).generateKey();
        byte[] salt = KeyGenerators.secureRandom().generateKey();

        SecureResourceStorage storage = new SecureResourceStorage(new InMemoryByteArrayStorage());
        Resource resource = new TestResource();

        storage.store(resource, key, salt);

        assertEquals(resource, storage.retrieve(resource.getId(), key, salt));
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