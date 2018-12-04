package advent2018.test;

import advent2018.D04;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class D04Tests {
    @Test
    public void shouldExtractTimestamp() {
        assertThat(D04.extractTimestamp("[1518-11-05 00:45] falls asleep")).isEqualTo(1518110545);
        assertThat(D04.extractTimestamp("[1619-03-16 00:04] falls asleep")).isEqualTo(1619031604);
    }

    @Test
    public void sample1() {
        assertThat(D04.solve1(List.of(
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up",
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-03 00:05] Guard #10 begins shift",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
        )));
    }
}
