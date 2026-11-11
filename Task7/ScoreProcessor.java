
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ScoreProcessor {

    static void createFiles() throws IOException {
        Path folder = Paths.get("score_files");

        Files.createDirectories(folder);

        for (int i = 1; i <= 10; i++) {
            Path file = folder.resolve("score" + i + ".txt");

            // Missing files
            if (i % 100 == 0) {
                Files.deleteIfExists(file);
                continue;
            }

            // Corrupted files
            if (i % 100 == 1) {
                Files.writeString(file, "INVALID");
            } else {
                Files.writeString(file, "75");
            }
        }
    }

    static void processFiles(int threadCount)
            throws Exception {

        ExecutorService service =
            Executors.newFixedThreadPool(threadCount);

        ConcurrentLinkedQueue<String> success =
            new ConcurrentLinkedQueue<>();

        ConcurrentLinkedQueue<String> failure =
            new ConcurrentLinkedQueue<>();

        List<Future<?>> futures = new ArrayList<>();

        long start = System.nanoTime();

        for (int i = 1; i <= 10000; i++) {
            final int id = i;

            futures.add(service.submit(() -> {
                Path file = Paths.get(
                    "score_files", "score" + id + ".txt"
                );

                try {
                    String data = Files.readString(file);
                    int score = Integer.parseInt(data.trim());

                    if (score < 0 || score > 100) {
                        throw new NumberFormatException();
                    }

                    success.add("Student " + id);

                } catch (IOException | NumberFormatException e) {
                    failure.add("Student " + id);
                }

                return null;
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        service.shutdown();

        long end = System.nanoTime();

        System.out.println("\nThread Pool Size: " + threadCount);
        System.out.println("Successful files: " + success.size());
        System.out.println("Failed files: " + failure.size());
        System.out.println("Total files: "
            + (success.size() + failure.size()));

        System.out.println("Execution Time: "
            + (end - start) + " ns");
    }

    public static void main(String[] args)
            throws Exception {

        System.out.println("Creating 10 score files...");
        createFiles();

        processFiles(1);
        processFiles(2);
        processFiles(4);
        processFiles(8);
    }
}