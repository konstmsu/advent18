package advent2018;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class Utils {
    private Utils() {
    }

    public static Path of(String fileName) {
        return Paths.get("../_input", fileName);
    }

    public static <T> T timeIt(Supplier<T> action) {
        long start = System.nanoTime();
        T result = action.get();
        long end = System.nanoTime();
        System.out.printf("Took %.2fs%n", (end - start) * 0.001 * 0.001 * 0.001);
        return result;
    }
}
