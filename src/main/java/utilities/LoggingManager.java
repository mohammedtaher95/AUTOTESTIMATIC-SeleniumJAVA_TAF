package utilities;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.jul.Log4jBridgeHandler;
import org.apache.logging.log4j.core.config.Configurator;
import java.net.URI;

public class LoggingManager {

    private static final String FRAME = StringUtils.repeat("-", 140);
    private LoggingManager(){

    }
    private static final Logger logger = LogManager.getLogger();

    public static Logger getCurrentLogger() {
        return logger;
    }

    public static void startLog() {
        System.setProperty("log4j.configurationFile", "properties/log4j2.properties");
        System.setProperty("java.util.logging.config.file", "");
        Configurator.reconfigure(URI.create("properties/log4j2.properties"));

        java.util.logging.Logger rootLogger = java.util.logging.LogManager.getLogManager().getLogger("");
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        // Add Log4jBridgeHandler to the root logger
        Log4jBridgeHandler.install(true,null,true);
        info("\n" + FRAME
            +"\n                                                        SeleniumJava TAF v1.2.5                                           \n"
            + FRAME);
    }

    public static synchronized void startTestCase(String txt) {
        info("\n" + FRAME
                +"\n                                                Starting Execution of: " + txt + "\n"
                + FRAME);
    }

    public static void endTestCase(String txt) {
        info("\n" + FRAME
                +"\n                                                 Execution Ended for: " + txt + "\n"
                + FRAME);
    }

    public static void trace(Object message){
        getCurrentLogger().trace(message);
    }

    public static void trace(Object message, Throwable throwable){
        getCurrentLogger().trace(message, throwable);
    }

    public static void debug(Object message) {

        getCurrentLogger().debug(message);
    }

    public static void debug(Object message, Throwable t) {
        getCurrentLogger().debug(message, t);
    }

    public static void error(Object message) {

        getCurrentLogger().error(message);
    }

    public static void error(Object message, Throwable t) {
        getCurrentLogger().error(message, t);
    }

    public static void fatal(Object message) {
        getCurrentLogger().fatal(message);
    }

    public static void fatal(Object message, Throwable t) {
        getCurrentLogger().fatal(message, t);
    }

    public static void info(Object message) {
        getCurrentLogger().info(message);
    }

    public static void info(Object message, Throwable t) {
        getCurrentLogger().info(message, t);
    }

    public static void warn(Object message) {
        getCurrentLogger().warn(message);
    }

    public static void warn(Object message, Throwable t) {
        getCurrentLogger().warn(message, t);
    }
}
