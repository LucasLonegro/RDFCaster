import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class RDFCaster {

    public static void cast(InputStream in, AsyncStringWriter out, Function<String, List<String>> rowCaster) {
        Scanner scanner = new Scanner(in).useDelimiter("\n");
        scanner.next(); // ignore headers

        scanner.forEachRemaining((line) -> {
            out.write(rowCaster.apply(line));
        });
    }

    public static void main(String[] args) {
        try (InputStream in = new FileInputStream("Resources/Assigned_Hours.csv");
             Writer out = new FileWriter("out.txt")) {
            AsyncStringWriter asyncWriter = new AsyncStringWriter(out);
            cast(in, asyncWriter, Casters::noopCaster);
            asyncWriter.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
