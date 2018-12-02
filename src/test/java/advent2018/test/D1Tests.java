package advent2018.test;

import advent2018.D1;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class D1Tests {

    static <T> T useFile(Function<List<String>, T> test) throws IOException {
        return test.apply(Files.readAllLines(Utils.of("d1i1.txt")));
    }

    @Test
    public void test1() throws IOException {
        assertThat((long)useFile(lines -> D1.findSum(lines))).isEqualTo(502);
    }

    @Test
    public void test2Sample() throws IOException {
        assertThat(D1.findRepeatedSum(List.of(+1, -1))).isEqualTo(0);
        assertThat(D1.findRepeatedSum(List.of(+3, +3, +4, -2, -4))).isEqualTo(10);
        assertThat(D1.findRepeatedSum(List.of(-6, +3, +8, +5, -6))).isEqualTo(5);
        assertThat(D1.findRepeatedSum(List.of(+7, +7, -2, -7, -4))).isEqualTo(14);
        assertThat((long)useFile(lines ->
                D1.findRepeatedSum(lines.stream().map(Integer::parseInt).collect(Collectors.toList()))))
                .isEqualTo(71961L);
    }
}
