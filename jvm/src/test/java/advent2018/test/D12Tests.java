package advent2018.test;

import advent2018.D12;
import advent2018.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class D12Tests {
    @Test
    public void applyRulesTest() {
        var target = D12.applyRules(".....", List.of(new String[][]{
                new String[]{".....", "#"}}));

        assertThat(target).isEqualTo("..#..");
    }

    @Test
    public void applyRules2Test() {
        var target = D12.applyRules("..###.", List.of(new String[][]{
                new String[]{"..###", "#"},
                new String[]{".###.", "#"},
        }));
        assertThat(target).isEqualTo("..##..");
    }

    @Test
    public void applyRules3Test() {
        var rules = List.of(new String[][]{new String[]{"...##", "#"}});

        assertThat(D12.applyRules("...###...###..", rules))
                .isEqualTo("..#.....#.....");

        assertThat(D12.applyRules("..#..#.#..##......###...###..", rules))
                .isEqualTo(".................#.....#.....");
    }

    @Test
    public void sample1() {
        assertThat(D12.solve1(List.of(
                "initial state: #..#.#..##......###...###",
                "",
                "...## => #",
                "..#.. => #",
                ".#... => #",
                ".#.#. => #",
                ".#.## => #",
                ".##.. => #",
                ".#### => #",
                "#.#.# => #",
                "#.### => #",
                "##.#. => #",
                "##.## => #",
                "###.. => #",
                "###.# => #",
                "####. => #"
        ))).isEqualTo(325);
    }

    @Test
    public void input1() throws IOException {
        assertThat(D12.solve1(Files.readAllLines(Utils.of("d12.txt")))).isEqualTo(1623);
    }

    @Test
    public void input2() throws IOException {
        assertThat(D12.solve2(Files.readAllLines(Utils.of("d12.txt")))).isEqualTo(42);
    }
}
