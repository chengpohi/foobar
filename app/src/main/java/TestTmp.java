import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * scala99
 * Created by chengpohi on 6/25/16.
 */
public class TestTmp {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 20000; i++) {
            Files.lines(Paths.get("build.sbt"));
        }
    }
}

