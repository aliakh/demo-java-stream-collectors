package demo.part5;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 10
 */
public class ToUnmodifiableMap {

    @Test
    public void collectors_toUnmodifiableMap_keyMapper_valueMapper() {
        Map<Character, String> unmodifiableMap = Stream.of("Alpha", "Bravo", "Charlie")
                .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity()));

        assertThat(unmodifiableMap)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie");

        assertThatThrownBy(unmodifiableMap::clear)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void collectors_toUnmodifiableMap_keyMapper_valueMapper_nullKey() {
        assertThrows(NullPointerException.class, () -> Stream.of("Alpha")
                .collect(toUnmodifiableMap(s -> null, Function.identity())));
    }

    @Test
    public void collectors_toUnmodifiableMap_keyMapper_valueMapper_nullValue() {
        assertThrows(NullPointerException.class, () -> Stream.of("Alpha")
                .collect(toUnmodifiableMap(s -> s.charAt(0), s -> null)));
    }

    @Test
    public void collectors_toUnmodifiableMap_keyMapper_valueMapper_keysCollision() {
        assertThrows(IllegalStateException.class, () -> Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity())));
    }

    @Test
    public void collectors_toUnmodifiableMap_keyMapper_valueMapper_mergeFunction() {
        Map<Character, String> unmodifiableMap = Stream.of(
                "Amsterdam", "Baltimore", "Casablanca",
                "Alpha", "Bravo", "Charlie")
                .collect(toUnmodifiableMap(s -> s.charAt(0), Function.identity(), (v1, v2) -> v2));

        assertThat(unmodifiableMap)
                .hasSize(3)
                .containsEntry('A', "Alpha")
                .containsEntry('B', "Bravo")
                .containsEntry('C', "Charlie");

        assertThatThrownBy(unmodifiableMap::clear)
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
