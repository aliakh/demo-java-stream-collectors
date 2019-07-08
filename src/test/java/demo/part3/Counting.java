package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Counting {

    @Test
    public void collectors_counting() {
        long count = Stream.of(1, 2, 3)
                .collect(counting());

        assertEquals(3L, count);
    }

    @Test
    public void stream_count() {
        long count = Stream.of(1, 2, 3)
                .count();

        assertEquals(3L, count);
    }
}
