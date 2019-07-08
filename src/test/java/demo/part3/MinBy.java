package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.minBy;
import static org.assertj.core.api.Assertions.assertThat;

public class MinBy {

    @Test
    public void collectors_minBy() {
        Optional<Integer> min = Stream.of(1, 2, 3)
                .collect(minBy(Comparator.naturalOrder()));

        assertThat(min)
                .isNotEmpty()
                .hasValue(1);
    }

    @Test
    public void stream_minBy() {
        Optional<Integer> min = Stream.of(1, 2, 3)
                .min(Comparator.naturalOrder());

        assertThat(min)
                .isNotEmpty()
                .hasValue(1);
    }
}
