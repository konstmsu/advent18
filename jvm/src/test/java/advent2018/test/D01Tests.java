package advent2018.test;

import advent2018.D01;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class D01Tests {

    static <T> T useFile(Function<List<String>, T> test) throws IOException {
        return test.apply(Files.readAllLines(Utils.of("d01.txt")));
    }

    @Test
    public void test1() throws IOException {
        assertThat((long)useFile(D01::findSum)).isEqualTo(502);
    }

    @Test
    public void test2Sample() throws IOException {
        assertThat(D01.findRepeatedSum(Arrays.asList(+1, -1))).isEqualTo(0);
        assertThat(D01.findRepeatedSum(Arrays.asList(+3, +3, +4, -2, -4))).isEqualTo(10);
        assertThat(D01.findRepeatedSum(Arrays.asList(-6, +3, +8, +5, -6))).isEqualTo(5);
        assertThat(D01.findRepeatedSum(Arrays.asList(+7, +7, -2, -7, -4))).isEqualTo(14);
        assertThat((long)useFile(lines ->
                D01.findRepeatedSum(lines.stream().map(Integer::parseInt).collect(Collectors.toList()))))
                .isEqualTo(71961L);
    }
}
