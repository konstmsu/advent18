package advent2018;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class D13 {
    static int[][] direction = new int[][]{
            new int[]{-1, 0},
            new int[]{0, -1},
            new int[]{1, 0},
            new int[]{0, 1}
    };

    static Integer getCartFacing(char c) {
        switch (c) {
            case '<':
                return 0;
            case '^':
                return 1;
            case '>':
                return 2;
            case 'v':
                return 3;
            default:
                return null;
        }
    }

    static class Context {
        List<int[]> location = new ArrayList<>();
        List<Integer> facing = new ArrayList<>();
        List<Integer> nextCrossingPreference = new ArrayList<>();
        List<Integer> cartOrder = new ArrayList<>();
        List<int[]> collisions = new ArrayList<>();
        int[] lastCartLocation;
        final List<String> input;

        char getChar(int[] loc) {
            var x = loc[0];
            var y = loc[1];
            if (y >= 0 && y < input.size()) {
                var line = input.get(y);
                if (x >= 0 && x < line.length())
                    return line.charAt(x);
            }
            return ' ';
        }

        public Context(List<String> input) {
            this.input = input;

            for (var y = 0; y < this.input.size(); y++)
                for (var x = 0; x < this.input.get(y).length(); x++) {
                    var cartFacing = getCartFacing(this.input.get(y).charAt(x));
                    if (cartFacing == null)
                        continue;

                    cartOrder.add(location.size());
                    location.add(new int[]{x, y});
                    facing.add(cartFacing);
                    nextCrossingPreference.add(-1);
                }
        }

        public void run() {
            for (var tick = 0; ; tick++) {
                cartOrder.sort((o1, o2) -> {
                    var p1 = location.get(o1);
                    var p2 = location.get(o2);
                    return p1[1] == p2[1] ? Integer.compare(p1[0], p2[0]) : Integer.compare(p1[1], p2[1]);
                });

                   /* System.out.printf("Tick %d%n", tick);
                    for (var l : format())
                        System.out.println(l);*/

                var it = 0;
                while (it < cartOrder.size()) {
                    var c = cartOrder.get(it);
                    var currentChar = getChar(location.get((c)));
                    int move;
                    if (currentChar == '-' || currentChar == '|'
                            || currentChar == '<' || currentChar == '>'
                            || currentChar == '^' || currentChar == 'v') {
                        move = facing.get(c);
                    } else if (currentChar == '/') {
                        switch (facing.get(c)) {
                            case 0:
                                move = 3;
                                break;
                            case 1:
                                move = 2;
                                break;
                            case 2:
                                move = 1;
                                break;
                            case 3:
                                move = 0;
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else if (currentChar == '\\') {
                        switch (facing.get(c)) {
                            case 0:
                                move = 1;
                                break;
                            case 1:
                                move = 0;
                                break;
                            case 2:
                                move = 3;
                                break;
                            case 3:
                                move = 2;
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else if (currentChar == '+') {
                        move = mod(facing.get(c) + nextCrossingPreference.get(c), 4);
                        nextCrossingPreference.set(c, mod(nextCrossingPreference.get(c) + 2, 3) - 1);
                    } else {
                        throw new IllegalArgumentException();
                    }

                    location.set(c, getNextLocation(location.get(c), move));
                    facing.set(c, move);

                    var collision = detectCollision(c);
                    if (collision != null) {
                        collisions.add(location.get(c));
                        int i1 = cartOrder.indexOf(collision);
                        int i2 = cartOrder.indexOf(c);
                        cartOrder.removeAll(List.of(c, collision));
                        if (i1 < i2)
                            it--;
                    } else {
                        it++;
                    }
                }

                if (cartOrder.size() == 1) {
                    lastCartLocation = location.get(cartOrder.get(0));
                    break;
                }

                if (cartOrder.isEmpty())
                    break;
            }
        }

        private Integer detectCollision(int cartIndex) {
            var l1 = location.get(cartIndex);
            for (var c : cartOrder) {
                if (c == cartIndex)
                    continue;

                var p2 = location.get(c);
                if (l1[0] == p2[0] && l1[1] == p2[1])
                    return c;
            }
            return null;
        }

        public List<String> format() {
            var lines = input.stream().map(StringBuilder::new).collect(Collectors.toList());
            var i = 0;
            for (var c : cartOrder) {
                var p = location.get(c);
                lines.get(p[1]).setCharAt(p[0], (char) ('A' + i));
                i++;
            }

            return lines.stream().map(StringBuilder::toString).collect(Collectors.toList());
        }
    }

    public static int[] getFirstCrashPoint(List<String> input) {
        var context = new Context(input);
        context.run();
        return context.collisions.get(0);
    }

    public static int mod(int l, int r) {
        var v = l % r;
        return v < 0 ? v + r : v;
    }

    private static int[] getNextLocation(int[] position, int dir) {
        var d = direction[dir];
        return new int[]{position[0] + d[0], position[1] + d[1]};
    }

    public static int[] getLastCartPoint(List<String> input) {
        var context = new Context(input);
        context.run();
        return context.lastCartLocation;
    }
}
