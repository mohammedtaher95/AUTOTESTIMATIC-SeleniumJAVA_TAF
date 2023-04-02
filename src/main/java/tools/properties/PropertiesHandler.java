package tools.properties;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static tools.properties.DefaultProperties.*;

public class PropertiesHandler {

    public static Properties properties;
    protected static FileInputStream inputStream;

    public static Properties readPropertyFile(String filePath) throws IOException {

        File propFile = new File(filePath);
        inputStream = new FileInputStream(propFile);
        properties = new Properties();
        properties.load(inputStream);

        return properties;
    }

    public static synchronized void initializeProperties() throws IOException {

        DefaultProperties.platform = ConfigFactory.create(ExecutionPlatform.class);
        DefaultProperties.capabilities = ConfigFactory.create(WebCapabilities.class);
        DefaultProperties.reporting = ConfigFactory.create(Reporting.class);

        generateDefaultProperties();
    }

    private static synchronized void generateDefaultProperties() throws IOException {

        File platformProperties = new File(platformPath);
        File capProperties = new File(webCapPath);
        File reporting = new File(reportingPath);

        if(!platformProperties.exists()){
            FileOutputStream outputStream = new FileOutputStream(platformPath);
            DefaultProperties.platform.store(outputStream, "#######################################################"
                    + "\n" + "########## TAF Execution Platform Properties ###########"
            + "\n" + "########################################################");
            outputStream.close();
        }

        if(!capProperties.exists()){
            FileOutputStream outputStream = new FileOutputStream(webCapPath);
            DefaultProperties.capabilities.store(outputStream, "######################################################"
                    + "\n" + "################ TAF Web Capabilities #################"
                    + "\n" + "#######################################################");
            outputStream.close();
        }

        if(!reporting.exists()){
            FileOutputStream outputStream = new FileOutputStream(reportingPath);
            DefaultProperties.reporting.store(outputStream, "######################################################"
                    + "\n" + "################ TAF Reporting Options ################"
                    + "\n" + "#######################################################");
            outputStream.close();
        }
    }

}
