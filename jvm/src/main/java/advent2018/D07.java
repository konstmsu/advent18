package advent2018;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class D07 {
    public static String solve1(List<String> rows) {
        TreeMap<Character, HashSet<Character>> prerequisites = new TreeMap<>();
        for (var r : rows) {
            char first = r.charAt(5);
            char then = r.charAt(36);
            prerequisites.computeIfAbsent(first, v -> new HashSet<>());
            prerequisites.computeIfAbsent(then, v -> new HashSet<>()).add(first);
        }
        var result = "";
        while (!prerequisites.isEmpty()) {
            var found = false;
            for (var c : prerequisites.keySet()) {
                if (prerequisites.get(c).isEmpty()) {
                    result += c;
                    prerequisites.remove(c);
                    for (var s : prerequisites.values()) {
                        s.remove(c);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalStateException();
            }
        }
        return result;
    }
}
