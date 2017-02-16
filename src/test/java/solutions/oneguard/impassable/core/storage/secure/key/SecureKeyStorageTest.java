package solutions.oneguard.impassable.core.storage.secure.key;

import org.junit.Test;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;

import static org.junit.Assert.assertNotNull;

public class SecureKeyStorageTest {
    @Test
    public void create() throws Exception {
        SecureKeyStorage storage = new SecureKeyStorage(new InMemoryByteArrayStorage());

        assertNotNull(storage);
    }
}
