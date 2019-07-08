package demo.part7;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.teeing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @since 12
 */
public class Teeing {

    @Test
    public void collectors_teeing() {
        Map.Entry<Optional<Integer>, Optional<Integer>> limits = Stream.of(1, 2, 3)
                .collect(
                        teeing(
                                minBy(Integer::compareTo),
                                maxBy(Integer::compareTo),
                                AbstractMap.SimpleImmutableEntry::new
                        )
                );

        assertNotNull(limits);

        Optional<Integer> minOptional = limits.getKey();
        assertThat(minOptional)
                .isNotEmpty()
                .hasValue(1);

        Optional<Integer> maxOptional = limits.getValue();
        assertThat(maxOptional)
                .isNotEmpty()
                .hasValue(3);
    }
}
