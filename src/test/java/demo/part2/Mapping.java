package demo.part2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Mapping {

    @Test
    public void collectors_mapping() {
        List<Integer> listOfSquares = Stream.of(1, 2, 3)
                .collect(mapping(i -> i * i, toList()));

        assertThat(listOfSquares)
                .hasSize(3)
                .containsOnly(1, 4, 9);
    }

    @Test
    public void stream_mapping() {
        List<Integer> listOfSquares = Stream.of(1, 2, 3)
                .map(i -> i * i)
                .collect(toList());

        assertThat(listOfSquares)
                .hasSize(3)
                .containsOnly(1, 4, 9);
    }
}
