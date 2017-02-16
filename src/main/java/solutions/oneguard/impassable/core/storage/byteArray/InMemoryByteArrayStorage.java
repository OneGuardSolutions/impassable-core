package solutions.oneguard.impassable.core.storage.byteArray;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class InMemoryByteArrayStorage implements ByteArrayStorage {
    private final Map<UUID, byte[]> encryptedResources = new Hashtable<>();

    @Override
    public void store(UUID id, byte[] data) {
        encryptedResources.put(id, data);
    }

    @Override
    public byte[] retrieve(UUID id) {
        return encryptedResources.get(id);
    }
}
