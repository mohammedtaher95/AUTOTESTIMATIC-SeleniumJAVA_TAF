package tools.properties;

import org.aeonbits.owner.ConfigFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;



public class PropertiesHandler {

    private PropertiesHandler(){

    }

    static String header = "#######################################################";
    protected static Properties properties;
    private static ExecutionPlatform platform;
    private static WebCapabilities capabilities;
    private static Reporting reporting;

    private static TestNG testNG;

    private static File platformProperties;
    private static File capProperties;
    private static File reportingFile;
    private static File testNGFile;

    static String platformPath = "src/main/resources/properties/ExecutionPlatform.properties";
    static String webCapPath = "src/main/resources/properties/WebCapabilities.properties";
    static String reportingPath = "src/main/resources/properties/Reporting.properties";
    static String testNGPath = "src/main/resources/properties/TestNG.properties";

    public static synchronized void initializeProperties() throws IOException {

        platform = ConfigFactory.create(ExecutionPlatform.class);
        capabilities = ConfigFactory.create(WebCapabilities.class);
        reporting = ConfigFactory.create(Reporting.class);
        testNG = ConfigFactory.create(TestNG.class);

        generateDefaultProperties();
    }

    private static synchronized void generateDefaultProperties() throws IOException {

        platformProperties = new File(platformPath);
        capProperties = new File(webCapPath);
        reportingFile = new File(reportingPath);
        testNGFile = new File(testNGPath);


        if(!platformProperties.exists()){
            printHeader(platformProperties);
            FileOutputStream outputStream = new FileOutputStream(platformPath, true);
            platform.store(outputStream, null);
            printFooter(platformProperties);
            outputStream.close();
        }

        if(!capProperties.exists()){
            printHeader(capProperties);
            FileOutputStream outputStream = new FileOutputStream(webCapPath, true);
            capabilities.store(outputStream, null);
            printFooter(capProperties);
            outputStream.close();
        }

        if(!reportingFile.exists()){
            printHeader(reportingFile);
            FileOutputStream outputStream = new FileOutputStream(reportingPath, true);
            reporting.store(outputStream, null);
            printFooter(reportingFile);
            outputStream.close();
        }

        if(!testNGFile.exists()){
            printHeader(testNGFile);
            FileOutputStream outputStream = new FileOutputStream(testNGPath, true);
            testNG.store(outputStream, null);
            printFooter(testNGFile);
            outputStream.close();
        }
    }

    private static void printHeader(File file) throws IOException {
        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        if(file.equals(platformProperties)){
            Files.writeString(Paths.get(file.toURI()), "\n########## TAF Execution Platform Properties ##########\n"
                    , StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }

        if(file.equals(capProperties)){
            Files.writeString(Paths.get(file.toURI()), "\n################ TAF Web Capabilities #################\n"
                    , StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }

        if(file.equals(reportingFile)){
            Files.writeString(Paths.get(file.toURI()), "\n################ TAF Reporting Options ################\n"
                    , StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }

        if(file.equals(testNGFile)){
            Files.writeString(Paths.get(file.toURI()), "\n################## TAF TestNG Options #################\n"
                    , StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }
        Files.writeString(Paths.get(file.toURI()), header + "\n", StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }
    private static void printFooter(File file) throws IOException {
        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        Files.writeString(Paths.get(file.toURI()), "\n##################### End of File #####################\n"
                , StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        Files.writeString(Paths.get(file.toURI()), header, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }

    public static void setPlatform(ExecutionPlatform platform) {
        PropertiesHandler.platform = platform;
    }

    public static void setCapabilities(WebCapabilities capabilities) {
        PropertiesHandler.capabilities = capabilities;
    }

    public static void setReporting(Reporting reporting) {
        PropertiesHandler.reporting = reporting;
    }

    public static void setTestNG(TestNG testNG) {
        PropertiesHandler.testNG = testNG;
    }

    public static TestNG getTestNG() {
        return testNG;
    }

    public static ExecutionPlatform getPlatform() {
        return platform;
    }

    public static WebCapabilities getCapabilities() {
        return capabilities;
    }


    public static Reporting getReporting() {
        return reporting;
    }

}
