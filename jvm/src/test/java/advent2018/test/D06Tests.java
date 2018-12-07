package advent2018.test;

import advent2018.D06;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class D06Tests {
    @Test
    public void sample1() {
        assertThat(D06.solve1(List.of(
                "1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"
        ))).isEqualTo(17);
    }

    @Test
    public void input1() throws IOException {
        assertThat(D06.solve1(Files.readAllLines(Utils.of("d06.txt")))).isEqualTo(5365);
    }

    @Test
    public void sample2() {
        assertThat(D06.solve2(32, List.of(
                "1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"
        ))).isEqualTo(16);
    }

    @Test
    public void input2() throws IOException {
        assertThat(D06.solve2(10000, Files.readAllLines(Utils.of("d06.txt")))).isEqualTo(42513);
    }
}
