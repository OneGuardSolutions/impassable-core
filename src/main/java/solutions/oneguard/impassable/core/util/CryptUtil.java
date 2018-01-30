package solutions.oneguard.impassable.core.util;

import org.springframework.security.crypto.keygen.KeyGenerators;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class CryptUtil {
    private static final int SYMMETRIC_KEY_LENGTH = 32; // 256-bit
    private static final int ASYMMETRIC_KEY_LENGTH = 4096; // 4096-bit

    public static byte[] generateKey() {
        return KeyGenerators.secureRandom(SYMMETRIC_KEY_LENGTH).generateKey();
    }

    public static byte[] generateSalt() {
        return KeyGenerators.secureRandom().generateKey();
    }

    public static KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(ASYMMETRIC_KEY_LENGTH);

            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
