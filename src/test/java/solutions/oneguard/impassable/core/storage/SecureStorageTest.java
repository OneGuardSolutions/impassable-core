package solutions.oneguard.impassable.core.storage;

import org.junit.Test;
import solutions.oneguard.impassable.core.util.AESCryptoUtil;

import java.security.Key;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SecureStorageTest {
    @Test
    public void storeAndRetrieve() throws Exception {
        Key key = AESCryptoUtil.generateKey();

        SecureStorage storage = new InMemorySecureStorage();
        Resource resource = new TestResource("Hello storage");

        storage.store(resource, key);
        Resource resource2 = storage.retrieve(resource.getId(), key);

        assertEquals(resource, resource2);
    }

    private static class TestResource implements Resource {
        private UUID id;
        private String text;

        TestResource(String text) {
            this.id = UUID.randomUUID();
            this.text = text;
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

            return text != null ? text.equals(that.text) : that.text == null;
        }

        @Override
        public int hashCode() {
            return text != null ? text.hashCode() : 0;
        }
    }
}