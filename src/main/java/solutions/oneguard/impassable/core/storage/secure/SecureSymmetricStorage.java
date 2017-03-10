package solutions.oneguard.impassable.core.storage.secure;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.util.SerializationUtils;
import solutions.oneguard.impassable.core.storage.StorageException;
import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;

import java.io.Serializable;
import java.util.UUID;

public class SecureSymmetricStorage <T extends Serializable> {
    private final Class<T> tClass;
    private final ByteArrayStorage storage;

    public SecureSymmetricStorage(Class<T> tClass, ByteArrayStorage storage) {
        this.tClass = tClass;
        this.storage = storage;
    }

    public void store(UUID id, T resource, byte[] key, byte[] salt) throws StorageException {
        BytesEncryptor encryptor = Encryptors.standard(new String(key), new String(Hex.encode(salt)));

        byte[] serialized = SerializationUtils.serialize(resource);
        byte[] encrypted = encryptor.encrypt(serialized);

        storage.store(id, encrypted);
    }

    public T retrieve(UUID id, byte[] key, byte[] salt) throws StorageException {
        byte[] encrypted = storage.retrieve(id);
        if (encrypted == null) {
            return null;
        }

        BytesEncryptor encryptor = Encryptors.standard(new String(key), new String(Hex.encode(salt)));
        byte[] serialized = encryptor.decrypt(encrypted);
        Object deserialized = SerializationUtils.deserialize(serialized);

        return tClass.cast(deserialized);
    }
}
