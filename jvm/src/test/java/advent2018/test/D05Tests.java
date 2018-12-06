package advent2018.test;

import advent2018.D05;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class D05Tests {
    @Test
    public void sample1() {
        assertThat(D05.process1("aA")).isEqualTo("");
        assertThat(D05.process1("abBA")).isEqualTo("");
        assertThat(D05.process1("abAB")).isEqualTo("abAB");
        assertThat(D05.process1("aabAAB")).isEqualTo("aabAAB");
        assertThat(D05.process1("dabAcCaCBAcCcaDA")).isEqualTo("dabCBAcaDA");
    }

    @Test
    public void input1() throws IOException {
        assertThat(D05.process1(Files.readString(Utils.of("d05.txt")).trim()).length()).isEqualTo(9462);
    }

    @Test
    public void sample2() {
        assertThat(D05.removeAndReact("dabAcCaCBAcCcaDA", "a")).isEqualTo("dbCBcD");
        assertThat(D05.removeAndReact("dabAcCaCBAcCcaDA", "B")).isEqualTo("daCAcaDA");
        assertThat(D05.removeAndReact("dabAcCaCBAcCcaDA", "c")).isEqualTo("daDA");
        assertThat(D05.removeAndReact("dabAcCaCBAcCcaDA", "d")).isEqualTo("abCBAc");
        assertThat(D05.process2("dabAcCaCBAcCcaDA").length()).isEqualTo(4);
    }

    @Test
    public void input2() throws IOException {
        assertThat(D05.process2(Files.readString(Utils.of("d05.txt")).trim()).length()).isEqualTo(4952);
    }
}
