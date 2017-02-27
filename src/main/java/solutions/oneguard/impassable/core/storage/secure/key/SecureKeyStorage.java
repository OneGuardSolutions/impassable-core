package solutions.oneguard.impassable.core.storage.secure.key;

import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.storage.secure.SecureAsymmetricStorage;

public class SecureKeyStorage extends SecureAsymmetricStorage<KeyAndSalt> {
    public SecureKeyStorage(ByteArrayStorage storage) {
        super(KeyAndSalt.class, storage);
    }
}
