package advent2018;

public class D11 {
    public static int getCellPower(int gridNumber, int x, int y) {
        var rackId = x + 10;
        var power = rackId * y;
        power += gridNumber;
        power *= rackId;
        power = (power % 1000) / 100;
        power -= 5;
        return power;
    }

    public static int[] getLargestTotalPowerPosition(int gridNumber) {
        int w = 300;
        int h = 300;
        var context = new Context(gridNumber, w, h);
        var squareSize = 3;
        var level = squareSize - 1;

        context.populateUpTo(level);

        int bestX = -1;
        int bestY = -1;
        int bestTotal = Integer.MIN_VALUE;

        for (var x = 0; x < w - 2; x++)
            for (var y = 0; y < h - 2; y++) {
                var total = context.cache[level][x][y];
                if (total > bestTotal) {
                    bestTotal = total;
                    bestX = x;
                    bestY = y;
                }
            }

        return new int[]{bestX + 1, bestY + 1};
    }

    public static int[] getLargestTotalPowerPositionAndSize(int gridNumber) {
        return getLargestTotalPowerPositionAndSize(gridNumber, 300, 300);
    }

    static class Context {
        final int gridNumber;
        final int w;
        final int h;
        final int[][][] cache;

        Context(int gridNumber, int w, int h) {
            this.gridNumber = gridNumber;
            this.w = w;
            this.h = h;
            cache = new int[Math.min(w, h)][w][h];

            for (var x = 0; x < w; x++)
                for (var y = 0; y < h; y++) {
                    cache[0][x][y] = getCellPower(gridNumber, x + 1, y + 1);
                }
        }

        public int[][] populateUsingLevel0(int level) {
            for (var x = 0; x < w - level; x++)
                for (var y = 0; y < h - level; y++) {
                    int total = 0;
                    for (var xi = 0; xi <= level; xi++)
                        for (var yi = 0; yi <= level; yi++) {
                            total += cache[0][x + xi][y + yi];
                        }
                    cache[level][x][y] = total;
                }

            return cache[level];
        }

        public int[][] populateUpTo(int maxLevel) {
            for (var level = 1; level <= maxLevel; level++)
                populateUsingLowerLevelsLevels(level);

            return cache[maxLevel];
        }

        private int[][] populateUsingLowerLevelsLevels(int level) {
            int squareSize = level + 1;
            var largestDivider = getLargestDivider(squareSize);

            if (largestDivider > 1) {
                for (var x = 0; x <= w - squareSize; x++)
                    for (var y = 0; y <= h - squareSize; y++) {
                        int total = 0;
                        for (var px = 0; px < squareSize; px += largestDivider)
                            for (var py = 0; py < squareSize; py += largestDivider)
                                total += cache[largestDivider - 1][x + px][y + py];

                        cache[level][x][y] = total;
                    }
            } else {
                // Square of prime size
                for (var x = 0; x <= w - squareSize; x++)
                    for (var y = 0; y <= h - squareSize; y++) {
                        var total = cache[level - 1][x + 1][y + 1] + cache[0][x][y];

                        for (var i = 1; i < squareSize; i++) {
                            total += cache[0][x + i][y];
                            total += cache[0][x][y + i];
                        }

                        cache[level][x][y] = total;
                    }
            }

            return cache[level];
        }

        private int getLargestDivider(int number) {
            for (var i = 2; i * i <= number; i++)
                if (number % i == 0)
                    return number / i;

            return 1;
        }

        public int[] getBestSquare() {
            int bestLevel = 0;
            int bestX = 0;
            int bestY = 0;
            int bestValue = Integer.MIN_VALUE;
            for (var level = 1; level < cache.length; level++)
                for (var x = 0; x < w - level; x++)
                    for (var y = 0; y < h - level; y++) {
                        if (cache[level][x][y] > bestValue) {
                            bestLevel = level;
                            bestX = x;
                            bestY = y;
                            bestValue = cache[level][x][y];
                            System.out.printf("Current best is %d, %d, %d with total power %d%n",
                                    x + 1, y + 1, level + 1, bestValue);
                        }
                    }

            return new int[]{bestX + 1, bestY + 1, bestLevel + 1};
        }
    }

    public static int[] getLargestTotalPowerPositionAndSize(int gridNumber, int w, int h) {
        var context = new Context(gridNumber, w, h);
        context.populateUpTo(Math.min(w, h) - 1);
        return context.getBestSquare();
    }
}
