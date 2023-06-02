package utilities;

import org.apache.logging.log4j.*;

import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Reporter;

public class LoggingManager {

    private LoggingManager(){

    }
    private static final Logger logger = LogManager
            .getLogger();

    public static Logger getCurrentLogger() {
        return logger;
    }

    public static void startLog() {
        Configurator.setRootLevel(Level.INFO);
        ThreadContext.put("logFilename", "prints");
    }

    public static synchronized void startTestCase(String txt) {
        info("\n\n************** Execution Started : " + txt + " **************\n");
    }

    public static void endTestCase(String txt) {
        info("\n\n************** Execution Ended : " + txt + " **************\n");
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
