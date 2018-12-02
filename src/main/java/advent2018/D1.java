package advent2018;

import java.util.HashSet;
import java.util.List;

public class D1 {
    public static long findSum(List<String> lines) {
        long sum = 0;
        for(var l: lines)
            sum += Integer.parseInt(l);

        return sum;
    }

    public static long findRepeatedSum(List<Integer> values) {
        long sum = 0;
        var seen = new HashSet<>(List.of(0L));
        int i = 0;
        for(;;) {
            for (int v: values) {
                i++;
                sum += v;
                if (!seen.add(sum)) {
                    System.out.println(String.format("Found repeated at %s%n", i));
                    return sum;
                }
            }
        }
    }
}
