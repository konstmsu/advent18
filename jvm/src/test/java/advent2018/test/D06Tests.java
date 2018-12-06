package advent2018.test;

import advent2018.D06;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class D06Tests {
    @Test
    public void sample1() {
        assertThat(D06.solve1(
                1, 1,
                1, 6,
                8, 3,
                3, 4,
                5, 5,
                8, 9)).isEqualTo(17);
    }
}
