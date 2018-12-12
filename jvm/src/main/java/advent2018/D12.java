package advent2018;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class D12 {
    public static long solve1(List<String> rows) {
        return solve(rows, 20);
    }

    public static long solve2(List<String> rows) {
        return solve(rows, 50_000_000_000L);
    }

    private static long solve(List<String> rows, long generationCount) {
        var input = rows.get(0).substring("initial state: ".length());
        var rules = rows.stream().skip(2).map(r -> r.split(" => ")).collect(Collectors.toList());
        return solve(input, rules, generationCount);
    }

    public static long solve(String input, List<String[]> rules, long generationCount) {
        var seen = new HashMap<String, Long>();
        var generationZeroOffsets = new HashMap<Long, Long>();
        var zeroOffset = new long[1];
        var current = input;

        for (long g = 0; g < generationCount; g++) {
            current = carvePlants(current, zeroOffset);

            if (seen.containsKey(current)) {
                long loopStart = seen.get(current);
                System.out.printf("Looped generations %d-%d, zero offset %d%n", loopStart, g, zeroOffset[0]);
                if (g - loopStart != 1) {
                    throw new IllegalArgumentException("All calculations are based on loop of size 1");
                }
                var previousZeroOffset = generationZeroOffsets.get(loopStart);
                var loopZeroOffset = zeroOffset[0] - previousZeroOffset;
                var remainingGenerations = generationCount - g;
                var remainingOffset = remainingGenerations * loopZeroOffset;
                zeroOffset[0] += remainingOffset;
                // let's imaging it's looped at g == 1
                // loopOffset == 1
                //
                break;
            }

            seen.put(current, g);
            generationZeroOffsets.put(g, zeroOffset[0]);
            System.out.println(current);
            current = applyRules(current, rules);
        }

        long sum = 0;
        for (var i = 0; i < current.length(); i++) {
            if (current.charAt(i) == '#') {
                sum += i - zeroOffset[0];
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

    static String carvePlants(String input, long[] offsetRef) {
        var firstPlant = input.indexOf('#');
        var fix = stringOf('.', 4);
        offsetRef[0] += fix.length() - firstPlant;
        return fix + input.substring(firstPlant, input.lastIndexOf('#') + 1) + fix;
    }
}
