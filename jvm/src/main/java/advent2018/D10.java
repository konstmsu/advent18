package advent2018;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class D10 {
    public static class Solution {
        public final List<String> message;
        public final int waitTime;

        public Solution(List<String> message, int waitTime) {
            this.message = message;
            this.waitTime = waitTime;
        }
    }

    public static Solution solve(List<String> input) {
        var pointCount = input.size();
        var px = new int[pointCount];
        var py = new int[pointCount];
        var vx = new int[pointCount];
        var vy = new int[pointCount];

        var regex = Pattern.compile("[+-]?\\d+");
        for (var i = 0; i < input.size(); i++) {
            var numbers = regex.matcher(input.get(i)).results().mapToInt(v -> Integer.parseInt(v.group())).toArray();
            px[i] = numbers[0];
            py[i] = numbers[1];
            vx[i] = numbers[2];
            vy[i] = numbers[3];
        }

        int previousLineSpread = Integer.MAX_VALUE;
        int[] previousX = null;
        int[] previousY = null;

        for (var t = 0; ; t++) {
            var x = new int[pointCount];
            var y = new int[pointCount];
            var maxY = Integer.MIN_VALUE;
            var minY = Integer.MAX_VALUE;
            for (var i = 0; i < pointCount; i++) {
                x[i] = px[i] + t * vx[i];
                y[i] = py[i] + t * vy[i];
                maxY = Math.max(maxY, y[i]);
                minY = Math.min(minY, y[i]);
            }

            var lineSpread = maxY - minY;
            if (lineSpread <= previousLineSpread) {
                previousLineSpread = lineSpread;
                previousX = x;
                previousY = y;
                continue;
            }

            return new Solution(constructImage(previousX, previousY), t - 1);
        }
    }

    private static List<String> constructImage(int[] x, int[] y) {
        var xbase = Ints.min(x);
        var ybase = Ints.min(y);
        var lineCount = 1 + Ints.max(y) - ybase;
        var lineWidth = 1 + Ints.max(x) - xbase;

        var lines = new char[lineCount][];
        for (var l = 0; l < lineCount; l++) {
            lines[l] = new char[lineWidth];
            Arrays.fill(lines[l], ' ');
        }

        for (var p = 0; p < x.length; p++) {
            lines[y[p] - ybase][x[p] - xbase] = '#';
        }

        return Stream.concat(Stream.of(""), Arrays.stream(lines).map(String::new)).collect(Collectors.toList());
    }
}
