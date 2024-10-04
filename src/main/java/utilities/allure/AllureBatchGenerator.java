package utilities.allure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.lang.SystemUtils;
import utilities.LoggingManager;

public class AllureBatchGenerator {

    private AllureBatchGenerator() {}

    public static void generateBatFile() {
        Path file;
        if (SystemUtils.IS_OS_WINDOWS) {
            file = Paths.get("generateAllureReport.bat");
        } else {
            file = Paths.get("generateAllureReport.sh");
        }
        if (!Files.exists(file)) {
            try {
                Files.writeString(file,
                        "@echo off\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.writeString(file,
                        "allure serve target/allure-results\n", StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                Files.writeString(file,
                        "pause\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.writeString(file,
                        "exit", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                LoggingManager.error("Unable to create Allure Batch File" + e.getMessage());
            }
        }
    }
}
