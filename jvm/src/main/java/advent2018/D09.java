package advent2018;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class D09 {
    static class Node {
        final int value;
        private Node previous;
        private Node next;

        Node(int value) {
            this.value = value;
            this.previous = this;
            this.next = this;
        }

        Node(int value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        Node insertAfter(int value) {
            var node = new Node(value, this, next);
            next.previous = node;
            this.next = node;
            return node;
        }

        void remove() {
            previous.next = this.next;
            next.previous = this.previous;
        }
    }

    public static long solve1(int elfCount, int lastMarble) {
        var current = new Node(0);
        var scores = new long[elfCount];
        for (var c = 1; c <= lastMarble; c++) {
            if (c%23 == 0) {
                var elfIndex = (c - 1) % elfCount;
                var minus7 = current
                        .previous
                        .previous
                        .previous
                        .previous
                        .previous
                        .previous
                        .previous;
                scores[elfIndex] += c + minus7.value;
                current = minus7.next;
                minus7.remove();
            }
            else {
                current = current.next.insertAfter(c);
            }
        }

        return Longs.max(scores);
    }
}
