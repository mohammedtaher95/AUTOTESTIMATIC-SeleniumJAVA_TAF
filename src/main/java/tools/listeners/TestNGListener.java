package tools.listeners;

import driverfactory.Webdriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.testng.*;
import org.testng.xml.XmlSuite;
import tools.listeners.helpers.TestNGHelper;
import utilities.allure.AllureBatchGenerator;

import utilities.EmailableReportGenerator;
import utilities.LoggingManager;
import utilities.ScreenshotHelper;
import utilities.allure.AllureReportHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;


import static tools.properties.PropertiesHandler.*;

public class TestNGListener implements IAlterSuiteListener, ITestListener, ISuiteListener,
        IExecutionListener, IInvokedMethodListener {

    private int retryCount = 0;
    private int maxRetryCount = 5;

    @Override
    public void onExecutionStart() {

        Allure.getLifecycle();
    }

    @Override
    public void onExecutionFinish() {
        if (getReporting().automaticOpenAllureReport()) {
            try {
                Runtime.getRuntime().exec("generateAllureReport.bat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        EmailableReportGenerator.addPassedTest(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // To be implemented later
        LoggingManager.endTestCase(result.getName());
        EmailableReportGenerator.addFailedTest(result);
        if (retryCount < maxRetryCount) {
            retryCount++;
            result.setStatus(ITestResult.FAILURE);
        } else {
            result.setStatus(ITestResult.FAILURE);
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
        method.getTestMethod().getXmlTest().setThreadCount(50);
        method.getTestMethod().setThreadPoolSize(50);
        method.getTestMethod().setInvocationCount(50);
//        method.getTestMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
//        method.getTestMethod().setTimeOut(1000000);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            LoggingManager.error("Failure of test cases and its details are : " + result.getName());
            LoggingManager.error("Failed!");
            LoggingManager.error("Taking Screenshot....");
//            String fullPath = null;
//            try {
//                fullPath = System.getProperty("user.dir")
//                        + ScreenshotHelper.captureScreenshot(Webdriver.getDriver(),
//
//                        result.getMethod().getConstructorOrMethod().getName());
//                LoggingManager.info("Screenshot captured for Test case: " + result.getName());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                assert fullPath != null;
//                Allure.addAttachment(result.getMethod().getConstructorOrMethod().getName(),
//                        FileUtils.openInputStream(new File(fullPath)));
//            } catch (IOException e) {
//                throw new RuntimeException("Attachment isn't Found");
//            }
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggingManager.error("Skip of test cases and its details are : " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggingManager.error("Failure of test cases and its details are : " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        //TO-DO
        context.getCurrentXmlTest().setThreadCount(2);

    }

    @Override
    public void onFinish(ITestContext context) {
        //TO-DO

    }

    @Override
    public void onStart(ISuite suite) {
        try {
            AllureBatchGenerator.generateBatFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (getReporting().cleanAllureReport()) {
            AllureReportHelper.cleanAllureReport();
        }
    }

    @Override
    public void alter(List<XmlSuite> suites) {

        LoggingManager.startLog();

        try {
            initializeProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            suites.set(0, TestNGHelper.suiteGenerator(suites.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
