package solutions.oneguard.impassable.core.util;

import org.junit.Test;
import solutions.oneguard.impassable.core.storage.TestSerializable;

import static org.junit.Assert.assertEquals;

public class SerializationUtilTest {
    @Test
    public void serializeAndDeserialize() throws Exception {
        TestSerializable testObject = TestSerializable.generate();

        byte[] serialized = SerializationUtil.serialize(testObject);

        assertEquals(testObject, SerializationUtil.deserialize(TestSerializable.class, serialized));
    }
}
