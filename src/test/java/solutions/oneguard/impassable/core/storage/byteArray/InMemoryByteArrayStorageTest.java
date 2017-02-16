package solutions.oneguard.impassable.core.storage.byteArray;

import org.junit.Test;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.storage.byteArray.InMemoryByteArrayStorage;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class InMemoryByteArrayStorageTest {
    @Test
    public void storeAndRetrieve() throws Exception {
        byte[] raw = new byte[1024];
        new Random().nextBytes(raw);
        UUID id = UUID.randomUUID();
        ByteArrayStorage storage = new InMemoryByteArrayStorage();

        storage.store(id, raw);

        assertEquals(raw, storage.retrieve(id));
    }
}