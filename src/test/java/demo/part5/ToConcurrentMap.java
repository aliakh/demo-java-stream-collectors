package demo.part5;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToConcurrentMap {

    @Test
    public void characteristics() {
        assertThat(toMap(Function.identity(), Function.identity()).characteristics())
                .doesNotContain(CONCURRENT)
                .doesNotContain(UNORDERED);

        assertThat(toConcurrentMap(Function.identity(), Function.identity()).characteristics())
                .contains(CONCURRENT)
                .contains(UNORDERED);
    }

    @Test
    public void collectors_toConcurrentMap_keyMapper_valueMapper() {
        ConcurrentMap<Character, String> map = Stream.of("Alpha", "Bravo", "Charlie")
                .parallel()
                .collect(toConcurrentMap(s -> s.charAt(0), Function.identity()));

        assertThat(map)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie");
    }

    @Test
    public void collectors_toConcurrentMap_keyMapper_valueMapper_exception() {
        assertThrows(IllegalStateException.class, () -> {
            Stream.of(
                    "Amsterdam", "Baltimore", "Casablanca",
                    "Alpha", "Bravo", "Charlie")
                    .parallel()
                    .collect(toConcurrentMap(s -> s.charAt(0), Function.identity()));
        });
    }

    @Test
    public void collectors_toConcurrentMap_keyMapper_valueMapper_mergeFunction() {
        ConcurrentMap<Character, String> map = Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .parallel()
                .collect(toConcurrentMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

        assertThat(map)
                .hasSize(3)
                .containsKey('A')
                .containsKey('B')
                .containsKey('C');
    }

    @Test
    public void collectors_toConcurrentMap_keyMapper_valueMapper_mergeFunction_mapFactory() {
        ConcurrentMap<Character, String> map = Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .parallel()
                .collect(toConcurrentMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2, ConcurrentSkipListMap::new));

        assertThat(map)
                .hasSize(3)
                .containsKey('A')
                .containsKey('B')
                .containsKey('C')
                .isExactlyInstanceOf(ConcurrentSkipListMap.class);
    }
}

