package solutions.oneguard.impassable.core.storage.secure;

import solutions.oneguard.impassable.core.storage.StorageException;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.util.AESCryptoUtil;
import solutions.oneguard.impassable.core.util.SerializationUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.UUID;

public class SecureSymmetricStorage <T extends Serializable> {
    private final Class<T> tClass;
    private final ByteArrayStorage storage;

    public SecureSymmetricStorage(Class<T> tClass, ByteArrayStorage storage) {
        this.tClass = tClass;
        this.storage = storage;
    }

    public void store(UUID id, T resource, Key secret) throws StorageException {
        byte[] encodedResource = SerializationUtil.serialize(resource);

        final byte[] encrypted;
        try {
            encrypted = AESCryptoUtil.encrypt(secret, encodedResource);
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not encrypt the resource", e);
        }

        storage.store(id, encrypted);
    }

    public T retrieve(UUID id, Key secret) throws StorageException {
        byte[] encrypted = storage.retrieve(id);
        if (encrypted == null) {
            return null;
        }

        final byte[] encodedResource;
        try {
            encodedResource = AESCryptoUtil.decrypt(secret, encrypted);
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not decrypt the resource", e);
        }

        return SerializationUtil.deserialize(tClass, encodedResource);
    }
}
