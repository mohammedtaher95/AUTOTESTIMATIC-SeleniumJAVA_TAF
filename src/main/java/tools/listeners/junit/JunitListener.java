package tools.listeners.junit;

import com.google.auto.service.AutoService;
import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import tools.listeners.junit.helpers.JunitHelper;
import tools.engineconfigurations.Configurations;
import tools.engineconfigurations.ConfigurationsManager;
import utilities.ExtentReportManager;
import utilities.LoggingManager;
import utilities.ScreenshotHelper;
import utilities.allure.AllureBatchGenerator;
import utilities.allure.AllureReportHelper;



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
            public void executionFinished(TestIdentifier testIdentifier,
                                          TestExecutionResult testExecutionResult) {
                if (testIdentifier.isTest()) {
                    if (testExecutionResult.getStatus()
                          .equals(TestExecutionResult.Status.SUCCESSFUL)) {
                        testPassed(testIdentifier);
                    }
                    if (testExecutionResult.getStatus().equals(TestExecutionResult.Status.FAILED)
                          || testExecutionResult.getStatus()
                          .equals(TestExecutionResult.Status.ABORTED)) {
                        testFailure(testIdentifier, testExecutionResult);

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
        ConfigurationsManager.initialize();
        ExtentReportManager.setUpReport();
        Allure.getLifecycle();
        AllureBatchGenerator.generateBatFile();
        if (Configurations.reporting.cleanAllureReport()) {
            AllureReportHelper.cleanAllureReport();
        }
    }


    public void testRunFinished() {
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

    }


    public void testStarted(TestIdentifier testIdentifier) {
        if (testIdentifier.isTest()) {
            LoggingManager.startTestCase(testIdentifier.getDisplayName());
            ExtentReportManager.logTest(
                  testIdentifier.getUniqueIdObject().getSegments().get(1).getValue());
            ExtentReportManager.logMethod(testIdentifier.getDisplayName());
        }
    }


    public void testPassed(TestIdentifier testIdentifier) {
        LoggingManager.info(
              "Success of test cases and its details are : " + testIdentifier.getDisplayName());
        ExtentReportManager.testPassed();
    }


    public void testFailure(TestIdentifier testIdentifier,
                            TestExecutionResult testExecutionResult) {
        WebDriver driver = JunitHelper.getDriverInstance(testIdentifier);
        if (testIdentifier.getType().isTest()) {
            LoggingManager.error(
                  "Failure of test cases and its details are : " + testIdentifier.getDisplayName());
            LoggingManager.error("Failed!");
            LoggingManager.error("Taking Screenshot....");
            String fullPath = System.getProperty("user.dir")
                  + ScreenshotHelper.captureScreenshot(driver, testIdentifier.getDisplayName());
            LoggingManager.info(
                  "Screenshot captured for Test case: " + testIdentifier.getDisplayName());

            try {
                Allure.addAttachment(testIdentifier.getDisplayName(),
                      FileUtils.openInputStream(new File(fullPath)));
            } catch (IOException e) {
                LoggingManager.error("Attachment isn't Found");
            }

        }
        ExtentReportManager.saveResults(testExecutionResult, driver);
    }


    public void testSkipped(TestIdentifier testIdentifier, String reason) {
        if (testIdentifier.isTest()) {
            LoggingManager.error(
                  "Skip of test cases and its details are : " + testIdentifier.getDisplayName());
            LoggingManager.error(reason);
        }
    }


}
