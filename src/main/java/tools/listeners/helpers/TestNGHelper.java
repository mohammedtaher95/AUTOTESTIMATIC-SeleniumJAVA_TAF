package tools.listeners.helpers;

import constants.CrossBrowserMode;
import org.apache.commons.io.FileUtils;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import utilities.Classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static tools.properties.PropertiesHandler.*;


public class TestNGHelper {

    public static final XmlSuite testSuite = new XmlSuite();
    static XmlTest test;
    static String browserName = "browserName";

    private TestNGHelper(){

    }

    public static XmlSuite suiteGenerator(XmlSuite suite) throws IOException {

        test = suite.getTests().get(0);

        testSuite.setPreserveOrder(getTestNG().preserveOrder());
        testSuite.setGroupByInstances(getTestNG().groupByInstances());
        testSuite.setVerbose(getTestNG().verbose());
        testSuite.setParallel(XmlSuite.ParallelMode.valueOf(getTestNG().parallel()));
        testSuite.setThreadCount(getTestNG().threadCount());
        testSuite.setDataProviderThreadCount(getTestNG().dataProviderThreadCount());

        testSuite.setName("WebDriver Suite");
        testSuite.setListeners(Collections.singletonList("tools.listeners.TestNGListener"));

        if(CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.OFF){
            initializeNormalExecution();
        }

        else {
            initializeCrossBrowserSuite();
        }

        Path destination = Paths.get(".", "TestNG.xml");
        File newFile = new File("TestNG.xml");

        try {
            if(newFile.exists()){
                Files.delete(destination);
            }
            else{
                FileUtils.forceMkdir(newFile);
            }
            Files.writeString(destination, testSuite.toXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testSuite;
    }

    private static void initializeCrossBrowserSuite(){

        if(CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.PARALLEL){
            testSuite.setParallel(XmlSuite.ParallelMode.TESTS);
        }

        if(CrossBrowserMode.valueOf(getPlatform().crossBrowserMode()) == CrossBrowserMode.SEQUENTIAL){
            testSuite.setParallel(XmlSuite.ParallelMode.NONE);
        }

        XmlTest chromeTest = new XmlTest(testSuite);
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

        if(getPlatform().runAllTests()){
            List<XmlClass> classes = new ArrayList<>();
            Set<Class<?>> newSet = Classloader.findAllClasses("tests");
            for (Class<?> aClass : newSet) {
                classes.add(new XmlClass(String.valueOf(aClass).replaceFirst("class ", "")));
            }
            chromeTest.setXmlClasses(classes);
            firefoxTest.setXmlClasses(classes);
        }

    }

    private static void initializeNormalExecution(){
        testSuite.setParallel(XmlSuite.ParallelMode.NONE);
        XmlTest singleTest = new XmlTest(testSuite);
        singleTest.setName("Test");
        singleTest.addParameter(browserName, getCapabilities().targetBrowserName());
        singleTest.setThreadCount(1);
        singleTest.setParallel(XmlSuite.ParallelMode.NONE);
        singleTest.setXmlClasses(test.getXmlClasses());

        if(getPlatform().runAllTests()){
            List<XmlClass> classes = new ArrayList<>();
            Set<Class<?>> newSet = Classloader.findAllClasses("tests");
            for (Class<?> aClass : newSet) {
                classes.add(new XmlClass(String.valueOf(aClass).replaceFirst("class ", "")));
            }
            singleTest.setXmlClasses(classes);
        }

    }

}
