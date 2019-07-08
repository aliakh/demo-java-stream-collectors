package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.averagingLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Averaging {

    @Test
    public void collectors_averagingInt() {
        double average = Stream.of(1, 2, 3)
                .collect(averagingInt(i -> i));

        assertThat(average).isEqualTo(2);
    }

    @Test
    public void collectors_averagingLong() {
        double average = Stream.of(1, 2, 3)
                .collect(averagingLong(i -> i));

        assertThat(average).isEqualTo(2);
    }

    @Test
    public void collectors_averagingDouble() {
        double average = Stream.of(1, 2, 3)
                .collect(averagingDouble(i -> i));

        assertThat(average).isEqualTo(2);
    }

    @Test
    public void stream_mapToInt_average() {
        OptionalDouble averageOptional = Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .average();

        assertTrue(averageOptional.isPresent());
        assertThat(averageOptional.getAsDouble()).isEqualTo(2);
    }

    @Test
    public void stream_mapToLong_average() {
        OptionalDouble averageOptional = Stream.of(1, 2, 3)
                .mapToLong(i -> i)
                .average();

        assertTrue(averageOptional.isPresent());
        assertThat(averageOptional.getAsDouble()).isEqualTo(2);
    }

    @Test
    public void stream_mapToDouble_average() {
        OptionalDouble averageOptional = Stream.of(1, 2, 3)
                .mapToDouble(i -> i)
                .average();

        assertTrue(averageOptional.isPresent());
        assertThat(averageOptional.getAsDouble()).isEqualTo(2);
    }
}
