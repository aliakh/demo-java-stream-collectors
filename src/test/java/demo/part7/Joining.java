package demo.part7;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Joining {

    @Test
    public void collectors_joining() {
        String result = Stream.of(1, 2, 3)
                .map(String::valueOf)
                .collect(joining());

        assertThat(result).isEqualTo("123");
    }

    @Test
    public void collectors_joining_delimiter() {
        String result = Stream.of(1, 2, 3)
                .map(String::valueOf)
                .collect(joining(","));

        assertThat(result).isEqualTo("1,2,3");
    }

    @Test
    public void collectors_joining_delimiter_prefix_suffix() {
        String result = Stream.of(1, 2, 3)
                .map(String::valueOf)
                .collect(joining(",", "[", "]"));

        assertThat(result).isEqualTo("[1,2,3]");
    }
}
