package demo.part6;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class PartitioningBy {

    @Test
    public void collectors_partitioningBy_predicate() {
        Map<Boolean, List<Integer>> reminderFromDivisionBy2IsZero = Stream.of(1, 2, 3)
                .collect(partitioningBy(i -> i % 2 == 0));

        assertThat(reminderFromDivisionBy2IsZero)
                .hasSize(2)
                .containsEntry(false, List.of(1, 3))
                .containsEntry(true, List.of(2));
    }

    @Test
    public void collectors_partitioningBy_predicate_downstream() {
        Map<Boolean, Set<Integer>> reminderFromDivisionBy4IsZero = Stream.of(1, 2, 3)
                .collect(partitioningBy(i -> i % 4 == 0, toSet()));

        assertThat(reminderFromDivisionBy4IsZero)
                .hasSize(2)
                .containsEntry(false, Set.of(1, 2, 3))
                .containsEntry(true, Set.of());
    }
}
