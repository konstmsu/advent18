package advent2018;

import java.util.ArrayList;
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

    public static int[] getFirstCrashPoint(List<String> input2) {
        class Context {
            List<int[]> location = new ArrayList<>();
            List<Integer> facing = new ArrayList<>();
            List<Integer> nextCrossingPreference = new ArrayList<>();
            List<Integer> cartOrder = new ArrayList<>();

            char getChar(int[] loc) {
                var x = loc[0];
                var y = loc[1];
                if (y >= 0 && y < input2.size()) {
                    var line = input2.get(y);
                    if (x >= 0 && x < line.length())
                        return line.charAt(x);
                }
                return ' ';
            }

            public Context() {
                for (var y = 0; y < input2.size(); y++)
                    for (var x = 0; x < input2.get(y).length(); x++) {
                        var cartFacing = getCartFacing(input2.get(y).charAt(x));
                        if (cartFacing == null)
                            continue;

                        cartOrder.add(location.size());
                        location.add(new int[]{x, y});
                        facing.add(cartFacing);
                        nextCrossingPreference.add(-1);
                    }
            }

            public int[] findFirstCrash() {
                for (var tick = 0; ; tick++) {
                    cartOrder.sort((o1, o2) -> {
                        var p1 = location.get(o1);
                        var p2 = location.get(o2);
                        return p1[1] == p2[1] ? Integer.compare(p1[0], p2[0]) : Integer.compare(p1[1], p2[1]);
                    });

                    /*System.out.printf("Tick %d%n", tick);
                    for (var l : format())
                        System.out.println(l);*/

                    for (var c : cartOrder) {
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
                        if (collision)
                            return location.get(c);
                    }
                }
            }

            private boolean detectCollision(int cartIndex) {
                var l1 = location.get(cartIndex);
                for (var c : cartOrder) {
                    if (c == cartIndex)
                        continue;

                    var p2 = location.get(c);
                    if (l1[0] == p2[0] && l1[1] == p2[1])
                        return true;
                }
                return false;
            }

            public List<String> format() {
                var lines = input2.stream().map(StringBuilder::new).collect(Collectors.toList());
                var i = 0;
                for (var c : cartOrder) {
                    var p = location.get(c);
                    lines.get(p[1]).setCharAt(p[0], (char) ('A' + i));
                    i++;
                }

                return lines.stream().map(StringBuilder::toString).collect(Collectors.toList());
            }
        }

        var context = new Context();
        return context.findFirstCrash();
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
        return null;
    }
}
