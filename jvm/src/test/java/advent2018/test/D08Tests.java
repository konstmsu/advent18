package advent2018.test;

import advent2018.D08;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class D08Tests {
    @Test
    public void sample1() {
        assertThat(D08.input1("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")).isEqualTo(138);
    }

    @Test
    public void input1() throws IOException {
        assertThat(D08.input1(Files.readString(Utils.of("d08.txt")).trim())).isEqualTo(45750);
    }

    @Test
    public void sample2() {
        assertThat(D08.input2("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")).isEqualTo(66);
    }

    @Test
    public void input2() throws IOException {
        assertThat(D08.input2(Files.readString(Utils.of("d08.txt")).trim())).isEqualTo(23266);
    }
}
