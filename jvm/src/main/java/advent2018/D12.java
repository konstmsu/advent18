package advent2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class D12 {
    public static int solve1(List<String> rows) {
        var input = rows.get(0).substring("initial state: ".length());
        var rules = rows.stream().skip(2).map(r -> r.split(" => ")).collect(Collectors.toList());

        var generationCount = 20;
        var zero = generationCount * 2;
        var previous = stringOf('.', zero) + input + stringOf('.', zero);
        for (var g = 0; g < generationCount; g++) {
            System.out.println(previous);
            var next = applyRules(previous, rules);
            previous = next;
        }

        var sum = 0;
        for (var i = 0; i < previous.length(); i++) {
            if (previous.charAt(i) == '#') {
                sum += i - zero;
            }
        }

        return sum;
    }

    private static boolean matches(CharSequence rule, String previous, int prevousOffset) {
        for (var i = 0; i < rule.length(); i++)
            if (rule.charAt(i) != previous.charAt(prevousOffset + i))
                return false;
        return true;
    }

    private static String stringOf(char fill, int size) {
        return new String(new char[size]).replace('\0', fill);
    }

    public static String applyRules(String previous, List<String[]> rules) {
        int ruleLength = rules.get(0)[0].length();
        var ruleOffset = ruleLength / 2;
        var next = new StringBuilder(stringOf('.', previous.length()));
        for (var i = 0; i <= next.length() - ruleLength; i++) {
            int finalI = i;
            Optional<Character> ruleCharacter = rules.stream()
                    .filter(r -> matches(r[0], previous, finalI))
                    .findFirst()
                    .map(r -> r[1].charAt(0));

            next.setCharAt(i + ruleOffset, ruleCharacter.orElse('.'));
        }

        return next.toString();
    }

    static String carvePlants(String input, int) {
        return stringOf('.', 4)
                + input.substring(input.indexOf('#'), input.lastIndexOf('#'))
                + stringOf('.', 4);
    }

    public static int solve2(List<String> rows) {
        var input = rows.get(0).substring("initial state: ".length());
        var rules = rows.stream().skip(2).map(r -> r.split(" => ")).collect(Collectors.toList());

        var generationCount = 50_000_000_000L;
        var zero = 5;
        var previous = carvePlants(input);

        var seen = new HashMap<String, Long>();

        for (var g = 0L; g < generationCount; g++) {
            if (seen.containsKey(previous)) {
                System.out.printf("Looped generations [%d-%d]%n", seen.get(previous), g);
                if (g - seen.get(previous) != 1)
                    throw new IllegalArgumentException();
                break;
            } else {
                seen.put(previous, g);
            }
            var next = applyRules(previous, rules);
            previous = next;
        }

        var sum = 0;
        for (var i = 0; i < previous.length(); i++) {
            if (previous.charAt(i) == '#') {
                sum += i - zero;
            }
        }

        return sum;
    }
}
