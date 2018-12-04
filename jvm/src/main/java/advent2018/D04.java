package advent2018;

import java.util.List;

public class D04 {
    public static int solve1(List<String> input) {
        return 0;
    }

    public static int extractTimestamp(String line) {
        return Integer.parseInt(line.substring(1, 5) + line.substring(6, 8) + line.substring(9, 11) + line.substring(15, 17));
    }
}
