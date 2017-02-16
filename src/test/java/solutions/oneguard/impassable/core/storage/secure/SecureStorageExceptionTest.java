package solutions.oneguard.impassable.core.storage.secure;

import org.junit.Test;

import static org.junit.Assert.*;

public class SecureStorageExceptionTest {
    @Test
    public void constructor() throws Exception {
        assertNotNull(new SecureStorageException("test", null));
    }
}
