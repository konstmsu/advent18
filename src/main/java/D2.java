import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class D2 {
    public static int getChecksum(Iterable<String> ids) {
        int twos = 0;
        int threes = 0;
        for (var id : ids) {
            var groups = id.chars().boxed().collect(groupingBy(c -> c, counting()));

            if (groups.containsValue(3L))
                threes++;

            if (groups.containsValue(2L))
                twos++;
        }
        return twos * threes;
    }

    public static String getDifferingLetters(List<String> values) {
        for (var l = 0; l < values.size(); l++)
            for (var r = l + 1; r < values.size(); r++) {
                var sl = values.get(l);
                var sr = values.get(r);
                assert sl.length() == sr.length();
                for (var i = 0; i < sl.length(); i++) {
                    var lhead = sl.substring(0, i);
                    var ltail = sl.substring(i + 1);
                    if (lhead.equals(sr.substring(0, i)) && ltail.equals(sr.substring(i+1)))
                        return lhead + ltail;
                }
            }

        throw new IllegalArgumentException();
    }
}
