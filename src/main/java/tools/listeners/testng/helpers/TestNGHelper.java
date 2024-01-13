package tools.listeners.testng.helpers;

import constants.CrossBrowserMode;
import driverfactory.webdriver.WebDriver;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import tools.properties.Properties;
import utilities.LoggingManager;
import java.lang.reflect.Field;
import java.util.Collections;



public class TestNGHelper {

    static XmlTest test;
    static String browserName = "browserName";

    private TestNGHelper() {

    }

    public static XmlSuite suiteGenerator(XmlSuite testSuite) {


        test = testSuite.getTests().get(0);

        if (test.getParameter("browserName") != null) {
            LoggingManager.info("Running from Existing XML File");
            testSuite.setListeners(Collections.singletonList("tools.listeners.testng.TestNGListener"));
        }
        else {
            LoggingManager.info("Generating TestNG Suite.....");
            testSuite.setPreserveOrder(Properties.testNG.preserveOrder());
            testSuite.setGroupByInstances(Properties.testNG.groupByInstances());
            testSuite.setVerbose(Properties.testNG.verbose());
            testSuite.setThreadCount(Properties.testNG.threadCount());
            testSuite.setDataProviderThreadCount(Properties.testNG.dataProviderThreadCount());
            testSuite.setParallel(XmlSuite.ParallelMode.valueOf(Properties.testNG.parallel()));
            testSuite.setName("WebDriver Suite");
            testSuite.setListeners(Collections.singletonList("tools.listeners.testng.TestNGListener"));

            try{
                if (CrossBrowserMode.valueOf(Properties.executionOptions.crossBrowserMode()) == CrossBrowserMode.OFF) {
                    testSuite.setParallel(XmlSuite.ParallelMode.NONE);
                    initializeNormalExecution();
                } else {
                    initializeCrossBrowserSuite(testSuite);
                }
            }
            catch (IllegalArgumentException exception){
                LoggingManager.warn("Cross Browsing Mode Parameter is not specified, so Running on Normal Mode By default");
                testSuite.setParallel(XmlSuite.ParallelMode.NONE);
                initializeNormalExecution();
            }
            LoggingManager.info("TestNG Suite Generated successfully");
        }

        return testSuite;
    }

    private static void initializeCrossBrowserSuite(XmlSuite testSuite) {

        if (CrossBrowserMode.valueOf(Properties.executionOptions.crossBrowserMode()) == CrossBrowserMode.PARALLEL) {
            testSuite.setParallel(XmlSuite.ParallelMode.TESTS);
            LoggingManager.info("Cross Browsing Mode Enabled, Tests will run in Parallel Mode");
        }

        if (CrossBrowserMode.valueOf(Properties.executionOptions.crossBrowserMode()) == CrossBrowserMode.SEQUENTIAL) {
            testSuite.setParallel(XmlSuite.ParallelMode.NONE);
            LoggingManager.info("Cross Browsing Mode Enabled, Tests will run in Sequential Mode");

        }

        testSuite.setThreadCount(2);

        XmlTest chromeTest = test;
        chromeTest.setName("Chrome Test");
        chromeTest.addParameter(browserName, "chrome");
        chromeTest.setThreadCount(1);
        chromeTest.setParallel(XmlSuite.ParallelMode.NONE);
        chromeTest.setXmlClasses(test.getXmlClasses());

        XmlTest firefoxTest = new XmlTest(testSuite);
        firefoxTest.setName("Firefox Test");
        firefoxTest.setThreadCount(1);
        firefoxTest.setParallel(XmlSuite.ParallelMode.NONE);
        firefoxTest.addParameter(browserName, "firefox");
        firefoxTest.setXmlClasses(test.getXmlClasses());

    }

    private static void initializeNormalExecution() {
        LoggingManager.info("Tests will run in Normal Mode");
        XmlTest singleTest = test;
        singleTest.setName("Test");
        singleTest.addParameter(browserName, Properties.web.targetBrowserName());
        singleTest.setThreadCount(1);
        singleTest.setParallel(XmlSuite.ParallelMode.NONE);
        singleTest.setXmlClasses(test.getXmlClasses());

    }

    public static WebDriver getDriverInstance(ITestResult result) {
        WebDriver driver = null;
        ThreadLocal<WebDriver> driverThreadlocal;
        Object currentClass = result.getInstance();
        if (currentClass != null) {
            Class<?> testClass = result.getTestClass().getRealClass();
            Field[] fields = testClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getType() == WebDriver.class) {
                        driver = (WebDriver) field.get(currentClass);
                    }

                    if (field.getType() == ThreadLocal.class) {
                        driverThreadlocal = (ThreadLocal<WebDriver>) field.get(currentClass);
                        driver = driverThreadlocal.get();
                    }

                } catch (IllegalAccessException e) {
                    LoggingManager.error("Unable to access field: " + field.getName());
                }
            }
        }
        return driver;
    }
}
