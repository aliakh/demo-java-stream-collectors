package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.summingLong;
import static org.assertj.core.api.Assertions.assertThat;

public class Summing {

    @Test
    public void collectors_summingInt() {
        int sum = Stream.of(1, 2, 3)
                .collect(summingInt(i -> i));

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void collectors_summingLong() {
        long sum = Stream.of(1, 2, 3)
                .collect(summingLong(i -> i));

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void collectors_summingDouble() {
        double sum = Stream.of(1, 2, 3)
                .collect(summingDouble(i -> i));

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void stream_mapToInt_sum() {
        int sum = Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .sum();

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void stream_mapToLong_sum() {
        long sum = Stream.of(1, 2, 3)
                .mapToLong(i -> i)
                .sum();

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void stream_mapToDouble_sum() {
        double sum = Stream.of(1, 2, 3)
                .mapToDouble(i -> i)
                .sum();

        assertThat(sum).isEqualTo(6);
    }
}
