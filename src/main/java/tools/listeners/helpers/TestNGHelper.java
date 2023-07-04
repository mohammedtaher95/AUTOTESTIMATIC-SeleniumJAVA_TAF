package tools.listeners.helpers;

import constants.CrossBrowserMode;
import driverfactory.webdriver.WebDriver;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import utilities.Classloader;
import utilities.LoggingManager;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static tools.properties.PropertiesHandler.*;


public class TestNGHelper {

    static XmlTest test;
    static String browserName = "browserName";

    private TestNGHelper() {

    }

    public static XmlSuite suiteGenerator(XmlSuite testSuite) {
        LoggingManager.info("Generating TestNG Suite.....");
        test = testSuite.getTests().get(0);

        testSuite.setPreserveOrder(getTestNG().preserveOrder());
        testSuite.setGroupByInstances(getTestNG().groupByInstances());
        testSuite.setVerbose(getTestNG().verbose());
        testSuite.setThreadCount(getTestNG().threadCount());
        testSuite.setDataProviderThreadCount(getTestNG().dataProviderThreadCount());
        testSuite.setName("WebDriver Suite");
        testSuite.setListeners(Collections.singletonList("tools.listeners.TestNGListener"));

        if (CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.OFF) {
            testSuite.setParallel(XmlSuite.ParallelMode.valueOf(getTestNG().parallel()));
            initializeNormalExecution();
        } else {
            initializeCrossBrowserSuite(testSuite);
        }

        Path destination = Paths.get(".", "TestNG.xml");
        File newFile = new File("TestNG.xml");

        try {
            if (newFile.exists()) {
                Files.delete(destination);
            } else {
                FileUtils.forceMkdir(newFile);
            }
            Files.writeString(destination, testSuite.toXml());
            LoggingManager.info("TestNG Suite Generated successfully");
        } catch (IOException e) {
            LoggingManager.error("Unable to generate TestNG.xml file, " + e.getMessage());
        }
        return testSuite;
    }

    private static void initializeCrossBrowserSuite(XmlSuite testSuite) {

        if (CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.PARALLEL) {
            testSuite.setParallel(XmlSuite.ParallelMode.TESTS);
            LoggingManager.info("Cross Browsing Mode Enabled, Tests will run in Parallel Mode");
        }

        if (CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.SEQUENTIAL) {
            testSuite.setParallel(XmlSuite.ParallelMode.NONE);
            LoggingManager.info("Cross Browsing Mode Enabled, Tests will run in Sequential Mode");

        }

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

        if (getPlatform().runAllTests()) {
            List<XmlClass> classes = new ArrayList<>();
            Set<Class<?>> newSet = Classloader.findAllClasses("tests");
            for (Class<?> aClass : newSet) {
                classes.add(new XmlClass(String.valueOf(aClass).replaceFirst("class ", "")));
            }
            chromeTest.setXmlClasses(classes);
            firefoxTest.setXmlClasses(classes);
        }

    }

    private static void initializeNormalExecution() {
        LoggingManager.info("Tests will run in Normal Mode");
        XmlTest singleTest = test;
        singleTest.setName("Test");
        singleTest.addParameter(browserName, getCapabilities().targetBrowserName());
        //singleTest.addParameter("config", "parallel.conf.json");
        singleTest.setThreadCount(1);
        singleTest.setParallel(XmlSuite.ParallelMode.NONE);
        singleTest.setXmlClasses(test.getXmlClasses());

        if (getPlatform().runAllTests()) {
            List<XmlClass> classes = new ArrayList<>();
            Set<Class<?>> newSet = Classloader.findAllClasses("tests");
            for (Class<?> aClass : newSet) {
                classes.add(new XmlClass(String.valueOf(aClass).replaceFirst("class ", "")));
            }
            singleTest.setXmlClasses(classes);
        }

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
                        field.setAccessible(true);
                        driver = (WebDriver) field.get(currentClass);
                    }

                    if (field.getType() == ThreadLocal.class) {
                        field.setAccessible(true);
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
