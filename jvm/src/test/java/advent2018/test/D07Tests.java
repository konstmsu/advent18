package advent2018.test;

import advent2018.D07;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class D07Tests {
    @Test
    public void sample1() {
        assertThat(D07.solve1(List.of(
                "Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."))).isEqualTo("CABDFE");
    }

    @Test
    public void input1() throws IOException {
        assertThat(D07.solve1(Files.readAllLines(Utils.of("d07.txt")))).isEqualTo("BFKEGNOVATIHXYZRMCJDLSUPWQ");
    }

    @Test
    public void sample2() {
        assertThat(D07.solve2(2, 0, List.of(
                "Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin."))).isEqualTo(15);
    }

    static <T> T measureTime(Supplier<T> test) {
        var start = System.nanoTime();
        T result;
        int counter = 0;
        for (; ; ) {
            result = test.get();
            counter++;
            if (System.nanoTime() - 2 * 1_000_000_000 > start)
                break;
        }

        var totalTime = System.nanoTime() - start;
        System.out.printf("Ran %d operations in %dns - %.2f ms per operation", counter, totalTime, totalTime * 0.001 * 0.001 / counter);
        return result;
    }

    @Test
    public void input2() throws IOException {
        List<String> rows = Files.readAllLines(Utils.of("d07.txt"));
        int result = measureTime(() -> D07.solve2(5, 60, rows));
        assertThat(result).isLessThan(1800);
        assertThat(result).isEqualTo(1020);
    }
}
