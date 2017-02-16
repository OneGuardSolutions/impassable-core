package solutions.oneguard.impassable.core.storage.secure.resource;

import solutions.oneguard.impassable.core.storage.StorageException;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.storage.secure.SecureSymmetricStorage;

import java.security.Key;

public class SecureResourceStorage extends SecureSymmetricStorage<Resource> {
    public SecureResourceStorage(ByteArrayStorage storage) {
        super(Resource.class, storage);
    }

    public void store(Resource resource, Key secret) throws StorageException {
        super.store(resource.getId(), resource, secret);
    }
}
