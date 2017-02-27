package solutions.oneguard.impassable.core.storage.secure;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.rsa.crypto.RsaAlgorithm;
import org.springframework.security.rsa.crypto.RsaSecretEncryptor;
import org.springframework.util.SerializationUtils;
import solutions.oneguard.impassable.core.storage.StorageException;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.UUID;

public class SecureAsymmetricStorage <T extends Serializable> {
    private final Class<T> tClass;
    private final ByteArrayStorage storage;

    public SecureAsymmetricStorage(Class<T> tClass, ByteArrayStorage storage) {
        this.tClass = tClass;
        this.storage = storage;
    }

    public void store(UUID resourceId, T resource, PublicKey publicKey) throws StorageException {
        BytesEncryptor encryptor = new RsaSecretEncryptor(publicKey, RsaAlgorithm.OAEP);
        byte[] serialized = SerializationUtils.serialize(resource);
        byte[] encrypted = encryptor.encrypt(serialized);

        storage.store(resourceId, encrypted);
    }

    public T retrieve(UUID resourceId, KeyPair key) throws StorageException {
        byte[] encrypted = storage.retrieve(resourceId);
        if (encrypted == null) {
            return null;
        }

        BytesEncryptor encryptor = new RsaSecretEncryptor(key, RsaAlgorithm.OAEP);
        byte[] serialized = encryptor.decrypt(encrypted);
        Object deserialized = SerializationUtils.deserialize(serialized);

        return tClass.cast(deserialized);
    }
}
