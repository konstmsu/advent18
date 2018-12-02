package advent2018.test;

import advent2018.D2;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static advent2018.Utils.timeIt;
import static org.assertj.core.api.Assertions.assertThat;

public class D2Tests {
    @Test
    public void sample1() {
        assertThat(D2.getChecksum(List.of(
                "abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab"))).isEqualTo(12);
    }

    @Test
    public void input1() throws IOException {
        assertThat(D2.getChecksum(Files.readAllLines(Utils.of("D02.txt")))).isEqualTo(6474);
    }

    @Test
    public void sample2() {
        assertThat(D2.getDifferingLetters(List.of(
                "abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz"))).isEqualTo("fgij");
    }

    @Test
    public void input2() throws IOException {
        List<String> values = Files.readAllLines(Utils.of("D02.txt"));
        String result = timeIt(() -> D2.getDifferingLetters(values));
        assertThat(result).isEqualTo("mxhwoglxgeauywfkztndcvjqr");
    }
}
