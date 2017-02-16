package solutions.oneguard.impassable.core.storage;

import java.io.Serializable;
import java.util.Random;

public class TestSerializable implements Serializable {
    private final String text;

    public TestSerializable(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSerializable that = (TestSerializable) o;

        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    public static TestSerializable generate() {
        return new TestSerializable(String.valueOf(new Random().nextLong()));
    }
}
