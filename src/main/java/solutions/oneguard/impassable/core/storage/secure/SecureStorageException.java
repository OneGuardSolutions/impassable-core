package solutions.oneguard.impassable.core.storage.secure;

import solutions.oneguard.impassable.core.storage.StorageException;

public class SecureStorageException extends StorageException {
    public SecureStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
