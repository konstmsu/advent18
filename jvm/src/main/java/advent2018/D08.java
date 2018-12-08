package advent2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D08 {
    static class Node {
        final List<Node> children = new ArrayList<>();
        final List<Integer> meta = new ArrayList<>();

        public int recursiveMetaSum() {
            var metaSum = 0;

            for (var c : children) {
                metaSum += c.recursiveMetaSum();
            }
            for (var m : meta) {
                metaSum += m;
            }

            return metaSum;
        }

        public int value() {
            if (children.isEmpty()) {
                var metaSum = 0;
                for (var m : meta) {
                    metaSum += m;
                }
                return metaSum;
            }

            var metaSum = 0;
            for (var m : meta) {
                var i = m - 1;
                if (i >= 0 && i < children.size())
                    metaSum += children.get(i).value();
            }

            return metaSum;
        }
    }

    static class Context {
        final int[] values;
        int pos;

        Context(int[] values) {
            this.values = values;
        }

        public Node readNode() {
            var childCount = values[pos++];
            var metaCount = values[pos++];
            Node node = new Node();

            for (var i = 0; i < childCount; i++) {
                node.children.add(readNode());
            }

            for (var i = 0; i < metaCount; i++) {
                node.meta.add(values[pos++]);
            }

            return node;
        }
    }

    public static int input1(String input) {

        var context = new Context(Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray());
        return context.readNode().recursiveMetaSum();
    }

    public static int input2(String input) {
        var context = new Context(Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray());
        return context.readNode().value();
    }
}
