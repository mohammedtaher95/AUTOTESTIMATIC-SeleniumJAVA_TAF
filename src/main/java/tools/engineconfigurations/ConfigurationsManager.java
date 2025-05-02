package tools.engineconfigurations;

import static tools.engineconfigurations.Configurations.executionOptions;
import static tools.engineconfigurations.Configurations.log4j;
import static tools.engineconfigurations.Configurations.reporting;
import static tools.engineconfigurations.Configurations.testNG;
import static tools.engineconfigurations.Configurations.timeouts;
import static tools.engineconfigurations.Configurations.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.aeonbits.owner.ConfigFactory;
import utilities.LoggingManager;

public class ConfigurationsManager {

    private ConfigurationsManager() {

    }

    static String header = "#######################################################";

    private static File configurationsFile;
    private static File log4jFile;

    static String propertiesDirectoryPath = "src/main/resources/properties/";
    static String configurationsFilePath = "src/main/resources/properties/Configurations.properties";
    static String log4jPath = "src/main/resources/properties/log4j2.properties";

    public static void initialize() {

        LoggingManager.info("Initializing Properties........");
        Configurations.executionOptions = ConfigFactory.create(ExecutionOptions.class);
        web = ConfigFactory.create(WebCapabilities.class);
        reporting = ConfigFactory.create(Reporting.class);
        testNG = ConfigFactory.create(TestNg.class);
        log4j = ConfigFactory.create(Log4j.class);
        timeouts = ConfigFactory.create(Timeouts.class);

        generateDefaultConfigurations();
    }

    public static void reloadConfigurations() {
        LoggingManager.info("Reloading Properties.....");
        executionOptions.reload();
        web.reload();
        reporting.reload();
        timeouts.reload();
    }

    private static void generateDefaultConfigurations() {
        LoggingManager.info("Checking if Properties files exist.....");
        File propertiesDirectory = new File(propertiesDirectoryPath);
        configurationsFile = new File(configurationsFilePath);
        log4jFile = new File(log4jPath);

        if (!propertiesDirectory.exists()) {
            boolean created = propertiesDirectory.mkdirs();
            if (created) {
                LoggingManager.info("Directory Created");
            }
        }

        try {
            if (!configurationsFile.exists()) {
                printHeader(configurationsFile);
                FileOutputStream outputStream = new FileOutputStream(configurationsFilePath, true);
                executionOptions.store(outputStream, "#### Execution Settings");
                testNG.store(outputStream, "\n#### TestNG Options");
                reporting.store(outputStream, "\n#### Reporting");
                timeouts.store(outputStream, "\n#### Timeouts");
                web.store(outputStream, "\n#### Browser Settings");
                printFooter(configurationsFile);
                outputStream.close();
            }


            if (!log4jFile.exists()) {
                printHeader(log4jFile);
                FileOutputStream outputStream = new FileOutputStream(log4jPath, true);
                log4j.store(outputStream, null);
                printFooter(log4jFile);
                outputStream.close();
            }

            LoggingManager.info("Properties Files are generated with default settings");
        } catch (IOException e) {
            LoggingManager.error("Unable to create Properties files");
        }


        LoggingManager.info("All Properties initialized successfully");
    }

    private static void printHeader(File file) throws IOException {

        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,
              StandardOpenOption.APPEND);

        if (file.equals(configurationsFile)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n########### AUTOTESTIMATIC Configurations #############\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        Files.writeString(Paths.get(file.toURI()), header + "\n", StandardOpenOption.CREATE,
              StandardOpenOption.APPEND);
    }

    private static void printFooter(File file) throws IOException {
        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,
              StandardOpenOption.APPEND);
        Files.writeString(Paths.get(file.toURI()),
              "\n##################### End of File #####################\n",
              StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,
              StandardOpenOption.APPEND);
    }

}
