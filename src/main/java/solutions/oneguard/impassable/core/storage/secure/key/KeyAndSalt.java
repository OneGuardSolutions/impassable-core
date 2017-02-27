package solutions.oneguard.impassable.core.storage.secure.key;

import solutions.oneguard.impassable.core.storage.secure.resource.Resource;

import java.util.UUID;

public class KeyAndSalt implements Resource {
    private final UUID id;
    private final byte[] secret;
    private final byte[] salt;

    public KeyAndSalt(byte[] secret, byte[] salt) {
        id = UUID.randomUUID();
        this.secret = secret;
        this.salt = salt;
    }

    public byte[] getSecret() {
        return secret;
    }

    public byte[] getSalt() {
        return salt;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
