package tools.properties;

import org.aeonbits.owner.ConfigFactory;
import java.io.*;
import java.util.Properties;



public class PropertiesHandler {

    private PropertiesHandler(){

    }

    static String header = "#######################################################";
    protected static Properties properties;
    private static ExecutionPlatform platform;
    private static WebCapabilities capabilities;
    private static Reporting reporting;

    static String platformPath = "src/main/resources/properties/ExecutionPlatform.properties";
    static String webCapPath = "src/main/resources/properties/WebCapabilities.properties";
    static String reportingPath = "src/main/resources/properties/Reporting.properties";

    public static synchronized void initializeProperties() throws IOException {

        platform = ConfigFactory.create(ExecutionPlatform.class);
        capabilities = ConfigFactory.create(WebCapabilities.class);
        reporting = ConfigFactory.create(Reporting.class);

        generateDefaultProperties();
    }

    private static synchronized void generateDefaultProperties() throws IOException {

        File platformProperties = new File(platformPath);
        File capProperties = new File(webCapPath);
        File reportingFile = new File(reportingPath);

        if(!platformProperties.exists()){
            FileOutputStream outputStream = new FileOutputStream(platformPath);
            platform.store(outputStream, header
                    + "\n" + "########## TAF Execution Platform Properties ###########"
            + "\n" + header);
            outputStream.close();
        }

        if(!capProperties.exists()){
            FileOutputStream outputStream = new FileOutputStream(webCapPath);
            capabilities.store(outputStream, header
                    + "\n" + "################ TAF Web Capabilities #################"
                    + "\n" + header);
            outputStream.close();
        }

        if(!reportingFile.exists()){
            FileOutputStream outputStream = new FileOutputStream(reportingPath);
            reporting.store(outputStream, header
                    + "\n" + "################ TAF Reporting Options ################"
                    + "\n" + header);
            outputStream.close();
        }
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
