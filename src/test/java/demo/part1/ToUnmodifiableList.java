package demo.part1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 10
 */
public class ToUnmodifiableList {

    @Test
    public void collectors_toUnmodifiableList() {
        List<Integer> unmodifiableList = Stream.of(1, 2, 3)
                .collect(toUnmodifiableList());

        assertThat(unmodifiableList)
                .hasSize(3)
                .containsOnly(1, 2, 3);

        assertThatThrownBy(unmodifiableList::clear)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void collectors_toUnmodifiableList_null() {
        assertThrows(NullPointerException.class, () -> Stream.of((Integer) null)
                .collect(toUnmodifiableList()));
    }
}
