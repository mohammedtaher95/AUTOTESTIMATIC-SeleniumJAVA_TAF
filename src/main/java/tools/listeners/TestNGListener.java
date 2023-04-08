package tools.listeners;

import driverfactory.Webdriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.testng.*;
import org.testng.xml.XmlSuite;

import tools.listeners.helpers.TestNGSuiteHelper;
import utilities.AllureBatchGenerator;

import utilities.ScreenshotHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;


import static tools.properties.PropertiesHandler.*;

public class TestNGListener implements IAlterSuiteListener, ITestListener, ISuiteListener,
        IExecutionListener, IInvokedMethodListener {


    @Override
    public void onExecutionStart() {
        Allure.getLifecycle();
    }

    @Override
    public void onExecutionFinish() {
        if(getReporting().automaticOpenAllureReport()){
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
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("Success of test cases and its details are : " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // To be implemented later
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failure of test cases and its details are : " + result.getName());
            System.out.println("Failed!");
            System.out.println("Taking Screenshot....");
            String fullPath = null;
            try {
                fullPath = System.getProperty("user.dir")
                        + ScreenshotHelper.captureScreenshot(Webdriver.getDriver(),
                        result.getMethod().getConstructorOrMethod().getName());
                System.out.println("Screenshot captured for Test case: " + result.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fullPath != null;
                Allure.addAttachment(result.getMethod().getConstructorOrMethod().getName(),
                        FileUtils.openInputStream(new File(fullPath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Skip of test cases and its details are : " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Failure of test cases and its details are : " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        //TO-DO
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
    }

    @Override
    public void alter(List<XmlSuite> suites){
        try {
            initializeProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            suites.set(0, TestNGSuiteHelper.suiteGenerator(suites.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
