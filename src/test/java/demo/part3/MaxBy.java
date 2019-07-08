package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.maxBy;
import static org.assertj.core.api.Assertions.assertThat;

public class MaxBy {

    @Test
    public void collectors_maxBy() {
        Optional<Integer> max = Stream.of(1, 2, 3)
                .collect(maxBy(Comparator.naturalOrder()));

        assertThat(max)
                .isNotEmpty()
                .hasValue(3);
    }

    @Test
    public void stream_maxBy() {
        Optional<Integer> max = Stream.of(1, 2, 3)
                .max(Comparator.naturalOrder());

        assertThat(max)
                .isNotEmpty()
                .hasValue(3);
    }
}
