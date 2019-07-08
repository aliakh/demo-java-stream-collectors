package demo.part4;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Reducing {

    @Test
    public void collectors_reducing_operator() {
        Optional<Integer> sumOptional = Stream.of(1, 2, 3)
                .collect(reducing(Integer::sum));

        assertTrue(sumOptional.isPresent());
        assertThat(sumOptional.get()).isEqualTo(6);
    }

    @Test
    public void collectors_reducing_identity_operator() {
        Integer sum = Stream.of(1, 2, 3)
                .collect(reducing(0, Integer::sum));

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void collectors_reducing_identity_mapper_operator() {
        Integer sumOfSquares = Stream.of(1, 2, 3)
                .collect(reducing(0, element -> element * element, Integer::sum));

        assertThat(sumOfSquares).isEqualTo(14);
    }

    @Test
    public void stream_reduce_accumulator() {
        Optional<Integer> sumOptional = Stream.of(1, 2, 3)
                .reduce(Integer::sum);

        assertTrue(sumOptional.isPresent());
        assertThat(sumOptional.get()).isEqualTo(6);
    }

    @Test
    public void stream_reduce_identity_accumulator() {
        Integer sum = Stream.of(1, 2, 3)
                .reduce(0, Integer::sum);

        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void stream_reduce_identity_accumulator_combiner() {
        Integer sumOfSquares = Stream.of(1, 2, 3)
                .reduce(0, (result, element) -> result + element * element, Integer::sum);

        assertThat(sumOfSquares).isEqualTo(14);
    }
}
