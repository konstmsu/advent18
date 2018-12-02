package advent2018;

import java.nio.file.Path;
import java.util.function.Supplier;

public class Utils {
    private Utils() {
    }

    public static Path of(String fileName) {
        return Path.of("../_input", fileName);
    }

    public static <T> T timeIt(Supplier<T> action) {
        var start = System.nanoTime();
        var result = action.get();
        var end = System.nanoTime();
        System.out.printf("Took %.2fs%n", (end - start) * 0.001 * 0.001 * 0.001);
        return result;
    }
}
