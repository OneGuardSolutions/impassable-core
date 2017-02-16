package solutions.oneguard.impassable.core.storage.byteArray;

import solutions.oneguard.impassable.core.storage.StorageException;

import java.util.UUID;

public interface ByteArrayStorage {
    void store(UUID id, byte[] data) throws StorageException;
    byte[] retrieve(UUID id) throws StorageException;
}
