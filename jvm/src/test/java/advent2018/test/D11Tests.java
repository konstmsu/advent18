package advent2018.test;

import advent2018.D11;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class D11Tests {
    @Test
    public void cellPowerTest() {
        Assertions.assertThat(D11.getCellPower(8, 3, 5)).isEqualTo(4);
        assertThat(D11.getCellPower(57, 122, 79)).isEqualTo(-5);
        assertThat(D11.getCellPower(39, 217, 196)).isEqualTo(0);
        assertThat(D11.getCellPower(71, 101, 153)).isEqualTo(4);
    }

    @Test
    public void sample1() {
        assertThat(D11.getLargestTotalPowerPosition(18)).isEqualTo(new int[]{33, 45});
        assertThat(D11.getLargestTotalPowerPosition(42)).isEqualTo(new int[]{21, 61});
        assertThat(D11.getLargestTotalPowerPosition(18)).isEqualTo(new int[]{33, 45});
    }

    @Test
    public void input1() {
        assertThat(D11.getLargestTotalPowerPosition(5235)).isEqualTo(new int[]{33, 54});
    }

    @Test
    public void sample2() {
        assertThat(D11.getLargestTotalPowerPositionAndSize(18)).isEqualTo(new int[]{90, 269, 16});
        assertThat(D11.getLargestTotalPowerPositionAndSize(42)).isEqualTo(new int[]{232, 251, 12});
    }

    @Test
    public void debug2() {
        int gridNumber = 18;
        compareMethods(18, 4, 5);
        for (var squareSize = 1; squareSize <= 10; squareSize++) {
            for (var gridSize = squareSize; gridSize <= 10; gridSize++) {
                compareMethods(gridNumber, squareSize, gridSize);
            }
        }
    }

    private void compareMethods(int gridNumber, int squareSize, int gridSize) {
        var expected = new D11.Context(gridNumber, gridSize, gridSize).populateUsingLevel0(squareSize - 1);
        var actual = new D11.Context(gridNumber, gridSize, gridSize).populateUpTo(squareSize - 1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void input2() {
        assertThat(D11.getLargestTotalPowerPositionAndSize(5235)).isEqualTo(new int[]{232, 289, 8});
    }
}