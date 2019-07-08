package demo.part5;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToMap {

    @Test
    public void collectors_toMap_keyMapper_valueMapper() {
        Map<Character, String> map = Stream.of("Alpha", "Bravo", "Charlie")
                .collect(toMap(s -> s.charAt(0), Function.identity()));

        assertThat(map)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie");
    }

    @Test
    public void collectors_toMap_keyMapper_valueMapper_nullKey() {
        Map<Character, String> map = Stream.of("Alpha")
                .collect(toMap(s -> null, Function.identity()));

        assertThat(map)
                .hasSize(1)
                .containsEntry(null, "Alpha");
    }

    @Test
    public void collectors_toMap_keyMapper_valueMapper_nullValue() {
        assertThrows(NullPointerException.class, () -> {
            Stream.of("Alpha")
                    .collect(toMap(s -> s.charAt(0), s -> null));
        });
    }

    @Test
    public void collectors_toMap_keyMapper_valueMapper_keysCollision() {
        assertThrows(IllegalStateException.class, () -> {
            Stream.of(
                    "Amsterdam", "Baltimore", "Casablanca",
                    "Alpha", "Bravo", "Charlie")
                    .collect(toMap(s -> s.charAt(0), Function.identity()));
        });
    }

    @Test
    public void collectors_toMap_keyMapper_valueMapper_mergeFunction() {
        Map<Character, String> map = Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .collect(toMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

        assertThat(map)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie");
    }

    @Test
    public void collectors_toMap_keyMapper_valueMapper_mergeFunction_mapFactory() {
        SortedMap<Character, String> map = Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .collect(toMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2, TreeMap::new));

        assertThat(map)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie")
                .isExactlyInstanceOf(TreeMap.class);
    }
}
