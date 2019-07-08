package demo.part0;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamCollect {

    @Test
    public void collect_supplier_accumulator_combiner() {
        List<Integer> list = Stream.of(1, 2, 3)
                .collect(
                        ArrayList::new,     // supplier
                        ArrayList::add,     // accumulator
                        ArrayList::addAll   // combiner
                );

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }

    @Test
    public void collect_collector() {
        Collector<Integer, ArrayList<Integer>, List<Integer>> collector = new Collector<>() {

            @Override
            public Supplier<ArrayList<Integer>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<ArrayList<Integer>, Integer> accumulator() {
                return ArrayList::add;
            }

            @Override
            public BinaryOperator<ArrayList<Integer>> combiner() {
                return (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                };
            }

            @Override
            public Function<ArrayList<Integer>, List<Integer>> finisher() {
                return list -> list;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };

        assertThat(collector.characteristics())
                .containsOnly();

        List<Integer> list = Stream.of(1, 2, 3)
                .collect(collector);

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }

    @Test
    public void collect_collector_of() {
        Collector<Integer, ?, ArrayList<Integer>> collector = Collector.of(
                ArrayList::new,         // supplier
                ArrayList::add,         // accumulator
                (list1, list2) -> {     // combiner
                    list1.addAll(list2);
                    return list1;
                }
        );

        assertThat(collector.characteristics())
                .containsOnly(Collector.Characteristics.IDENTITY_FINISH);

        List<Integer> list = Stream.of(1, 2, 3)
                .collect(collector);

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }

    @Test
    public void collect_collectors_toList() {
        Collector<Integer, ?, List<Integer>> collector = Collectors.toList();

        assertThat(collector.characteristics())
                .containsOnly(Collector.Characteristics.IDENTITY_FINISH);

        List<Integer> list = Stream.of(1, 2, 3)
                .collect(collector);

        assertThat(list)
                .hasSize(3)
                .containsOnly(1, 2, 3);
    }
}
