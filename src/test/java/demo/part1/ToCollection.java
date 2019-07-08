package demo.part1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;

public class ToCollection {

    @Test
    public void collectors_toCollection_ArrayList() {
        List<Integer> list = Stream.of(1, 2, 3)
                .collect(toCollection(ArrayList::new));

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3)
                .isExactlyInstanceOf(ArrayList.class);
    }
}
