package tools.properties;

import static tools.properties.Properties.executionOptions;
import static tools.properties.Properties.log4j;
import static tools.properties.Properties.reporting;
import static tools.properties.Properties.testNG;
import static tools.properties.Properties.timeouts;
import static tools.properties.Properties.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.aeonbits.owner.ConfigFactory;
import utilities.LoggingManager;

public class PropertiesHandler {

    private PropertiesHandler() {

    }

    static String header = "#######################################################";


    private static File platformProperties;
    private static File capProperties;
    private static File reportingFile;
    private static File testNGFile;
    private static File log4jFile;
    private static File timeoutsFile;

    static String propertiesDirectoryPath = "src/main/resources/properties/";
    static String platformPath = "src/main/resources/properties/ExecutionOptions.properties";
    static String webCapPath = "src/main/resources/properties/WebCapabilities.properties";
    static String reportingPath = "src/main/resources/properties/Reporting.properties";
    static String testNGPath = "src/main/resources/properties/TestNG.properties";
    static String log4jPath = "src/main/resources/properties/log4j2.properties";
    static String timeoutsPath = "src/main/resources/properties/Timeouts.properties";


    public static void initialize() {

        LoggingManager.info("Initializing Properties........");
        Properties.executionOptions = ConfigFactory.create(ExecutionOptions.class);
        web = ConfigFactory.create(WebCapabilities.class);
        reporting = ConfigFactory.create(Reporting.class);
        testNG = ConfigFactory.create(TestNg.class);
        log4j = ConfigFactory.create(Log4j.class);
        timeouts = ConfigFactory.create(Timeouts.class);

        generateDefaultProperties();
    }

    public static void reloadProperties() {
        LoggingManager.info("Reloading Properties.....");
        Properties.executionOptions.reload();
        web.reload();
        reporting.reload();
        timeouts.reload();
    }

    private static void generateDefaultProperties() {
        LoggingManager.info("Checking if Properties files exist.....");
        File propertiesDirectory = new File(propertiesDirectoryPath);
        platformProperties = new File(platformPath);
        capProperties = new File(webCapPath);
        reportingFile = new File(reportingPath);
        testNGFile = new File(testNGPath);
        log4jFile = new File(log4jPath);
        timeoutsFile = new File(timeoutsPath);

        if (!propertiesDirectory.exists()) {
            boolean created = propertiesDirectory.mkdirs();
            if (created) {
                LoggingManager.info("Directory Created");
            }
        }

        try {
            if (!platformProperties.exists()) {
                printHeader(platformProperties);
                FileOutputStream outputStream = new FileOutputStream(platformPath, true);
                executionOptions.store(outputStream, null);
                printFooter(platformProperties);
                outputStream.close();
            }

            if (!capProperties.exists()) {
                printHeader(capProperties);
                FileOutputStream outputStream = new FileOutputStream(webCapPath, true);
                web.store(outputStream, null);
                printFooter(capProperties);
                outputStream.close();
            }

            if (!reportingFile.exists()) {
                printHeader(reportingFile);
                FileOutputStream outputStream = new FileOutputStream(reportingPath, true);
                reporting.store(outputStream, null);
                printFooter(reportingFile);
                outputStream.close();
            }

            if (!testNGFile.exists()) {
                printHeader(testNGFile);
                FileOutputStream outputStream = new FileOutputStream(testNGPath, true);
                testNG.store(outputStream, null);
                printFooter(testNGFile);
                outputStream.close();
            }
            if (!log4jFile.exists()) {
                printHeader(log4jFile);
                FileOutputStream outputStream = new FileOutputStream(log4jPath, true);
                log4j.store(outputStream, null);
                printFooter(log4jFile);
                outputStream.close();
            }
            if (!timeoutsFile.exists()) {
                printHeader(timeoutsFile);
                FileOutputStream outputStream = new FileOutputStream(timeoutsPath, true);
                timeouts.store(outputStream, null);
                printFooter(timeoutsFile);
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

        if (file.equals(platformProperties)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n########## TAF Execution Options Properties ###########\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        if (file.equals(capProperties)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n################ TAF Web Capabilities #################\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        if (file.equals(reportingFile)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n################ TAF Reporting Options ################\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

        if (file.equals(testNGFile)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n################## TAF TestNG Options #################\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        if (file.equals(log4jFile)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n################## TAF Log4j Options #################\n",
                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        if (file.equals(timeoutsFile)) {
            Files.writeString(Paths.get(file.toURI()),
                  "\n################ TAF Timeouts Options #################\n",
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
