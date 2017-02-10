package solutions.oneguard.impassable.core.storage;

import java.util.UUID;

class EncryptedResource {
    private final UUID id;
    private final byte[] encryptedData;

    EncryptedResource(UUID id, byte[] encryptedData) {
        this.id = id;
        this.encryptedData = encryptedData;
    }

    UUID getId() {
        return id;
    }

    byte[] getEncryptedData() {
        return encryptedData;
    }
}
