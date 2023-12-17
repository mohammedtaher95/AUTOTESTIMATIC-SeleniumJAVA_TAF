package tools.listeners.junit;

import com.google.auto.service.AutoService;
import constants.CrossBrowserMode;
import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.*;
import tools.listeners.junit.helpers.JunitHelper;
import utilities.LoggingManager;
import utilities.ScreenshotHelper;
import utilities.allure.AllureBatchGenerator;
import utilities.allure.AllureReportHelper;
import java.io.File;
import java.io.IOException;

import static tools.properties.PropertiesHandler.*;

@AutoService(LauncherSessionListener.class)
public class JunitListener implements LauncherSessionListener {

    @Override
    public void launcherSessionOpened(LauncherSession session) {


        session.getLauncher().registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void testPlanExecutionStarted(TestPlan testPlan) {
                testRunStarted();
            }

            @Override
            public void executionSkipped(TestIdentifier testIdentifier, String reason) {
                testSkipped(testIdentifier, reason);
            }

            @Override
            public void executionStarted(TestIdentifier testIdentifier) {
                testStarted(testIdentifier);
            }

            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
                if (testIdentifier.isTest()) {
                    if (testExecutionResult.getStatus().equals(TestExecutionResult.Status.SUCCESSFUL)) {
                        testPassed(testIdentifier);
                    }
                    if (testExecutionResult.getStatus().equals(TestExecutionResult.Status.FAILED)
                            || testExecutionResult.getStatus().equals(TestExecutionResult.Status.ABORTED)) {
                        testFailure(testIdentifier);

                    }
                    LoggingManager.endTestCase(testIdentifier.getDisplayName());
                }
            }


            @Override
            public void dynamicTestRegistered(TestIdentifier testIdentifier) {
                TestExecutionListener.super.dynamicTestRegistered(testIdentifier);
            }
        });
    }

    @Override
    public void launcherSessionClosed(LauncherSession session) {
        testRunFinished();
    }



    public void testRunStarted() {
        LoggingManager.startLog();
        initializeProperties();
        Allure.getLifecycle();
        AllureBatchGenerator.generateBatFile();
        if (getReporting().cleanAllureReport()) {
            AllureReportHelper.cleanAllureReport();
        }
    }


    public void testRunFinished() {
        if (getReporting().automaticOpenAllureReport()) {
            try {
                LoggingManager.info("Generating Allure Report.....");
                Runtime.getRuntime().exec("generateAllureReport.bat");
            } catch (IOException e) {
                LoggingManager.error("Unable to open Allure Report " + e.getMessage());
            }
        }
    }


    public void testStarted(TestIdentifier testIdentifier) {
        if(testIdentifier.isTest()){
            LoggingManager.startTestCase(testIdentifier.getDisplayName());

        }
    }


    public void testPassed(TestIdentifier testIdentifier) {
        LoggingManager.info("Success of test cases and its details are : " + testIdentifier.getDisplayName());
    }


    public void testFailure(TestIdentifier testIdentifier) {
        WebDriver driver = JunitHelper.getDriverInstance(testIdentifier);
        if (!testIdentifier.getType().isTest()) {
            LoggingManager.error("Failure of test cases and its details are : " + testIdentifier.getDisplayName());
            LoggingManager.error("Failed!");
            LoggingManager.error("Taking Screenshot....");
            String fullPath = System.getProperty("user.dir")
                    + ScreenshotHelper.captureScreenshot(driver, testIdentifier.getDisplayName());
            LoggingManager.info("Screenshot captured for Test case: " + testIdentifier.getDisplayName());

            try {
                Allure.addAttachment(testIdentifier.getDisplayName(),
                        FileUtils.openInputStream(new File(fullPath)));
            } catch (IOException e) {
                LoggingManager.error("Attachment isn't Found");
            }

        }
    }


    public void testSkipped(TestIdentifier testIdentifier, String reason) {
        if (testIdentifier.isTest()){
            LoggingManager.error("Skip of test cases and its details are : " + testIdentifier.getDisplayName());
            LoggingManager.error(reason);
        }
    }



}
