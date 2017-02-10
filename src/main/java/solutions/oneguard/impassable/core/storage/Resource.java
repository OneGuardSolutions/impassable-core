package solutions.oneguard.impassable.core.storage;

import java.io.Serializable;
import java.util.UUID;

public interface Resource extends Serializable {
    UUID getId();
}
