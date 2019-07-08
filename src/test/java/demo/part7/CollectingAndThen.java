package demo.part7;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CollectingAndThen {

    @Test
    public void collectors_collectingAndThen() {
        List<Integer> unmodifiableList = Stream.of(1, 2, 3)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));

        assertThat(unmodifiableList)
                .hasSize(3)
                .containsOnly(1, 2, 3);

        assertThatThrownBy(unmodifiableList::clear)
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
