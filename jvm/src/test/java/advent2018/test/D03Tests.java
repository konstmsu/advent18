package advent2018.test;

import advent2018.D03;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class D03Tests {
    @Test
    public void sample() {
        assertThat(D03.process(List.of(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2")).overlaps)
                .isEqualTo(4);
    }

    @Test
    public void inputs() throws IOException {
        var result = D03.process(Files.readAllLines(Utils.of("d03.txt")));
        assertThat(result.overlaps).isEqualTo(114946);
        assertThat(result.intactId).isEqualTo(877);
    }

    @Test
    public void sample2() {
        assertThat(D03.process(List.of(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2")).intactId)
                .isEqualTo(3);
    }
}
