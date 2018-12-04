package advent2018;

import java.util.*;
import java.util.stream.Collectors;

public class D04 {
    public static class Solution {
        public final int solution1;
        public final int solution2;

        public Solution(int solution1, int solution2) {
            this.solution1 = solution1;
            this.solution2 = solution2;
        }
    }

    public static Solution solve(List<String> unorderedLines) {
        var lines = unorderedLines.stream().sorted().collect(Collectors.toList());

        class Context {
            final List<String> lines;
            int lineIndex;
            final HashMap<Integer, List<Integer>> fallAsleep = new HashMap<>();
            final HashMap<Integer, List<Integer>> wakeUp = new HashMap<>();
            Integer currentGuardId;

            Context(List<String> lines) {
                this.lines = lines;
            }

            public Set<Integer> guardIds() {
                return fallAsleep.keySet();
            }

            boolean hasReachedEnd() {
                return lineIndex >= lines.size();
            }

            void moveNext() {
                if (hasReachedEnd())
                    throw new IllegalStateException();

                lineIndex++;
            }

            boolean tryRecordAsNewGuard() {
                var l = getCurrentLine();
                var hash = l.indexOf('#');
                if (hash < 0)
                    return false;
                currentGuardId = Integer.parseInt(l, hash + 1, l.indexOf(' ', hash + 2), 10);
                moveNext();
                return true;
            }

            void recordAsFallAsleep() {
                String cl = getCurrentLine();
                if (!cl.endsWith("falls asleep"))
                    throw new IllegalStateException();

                fallAsleep.computeIfAbsent(currentGuardId, ArrayList::new).add(extractTimestamp(cl));
                moveNext();
            }

            void recordAsWakeUp() {
                String cl = getCurrentLine();
                if (!cl.endsWith("wakes up"))
                    throw new IllegalStateException();

                wakeUp.computeIfAbsent(currentGuardId, ArrayList::new).add(extractTimestamp(cl));
                moveNext();
            }

            public void populate() {
                while (!hasReachedEnd()) {
                    if (!tryRecordAsNewGuard()) {
                        recordAsFallAsleep();
                        recordAsWakeUp();
                    }
                }
            }

            public String getCurrentLine() {
                return lines.get(lineIndex);
            }

            class MinuteSleepTimes {
                public final int minute;
                public final int sleepTimes;

                public MinuteSleepTimes(int minute, int sleepTimes) {
                    this.minute = minute;
                    this.sleepTimes = sleepTimes;
                }
            }

            private int getSolution1() {
                int maxTotalSleep = -1;
                int maxId = -1;
                for (var id: this.guardIds()) {
                    int totalSleep = 0;
                    for (var i = 0; i < this.fallAsleep.get(id).size(); i++)
                        totalSleep += this.wakeUp.get(id).get(i) - this.fallAsleep.get(id).get(i);

                    if(totalSleep > maxTotalSleep) {
                        maxId = id;
                        maxTotalSleep = totalSleep;
                    }
                }

                MinuteSleepTimes longestMinute = getGuardTopSleepingMinute(maxId);

                return longestMinute.minute * maxId;
            }

            private Context.MinuteSleepTimes getGuardTopSleepingMinute(int guardId) {
                int[] sleeps = new int[60];
                var from = this.fallAsleep.get(guardId);
                var to = this.wakeUp.get(guardId);
                for(var i = 0; i < from.size(); i++) {
                    for (var m = from.get(i); m < to.get(i); m++)
                        sleeps[m % 100]++;
                }

                int maxMinute = -1;
                int maxSleep = -1;
                for(var m = 0; m < sleeps.length; m++) {
                    if (maxSleep < sleeps[m]) {
                        maxMinute = m;
                        maxSleep = sleeps[m];
                    }
                }

                return new MinuteSleepTimes(maxMinute, maxSleep);
            }

            public int getSolution2() {
                var topId = -1;
                MinuteSleepTimes topTopMinute = null;

                for(var id : guardIds()){
                    var minute = getGuardTopSleepingMinute(id);
                    if (topTopMinute == null || topTopMinute.sleepTimes<minute.sleepTimes) {
                        topTopMinute = minute;
                        topId = id;
                    }
                }

                return topId * topTopMinute.minute;
            }
        }

        var context = new Context(lines);
        context.populate();

        return new Solution(context.getSolution1(), context.getSolution2());
    }

    public static int extractTimestamp(String line) {
        return Integer.parseInt(line.substring(1, 5) + line.substring(6, 8) + line.substring(9, 11) + line.substring(15, 17));
    }
}
