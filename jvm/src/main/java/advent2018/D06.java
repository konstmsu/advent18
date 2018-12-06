package advent2018;

import java.util.Arrays;

public class D06 {
    public static int solve1(int... pairs) {
        int minX, maxX, minY, maxY;
        minX = maxX = pairs[0];
        minY = maxY = pairs[1];

        var points = new int[pairs.length / 2];
        for (var i = 0; i < pairs.length; i++) {
            points[i] = pairs[i] * 1000 + pairs[i + 1];
        }
    }
}
