package utilities.allure;

import utilities.LoggingManager;
import java.io.*;
import java.nio.file.Files;

public class AllureReportHelper {

    private static final File allureReportDir = new File("target/allure-results");

    private AllureReportHelper() {

    }

    public static void cleanAllureReport(){
        if (allureReportDir.exists()) {
            File[] reportFiles = allureReportDir.listFiles();
            assert reportFiles != null;
            for (File file : reportFiles) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException e) {
                        LoggingManager.error("File Not Found, " + e.getMessage());
                    }
                }
            }
            LoggingManager.info("Allure Report Cleaned successfully");
        }
        else {
            LoggingManager.info("Allure Report Already Cleaned");
        }
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException e) {
                        LoggingManager.error("File Not Found, " + e.getMessage());
                    }
                }
            }
        }
        try {
            Files.delete(directory.toPath());
        } catch (IOException e) {
            LoggingManager.error("Directory Not Found, " + e.getMessage());
        }
    }

}
