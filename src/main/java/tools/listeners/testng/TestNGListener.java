package tools.listeners.testng;

import com.google.auto.service.AutoService;
import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.testng.IAlterSuiteListener;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import tools.engineconfigurations.Configurations;
import tools.listeners.testng.helpers.RetryAnalyzer;
import tools.listeners.testng.helpers.TestNGHelper;
import tools.engineconfigurations.ConfigurationsManager;
import utilities.summaryreport.ReportHelper;
import utilities.ExtentReportManager;
import utilities.LoggingManager;
import utilities.ScreenshotHelper;
import utilities.allure.AllureBatchGenerator;
import utilities.allure.AllureReportHelper;
import utilities.docker.DockerFilesGenerator;



@AutoService(ITestNGListener.class)
public class TestNGListener
      implements ITestNGListener, IAlterSuiteListener, ITestListener, ISuiteListener,
      IExecutionListener, IInvokedMethodListener {

    long startTime;


    @Override
    public void onExecutionStart() {
        Allure.getLifecycle();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onExecutionFinish() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        LoggingManager.info("Elapsed Time: " + elapsedTime + "s");
        if (Configurations.reporting.automaticOpenAllureReport()) {
            try {
                LoggingManager.info("Generating Allure Report.....");
                if (SystemUtils.IS_OS_WINDOWS) {
                    Runtime.getRuntime().exec("generateAllureReport.bat");
                } else {
                    Runtime.getRuntime().exec("sh generateAllureReport.sh");
                }
            } catch (IOException e) {
                LoggingManager.error("Unable to open Allure Report " + e.getMessage());
            }
        }
        ExtentReportManager.finishReport();
        if (Configurations.reporting.automaticOpenExtentReport()) {
            ExtentReportManager.export();
        }
        ReportHelper.generateReport(elapsedTime);
        ReportHelper.openHtmlReport();

    }

    @Override
    public void onTestStart(ITestResult result) {
        // To be implemented later
        LoggingManager.startTestCase(result.getName());
        result.getMethod().setThreadPoolSize(50);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        LoggingManager.info("Success of test cases and its details are : " + result.getName());
        LoggingManager.endTestCase(result.getName());
        ReportHelper.addPassedTest(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggingManager.endTestCase(result.getName());
        ReportHelper.addFailedTest(result);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        method.getTestMethod().getXmlTest().setThreadCount(50);
        method.getTestMethod().setThreadPoolSize(50);
        method.getTestMethod().setInvocationCount(50);
        if (Configurations.testNG.retryFailedTestAttempts() > 0) {
            method.getTestMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
        }

        ExtentReportManager.logTest(result.getTestClass().getName());
        ExtentReportManager.logMethod(method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        WebDriver driver = TestNGHelper.getDriverInstance(result);
        if (result.getStatus() == ITestResult.FAILURE
              && (!(result.getMethod().isBeforeClassConfiguration()
                    || result.getMethod().isBeforeSuiteConfiguration()
                    || result.getMethod().isBeforeMethodConfiguration()
                    || result.getMethod().isBeforeTestConfiguration()))) {
            LoggingManager.error("Failure of test cases and its details are : " + result.getName());
            LoggingManager.error("Failed!");
            LoggingManager.error("Taking Screenshot....");
            String fullPath = System.getProperty("user.dir")
                  + ScreenshotHelper.captureScreenshot(driver,
                  result.getMethod().getConstructorOrMethod().getName());
            LoggingManager.info("Screenshot captured for Test case: " + result.getName());

            try {
                Allure.addAttachment(result.getMethod().getConstructorOrMethod().getName(),
                      FileUtils.openInputStream(new File(fullPath)));
            } catch (IOException e) {
                LoggingManager.error("Attachment isn't Found");
            }

        }
        ExtentReportManager.saveResults(result, driver);

    }


    @Override
    public void onTestSkipped(ITestResult result) {
        LoggingManager.error("Skip of test cases and its details are : " + result.getName());
        ReportHelper.addskippedTest(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggingManager.error("Failure of test cases and its details are : " + result.getName());
    }

    @Override
    public void onStart(ISuite suite) {
        AllureBatchGenerator.generateBatFile();
        DockerFilesGenerator.generateBatFiles();
        ExtentReportManager.setUpReport();

        if (Configurations.reporting.cleanAllureReport()) {
            AllureReportHelper.cleanAllureReport();
        }
    }

    @Override
    public void alter(List<XmlSuite> suites) {
        LoggingManager.startLog();
        ConfigurationsManager.initialize();
        ConfigurationsManager.reloadConfigurations();
        suites.set(0, TestNGHelper.suiteGenerator(suites.get(0)));
    }


}
