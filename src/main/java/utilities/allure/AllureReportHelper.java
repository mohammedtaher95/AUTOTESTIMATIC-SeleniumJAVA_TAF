package utilities.allure;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;

public class AllureReportHelper {

    private static final File allureReportDir = new File("target/allure-results");

    private AllureReportHelper() {

    }

    public static void cleanReport(){
        if (allureReportDir.exists()) {

            Queue<Path> queue = new ArrayDeque<>();
            queue.add(allureReportDir.toPath());

            // Loop through the queue until it is empty
            while (!queue.isEmpty()) {
                Path currentDir = queue.remove();

                // Loop through the contents of the directory
                try (var stream = Files.list(currentDir)) {
                    stream.forEach(path -> {
                        try {
                            if (Files.isDirectory(path)) {
                                queue.add(path);
                            }
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Allure Report Already Cleaned");
                }

                // Delete the directory
                try {
                    Files.deleteIfExists(currentDir);
                } catch (IOException e) {
                    System.out.println("Allure Report Already Cleaned");
                }
            }
        }
    }
}
