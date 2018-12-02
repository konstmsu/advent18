import java.nio.file.Path;

public class Inputs {
    private Inputs() {
    }

    public static Path of(String fileName) {
        return Path.of("src/main/resources", fileName);
    }
}
