package solutions.oneguard.impassable.core.storage;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class InMemorySecureStorage extends SecureStorage {
    private final Map<UUID, EncryptedResource> encryptedResources = new Hashtable<>();

    @Override
    protected void doStore(EncryptedResource encryptedResource) throws SecureStorageException {
        encryptedResources.put(encryptedResource.getId(), encryptedResource);
    }

    @Override
    protected EncryptedResource doRetrieve(UUID id) throws SecureStorageException {
        return encryptedResources.get(id);
    }
}
