package utilities;

import org.apache.logging.log4j.*;
import org.testng.Reporter;

public class LoggingManager {

    private static Logger logger;

    public LoggingManager(){
        logger = LogManager.getLogger(Reporter.getCurrentTestResult().getTestClass().getXmlClass().getName());
        logger.info("Starting WebDriver");
    }
    public static Logger getLogger() {
        return logger;
    }


}
