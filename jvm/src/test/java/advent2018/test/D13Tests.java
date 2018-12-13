package advent2018.test;

import advent2018.D13;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class D13Tests {
    @Test
    public void firstMoveTest() {
        assertThat(D13.getFirstCrashPoint(List.of(
                "v",
                "^"
        ))).isEqualTo(new int[]{0, 1});

        assertThat(D13.getFirstCrashPoint(List.of(
                "><"
        ))).isEqualTo(new int[]{1, 0});
    }

    @Test
    public void sample11() {
        assertThat(D13.getFirstCrashPoint(List.of(
                "|",
                "v",
                "|",
                "|",
                "|",
                "^",
                "|"
        ))).isEqualTo(new int[]{0, 3});
    }

    @Test
    public void sample12() {
        assertThat(D13.getFirstCrashPoint(List.of(
                "/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/   "
        ))).isEqualTo(new int[]{7, 3});
    }

    @Test
    public void input1() throws IOException {
        assertThat(D13.getFirstCrashPoint(Files.readAllLines(Utils.of("d13.txt"))))
                .isEqualTo(new int[]{41, 22});
    }

    @Test
    public void sample2() {
        assertThat(D13.getLastCartPoint(List.of(
                "/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/")))
                .isEqualTo(new int[]{6, 4});
    }

    @Test
    public void javaModTest() {
        assertThat(D13.mod(-7, 3)).isEqualTo(2);
        assertThat(D13.mod(-6, 3)).isEqualTo(0);
        assertThat(D13.mod(-5, 3)).isEqualTo(1);
        assertThat(D13.mod(-4, 3)).isEqualTo(2);
        assertThat(D13.mod(-3, 3)).isEqualTo(0);
        assertThat(D13.mod(-2, 3)).isEqualTo(1);
        assertThat(D13.mod(-1, 3)).isEqualTo(2);
        assertThat(D13.mod(0, 3)).isEqualTo(0);
        assertThat(D13.mod(1, 3)).isEqualTo(1);
        assertThat(D13.mod(2, 3)).isEqualTo(2);
        assertThat(D13.mod(3, 3)).isEqualTo(0);
        assertThat(D13.mod(4, 3)).isEqualTo(1);
        assertThat(D13.mod(5, 3)).isEqualTo(2);
        assertThat(D13.mod(6, 3)).isEqualTo(0);
        assertThat(D13.mod(7, 3)).isEqualTo(1);
    }
}
