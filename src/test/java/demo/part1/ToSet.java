package demo.part1;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class ToSet {

    @Test
    public void collectors_toSet() {
        Set<Integer> set = Stream.of(1, 1, 2, 2, 3, 3)
                .collect(toSet());

        assertThat(set)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }

    @Test
    public void collectors_toSet_null() {
        Set<Integer> setOfNull = Stream.of((Integer) null)
                .collect(toSet());

        assertThat(setOfNull)
                .hasSize(1)
                .element(0).isNull();
    }
}
