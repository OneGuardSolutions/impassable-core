package solutions.oneguard.impassable.core.storage.secure;

import solutions.oneguard.impassable.core.storage.StorageException;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.util.RSACryptoUtil;
import solutions.oneguard.impassable.core.util.SerializationUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class SecureAsymmetricStorage <T extends Serializable> {
    private final Class<T> tClass;
    private final ByteArrayStorage storage;

    public SecureAsymmetricStorage(Class<T> tClass, ByteArrayStorage storage) {
        this.tClass = tClass;
        this.storage = storage;
    }

    public void store(UUID resourceId, T resource, PublicKey secret) throws StorageException {
        final byte[] encrypted;
        try {
            encrypted = RSACryptoUtil.encrypt(secret, SerializationUtil.serialize(resource));
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not encrypt the resource", e);
        }

        storage.store(resourceId, encrypted);
    }

    public T retrieve(UUID resourceId, PrivateKey secret) throws StorageException {
        byte[] encrypted = storage.retrieve(resourceId);
        if (encrypted == null) {
            return null;
        }

        final byte[] serialized;
        try {
            serialized = RSACryptoUtil.decrypt(secret, encrypted);
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not decrypt the resource", e);
        }

        return SerializationUtil.deserialize(tClass, serialized);
    }
}
