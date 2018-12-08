package advent2018;

import com.google.common.primitives.Ints;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
            for (var c : prerequisites.keySet()) {
                if (prerequisites.get(c).isEmpty()) {
                    result += c;
                    prerequisites.remove(c);
                    for (var s : prerequisites.values()) {
                        s.remove(c);
                    }
                    break;
                }
            }
        }
        return result;
    }

    public static int solve2(int workerCount, int durationOffset, List<String> rows) {
        TreeMap<Character, HashSet<Character>> prerequisites = new TreeMap<>();
        for (var r : rows) {
            char first = r.charAt(5);
            char then = r.charAt(36);
            prerequisites.computeIfAbsent(first, v -> new HashSet<>());
            prerequisites.computeIfAbsent(then, v -> new HashSet<>()).add(first);
        }
        var totalTime = 0;
        var workers = new int[workerCount];
        var assignments = new Character[workerCount];

        while (!prerequisites.isEmpty()) {
            Character nextJob = null;
            for (var c : prerequisites.keySet()) {
                if (prerequisites.get(c).isEmpty()) {
                    nextJob = c;
                    break;
                }
            }

            if (nextJob != null) {
                var foundWorker = false;
                for (var j = 0; j < workers.length; j++) {
                    if (workers[j] == 0) {
                        workers[j] = nextJob - 'A' + 1 + 60;
                        assignments[j] = nextJob;
                        prerequisites.remove(nextJob);
                        foundWorker = true;
                        break;
                    }
                }
                if (foundWorker)
                    continue;
            }

            var rem = Integer.MAX_VALUE;
            for (var i = 0; i < workerCount; i++) {
                if (assignments[i] != null)
                    rem = Math.min(rem, workers[i]);
            }

            assert rem > 0;

            totalTime += rem;
            for (var j = 0; j < workers.length; j++) {
                if (assignments[j] == null)
                    continue;

                workers[j] -= rem;
                if (workers[j] == 0) {
                    for (var s : prerequisites.values()) {
                        s.remove(assignments[j]);
                    }
                    assignments[j] = null;
                }
            }
        }

        return totalTime + Ints.max(workers);
    }
}
