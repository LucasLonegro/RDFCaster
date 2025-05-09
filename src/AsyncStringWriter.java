import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class AsyncStringWriter {

    private final ExecutorService writerExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final BufferedWriter buffered;
    private final Object writeLock = new Object();

    public AsyncStringWriter(Writer out) {
        this.buffered = new BufferedWriter(out);
    }

    public void write(List<String> lines) {
        lines.forEach((s) ->
                writerExecutor.submit(() -> {
                    try {
                        synchronized (writeLock) {
                            buffered.write(s);
                            buffered.newLine();
                            buffered.flush();
                        }
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                }));
    }

    public void shutdown() {
        writerExecutor.shutdown();
        try {
            if (!writerExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                writerExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            writerExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
