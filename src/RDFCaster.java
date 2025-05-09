import java.io.Reader;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class RDFCaster {

    public static void cast(Reader in, AsyncStringWriter out, Function<String, List<String>> rowCaster) {
        Scanner scanner = new Scanner(in).useDelimiter("\n");
        scanner.next(); // ignore headers

        scanner.forEachRemaining((line) -> {
            out.write(rowCaster.apply(line));
        });
    }

}
