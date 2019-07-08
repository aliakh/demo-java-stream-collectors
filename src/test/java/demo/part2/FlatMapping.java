package demo.part2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class FlatMapping {

    /**
     * @since 9
     */
    @Test
    public void collectors_flatMapping() {
        List<Integer> list = Stream.of(
                List.of(1, 2),
                List.of(3, 4))
                .collect(flatMapping(List::stream, toList()));

        assertThat(list)
                .hasSize(4)
                .containsOnly(1, 2, 3, 4);
    }

    @Test
    public void stream_flatMap() {
        List<Integer> list = Stream.of(
                List.of(1, 2),
                List.of(3, 4))
                .flatMap(List::stream)
                .collect(toList());

        assertThat(list)
                .hasSize(4)
                .containsOnly(1, 2, 3, 4);
    }
}
