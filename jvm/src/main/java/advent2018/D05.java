package advent2018;

import com.google.common.primitives.Chars;

import java.util.HashSet;
import java.util.stream.Collectors;

public class D05 {
    public static String process1(String input) {

        var result = new StringBuilder(input);
        boolean madeReplacement;

        do {
            madeReplacement = false;
            for (var i = 0; i < result.length() - 1; ) {
                var a = result.charAt(i);
                var b = result.charAt(i + 1);
                if (a != b && Character.toUpperCase(a) == Character.toUpperCase(b)) {
                    result.delete(i, i + 2);
                    madeReplacement = true;
                    if (i > 0)
                        i--;
                } else {
                    i++;
                }
            }
        } while (madeReplacement);

        return result.toString();
    }

    public static String removeAndReact(String input, String remove) {

        return process1(input.replaceAll("(?i)" + remove, ""));
    }

    public static String process2(String input) {
        var chars = input.toUpperCase().chars().boxed().collect(Collectors.toSet());

        String bestResult = null;

        for (var c : chars) {
            var result = removeAndReact(input, Character.toString(c));
            if (bestResult == null || bestResult.length() > result.length()) {
                bestResult = result;
            }
        }

        return bestResult;
    }
}
