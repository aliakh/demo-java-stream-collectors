package demo.part2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Filtering {

    /**
     * @since 9
     */
    @Test
    public void collectors_filtering() {
        List<Integer> listOfOddNumbers = Stream.of(1, 2, 3)
                .collect(filtering(i -> i % 2 != 0, toList()));

        assertThat(listOfOddNumbers)
                .hasSize(2)
                .containsOnly(1, 3);
    }

    @Test
    public void stream_filter() {
        List<Integer> listOfOddNumbers = Stream.of(1, 2, 3)
                .filter(i -> i % 2 != 0)
                .collect(toList());

        assertThat(listOfOddNumbers)
                .hasSize(2)
                .containsOnly(1, 3);
    }
}
