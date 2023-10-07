package tools.listeners.junit;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.junit.platform.launcher.LauncherSessionListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import tools.listeners.junit.helpers.JunitHelper;

import utilities.LoggingManager;
import utilities.ScreenshotHelper;
import utilities.allure.AllureBatchGenerator;
import utilities.allure.AllureReportHelper;

import java.io.File;
import java.io.IOException;

import static tools.properties.PropertiesHandler.getReporting;
import static tools.properties.PropertiesHandler.initializeProperties;

public class JunitListener extends RunListener {

    private static final Description FAILED = Description.createTestDescription("failed", "failed");

    @Override
    public void testRunStarted(Description description) throws Exception {
        LoggingManager.startLog();
        initializeProperties();
        AllureBatchGenerator.generateBatFile();
        if (getReporting().cleanAllureReport()) {
            AllureReportHelper.cleanAllureReport();
        }
    }

    /**
     * Called when all tests have finished. This may be called on an
     * arbitrary thread.
     *
     * @param result the summary of the test run, including all the tests that failed
     */

    @Override
    public void testRunFinished(Result result) throws Exception {

        if (getReporting().automaticOpenAllureReport()) {
            try {
                LoggingManager.info("Generating Allure Report.....");
                Runtime.getRuntime().exec("generateAllureReport.bat");
            } catch (IOException e) {
                LoggingManager.error("Unable to open Allure Report " + e.getMessage());
            }
        }
    }

    /**
     * Called when a test suite is about to be started. If this method is
     * called for a given {@link Description}, then {@link #testSuiteFinished(Description)}
     * will also be called for the same {@code Description}.
     *
     * <p>Note that not all runners will call this method, so runners should
     * be prepared to handle {@link #testStarted(Description)} calls for tests
     * where there was no corresponding {@code testSuiteStarted()} call for
     * the parent {@code Description}.
     *
     * @param description the description of the test suite that is about to be run
     *                    (generally a class name)
     * @since 4.13
     */
    @Override
    public void testSuiteStarted(Description description) throws Exception {
    }

    /**
     * Called when a test suite has finished, whether the test suite succeeds or fails.
     * This method will not be called for a given {@link Description} unless
     * {@link #testSuiteStarted(Description)} was called for the same @code Description}.
     *
     * @param description the description of the test suite that just ran
     * @since 4.13
     */
    @Override
    public void testSuiteFinished(Description description) throws Exception {
    }

    /**
     * Called when an atomic test is about to be started.
     *
     * @param description the description of the test that is about to be run
     * (generally a class and method name)
     */
    @Override
    public void testStarted(Description description) throws Exception {
        LoggingManager.startTestCase(description.getMethodName());

    }

    /**
     * Called when an atomic test has finished, whether the test succeeds or fails.
     *
     * @param description the description of the test that just ran
     */
    @Override
    public void testFinished(Description description) throws Exception {
        if(!description.getChildren().contains(FAILED)){
            LoggingManager.info("Success of test cases and its details are : " + description.getMethodName());
            LoggingManager.endTestCase(description.getMethodName());
        }
    }

    /**
     * Called when an atomic test fails, or when a listener throws an exception.
     *
     * <p>In the case of a failure of an atomic test, this method will be called
     * with the same {@code Description} passed to
     * {@link #testStarted(Description)}, from the same thread that called
     * {@link #testStarted(Description)}.
     *
     * <p>In the case of a listener throwing an exception, this will be called with
     * a {@code Description} of {@link Description#TEST_MECHANISM}, and may be called
     * on an arbitrary thread.
     *
     * @param failure describes the test that failed and the exception that was thrown
     */
    @Override
    public void testFailure(Failure failure) throws Exception {
        failure.getDescription().addChild(FAILED);
        WebDriver driver = JunitHelper.getDriverInstance(failure);
        if (!failure.getDescription().isSuite()){
            LoggingManager.error("Failure of test cases and its details are : " + failure.getDescription().getMethodName());
            LoggingManager.error("Failed!");
            LoggingManager.error("Taking Screenshot....");
            String fullPath = System.getProperty("user.dir")
                    + ScreenshotHelper.captureScreenshot(driver, failure.getDescription().getMethodName());
            LoggingManager.info("Screenshot captured for Test case: " + failure.getDescription().getMethodName());

            try {
                Allure.addAttachment(failure.getDescription().getMethodName(),
                        FileUtils.openInputStream(new File(fullPath)));
            } catch (IOException e) {
                LoggingManager.error("Attachment isn't Found");
            }

        }
    }

    /**
     * Called when an atomic test flags that it assumes a condition that is
     * false
     *
     * @param failure describes the test that failed and the
     * {@link org.junit.AssumptionViolatedException} that was thrown
     */
    @Override
    public void testAssumptionFailure(Failure failure) {
    }

    /**
     * Called when a test will not be run, generally because a test method is annotated
     * with {@link org.junit.Ignore}.
     *
     * @param description describes the test that will not be run
     */
    @Override
    public void testIgnored(Description description) throws Exception {
        LoggingManager.error("Skip of test cases and its details are : " + description.getMethodName());

    }

}
