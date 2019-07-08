package demo.part1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ToList {

    @Test
    public void collectors_toList() {
        List<Integer> list = Stream.of(1, 2, 3)
                .collect(toList());

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }

    @Test
    public void collectors_toList_null() {
        List<Integer> listOfNull = Stream.of((Integer) null)
                .collect(toList());

        assertThat(listOfNull)
                .hasSize(1)
                .element(0).isNull();
    }
}
