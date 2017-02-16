package solutions.oneguard.impassable.core.util;

import java.io.*;

public class SerializationUtil {
    public static byte[] serialize(Serializable object) {
        try (
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(object);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Serializable> T deserialize(Class<T> clazz, byte[] encodedResource) {
        try (
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encodedResource);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
        ) {
            return clazz.cast(objectInputStream.readObject());
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
