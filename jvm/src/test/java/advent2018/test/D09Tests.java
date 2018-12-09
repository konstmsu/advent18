package advent2018.test;

import advent2018.D09;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class D09Tests {
    @Test
    public void sample1() {
        assertThat(D09.solve1(9, 25)).isEqualTo(32);
        assertThat(D09.solve1(10, 1618)).isEqualTo(8317);
        assertThat(D09.solve1(13, 7999)).isEqualTo(146373);
        assertThat(D09.solve1(17, 1104)).isEqualTo(2764);
        assertThat(D09.solve1(21, 6111)).isEqualTo(54718);
        assertThat(D09.solve1(30, 5807)).isEqualTo(37305);
    }

    @Test
    public void input1() {
        assertThat(D09.solve1(448, 71628)).isEqualTo(394486);
    }

    @Test
    public void input2() {
        assertThat(D09.solve1(448, 7162800)).isEqualTo(3276488008L);
    }
}
