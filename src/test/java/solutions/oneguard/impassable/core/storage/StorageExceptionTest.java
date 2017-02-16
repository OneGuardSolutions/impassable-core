package solutions.oneguard.impassable.core.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class StorageExceptionTest {
    @Test
    public void constructor() throws Exception {
        assertNotNull(new StorageException("test", null));
    }
}
