import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestClass {
    public void foobar() throws IOException {
        Path path = Paths.get("test.txt");
        String lines = Files.readAllLines(path).stream().collect(Collectors.joining(","));

        System.out.println(lines);
    }
}
