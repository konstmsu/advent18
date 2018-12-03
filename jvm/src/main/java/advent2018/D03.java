package advent2018;

import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class D03 {
    public final int overlaps;
    public final int intactId;

    public D03(int overlaps, int intactId) {
        this.overlaps = overlaps;
        this.intactId = intactId;
    }

    public static D03 process(List<String> input) {
        var claims = parseClaims(input);
        var intact = claims.stream().map(c -> c.id).collect(Collectors.toSet());

        var lastClaim = new int[1000][1000];
        var layers = new int[1000][1000];
        var overlaps = 0;
        for (var claim : claims) {
            for (var y = 0; y < claim.height; y++)
                for (var x = 0; x < claim.width; x++) {
                    int previous = lastClaim[claim.topOffset + y][claim.leftOffset + x];
                    if (previous != 0) {
                        intact.remove(previous);
                        intact.remove(claim.id);
                    }
                    lastClaim[claim.topOffset + y][claim.leftOffset + x] = claim.id;
                    if (1 == layers[claim.topOffset + y][claim.leftOffset + x]++)
                        overlaps++;
                }
        }

        return new D03(overlaps, Iterables.getOnlyElement(intact));
    }

    static class Claim {
        final int id;
        final int leftOffset;
        final int topOffset;
        final int width;
        final int height;

        public Claim(int id, int leftOffset, int topOffset, int width, int height) {
            this.id = id;
            this.leftOffset = leftOffset;
            this.topOffset = topOffset;
            this.width = width;
            this.height = height;
        }
    }

    @NotNull
    private static List<Claim> parseClaims(List<String> input) {
        Function<String, Claim> parse = line -> {
            var parts = line.split("[# @,:x]+");
            return new Claim(
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    Integer.parseInt(parts[5])
            );
            // "#1 @ 1,3: 4x4"
        };

        return input.stream().map(parse).collect(Collectors.toList());
    }
}
