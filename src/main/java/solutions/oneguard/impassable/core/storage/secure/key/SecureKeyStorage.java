package solutions.oneguard.impassable.core.storage.secure.key;

import solutions.oneguard.impassable.core.storage.byteArray.ByteArrayStorage;
import solutions.oneguard.impassable.core.storage.secure.SecureAsymmetricStorage;

import java.security.Key;

public class SecureKeyStorage extends SecureAsymmetricStorage<Key> {
    public SecureKeyStorage(ByteArrayStorage storage) {
        super(Key.class, storage);
    }
}
