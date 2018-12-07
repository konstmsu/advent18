package advent2018;

import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D06 {

    public static int solve1(List<String> rows) {
        var c = new Context(rows);
        List<List<Integer>> wx = c.xs.stream().map(v -> Ints.asList(v)).collect(Collectors.toList());
        List<List<Integer>> wy = c.ys.stream().map(v -> Ints.asList(v)).collect(Collectors.toList());

        var generations = new int[c.dx][c.dy];
        var sources = new int[c.dx][c.dy];
        for (var s : sources)
            Arrays.fill(s, -1);
        var counter = new int[wx.size() + 1];

        var equallyFar = wx.size();
        assert equallyFar > 0;
        for (var gen = 1; !wx.stream().flatMap(v -> v.stream()).findAny().isEmpty(); gen++) {
            for (int source = 0; source < wx.size(); source++) {
                assert source != equallyFar;
                var xs = wx.get(source);
                var ys = wy.get(source);
                var nx = new ArrayList<Integer>();
                var ny = new ArrayList<Integer>();
                for (var i = 0; i < xs.size(); i++) {
                    var x = xs.get(i);
                    var y = ys.get(i);
                    if (x < c.minx || y < c.miny || x > c.maxx || y > c.maxy)
                        continue;

                    if (sources[x - c.minx][y - c.miny] == -1) {
                        generations[x - c.minx][y - c.miny] = gen;
                        sources[x - c.minx][y - c.miny] = source;
                        counter[source]++;

                        nx.add(x);
                        nx.add(x);
                        nx.add(x + 1);
                        nx.add(x - 1);

                        ny.add(y + 1);
                        ny.add(y - 1);
                        ny.add(y);
                        ny.add(y);
                    } else if (generations[x - c.minx][y - c.miny] == gen && sources[x - c.minx][y - c.miny] != source) {
                        counter[sources[x - c.minx][y - c.miny]]--;
                        sources[x - c.minx][y - c.miny] = equallyFar;
                    }
                }
                wx.set(source, nx);
                wy.set(source, ny);
            }
        }

        return Ints.max(counter);
    }

    public static int solve2(int maxTotalDistance, List<String> rows) {
        var c = new Context(rows);
        var ax = (int) Math.round(c.xs.stream().mapToInt(Integer::intValue).average().getAsDouble());
        var ay = (int) Math.round(c.ys.stream().mapToInt(Integer::intValue).average().getAsDouble());
        Queue<Integer> wx = new ArrayDeque<>(Ints.asList(ax));
        Queue<Integer> wy = new ArrayDeque<>(Ints.asList(ay));
        HashSet<Integer> seen = new HashSet<>();
        int counter = 0;
        while (!wx.isEmpty()) {
            var x = wx.remove();
            var y = wy.remove();

            var k = x * maxTotalDistance + y;

            if (seen.contains(k))
                continue;

            seen.add(k);

            var d = 0;
            for (var i = 0; i < c.xs.size(); i++) {
                d += Math.abs(x - c.xs.get(i)) + Math.abs(y - c.ys.get(i));
            }

            if (d >= maxTotalDistance)
                continue;

            counter++;
            wx.add(x);
            wx.add(x);
            wx.add(x + 1);
            wx.add(x - 1);

            wy.add(y + 1);
            wy.add(y - 1);
            wy.add(y);
            wy.add(y);
        }

        return counter;
    }

    private static class Context {
        private List<Integer> xs;
        private List<Integer> ys;
        public final int minx;
        public final int miny;
        public final int maxx;
        public final int maxy;
        public final int dx;
        public final int dy;

        public Context(List<String> rows) {
            xs = new ArrayList<>();
            ys = new ArrayList<>();
            var tminx = Integer.MAX_VALUE;
            var tminy = Integer.MAX_VALUE;
            var tmaxx = Integer.MIN_VALUE;
            var tmaxy = Integer.MIN_VALUE;
            for (var r : rows) {
                var parts = r.split(", ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                xs.add(x);
                ys.add(y);
                tminx = Math.min(tminx, x);
                tminy = Math.min(tminy, y);
                tmaxx = Math.max(tmaxx, x);
                tmaxy = Math.max(tmaxy, y);
            }
            minx = tminx;
            maxx = tmaxx;
            miny = tminy;
            maxy = tmaxy;
            dx = tmaxx - tminx + 1;
            dy = tmaxy - tminy + 1;
        }
    }
}
