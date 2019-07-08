package demo.part1;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 10
 */
public class ToUnmodifiableSet {

    @Test
    public void collectors_toUnmodifiableSet() {
        Set<Integer> unmodifiableSet = Stream.of(1, 1, 2, 2, 3, 3)
                .collect(toUnmodifiableSet());

        assertThat(unmodifiableSet)
                .hasSize(3)
                .containsOnly(1, 2, 3);

        assertThatThrownBy(unmodifiableSet::clear)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void collectors_toUnmodifiableSet_null() {
        assertThrows(NullPointerException.class, () -> Stream.of((Integer) null)
                .collect(toUnmodifiableSet()));
    }
}
