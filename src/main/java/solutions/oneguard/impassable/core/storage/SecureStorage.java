package solutions.oneguard.impassable.core.storage;

import solutions.oneguard.impassable.core.util.AESCryptoUtil;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.UUID;

public abstract class SecureStorage {
    public void store(Resource resource, Key secret) throws SecureStorageException {
        byte[] serialized;
        try (
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)
        ) {
            oos.writeObject(resource);
            serialized = baos.toByteArray();
        } catch (IOException e) {
            throw new SecureStorageException("could not serialize the resource", e);
        }

        byte[] encryptedData;
        try {
            encryptedData = AESCryptoUtil.encrypt(secret, serialized);
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not encrypt the resource", e);
        }
        EncryptedResource encryptedResource = new EncryptedResource(resource.getId(), encryptedData);

        doStore(encryptedResource);
    }

    public Resource retrieve(UUID id, Key secret) throws SecureStorageException {
        EncryptedResource encryptedResource = doRetrieve(id);

        byte[] raw;
        try {
            raw = AESCryptoUtil.decrypt(secret, encryptedResource.getEncryptedData());
        } catch (GeneralSecurityException e) {
            throw new SecureStorageException("could not decrypt the resource", e);
        }

        try (
            ByteArrayInputStream bais = new ByteArrayInputStream(raw);
            ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return  (Resource) ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new SecureStorageException("could not deserialize the resource", e);
        }
    }

    protected abstract void doStore(EncryptedResource encryptedResource) throws SecureStorageException;

    protected abstract EncryptedResource doRetrieve(UUID id) throws SecureStorageException;
}
