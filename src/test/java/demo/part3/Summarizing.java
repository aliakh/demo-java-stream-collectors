package demo.part3;

import org.junit.jupiter.api.Test;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summarizingDouble;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summarizingLong;
import static org.assertj.core.api.Assertions.assertThat;

public class Summarizing {

    @Test
    public void collectors_summarizingInt() {
        IntSummaryStatistics iss = Stream.of(1, 2, 3)
                .collect(summarizingInt(i -> i));

        assertThat(iss.getAverage()).isEqualTo(2);
        assertThat(iss.getCount()).isEqualTo(3);
        assertThat(iss.getMax()).isEqualTo(3);
        assertThat(iss.getMin()).isEqualTo(1);
        assertThat(iss.getSum()).isEqualTo(6);
    }

    @Test
    public void collectors_summarizingLong() {
        LongSummaryStatistics lss = Stream.of(1, 2, 3)
                .collect(summarizingLong(i -> i));

        assertThat(lss.getAverage()).isEqualTo(2);
        assertThat(lss.getCount()).isEqualTo(3);
        assertThat(lss.getMax()).isEqualTo(3);
        assertThat(lss.getMin()).isEqualTo(1);
        assertThat(lss.getSum()).isEqualTo(6);
    }

    @Test
    public void collectors_summarizingDouble() {
        DoubleSummaryStatistics dss = Stream.of(1, 2, 3)
                .collect(summarizingDouble(i -> i));

        assertThat(dss.getAverage()).isEqualTo(2);
        assertThat(dss.getCount()).isEqualTo(3);
        assertThat(dss.getMax()).isEqualTo(3);
        assertThat(dss.getMin()).isEqualTo(1);
        assertThat(dss.getSum()).isEqualTo(6);
    }

    @Test
    public void stream_summarizingInt() {
        IntSummaryStatistics iss = Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .summaryStatistics();

        assertThat(iss.getAverage()).isEqualTo(2);
        assertThat(iss.getCount()).isEqualTo(3);
        assertThat(iss.getMax()).isEqualTo(3);
        assertThat(iss.getMin()).isEqualTo(1);
        assertThat(iss.getSum()).isEqualTo(6);
    }

    @Test
    public void stream_summarizingLong() {
        LongSummaryStatistics lss = Stream.of(1, 2, 3)
                .mapToLong(i -> i)
                .summaryStatistics();

        assertThat(lss.getAverage()).isEqualTo(2);
        assertThat(lss.getCount()).isEqualTo(3);
        assertThat(lss.getMax()).isEqualTo(3);
        assertThat(lss.getMin()).isEqualTo(1);
        assertThat(lss.getSum()).isEqualTo(6);
    }

    @Test
    public void stream_summarizingDouble() {
        DoubleSummaryStatistics dss = Stream.of(1, 2, 3)
                .mapToDouble(i -> i)
                .summaryStatistics();

        assertThat(dss.getAverage()).isEqualTo(2);
        assertThat(dss.getCount()).isEqualTo(3);
        assertThat(dss.getMax()).isEqualTo(3);
        assertThat(dss.getMin()).isEqualTo(1);
        assertThat(dss.getSum()).isEqualTo(6);
    }
}
