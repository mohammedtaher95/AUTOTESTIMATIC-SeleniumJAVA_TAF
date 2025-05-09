package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import driverfactory.webdriver.WebDriver;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.junit.platform.engine.TestExecutionResult;
import org.testng.ITestResult;
import tools.engineconfigurations.Configurations;

public class ExtentReportManager {

    public static final ExtentReports extentReport = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ExtentTest node;
    static String tempName = "Welcome";
    public static final String REPORT_PATH = "target/ExecutionSummaryReport.html";

    private ExtentReportManager() {
    }

    public static void setUpReport() {


        ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Execution Summary Report");
        spark.config().setReportName("Extent Report - Powered by AUTOTESTIMATIC");
        extentReport.attachReporter(spark);

        extentReport.setSystemInfo("Framework Type", "AUTOTESTIMATIC Web Framework");
        extentReport.setSystemInfo("Author", "Mohammed Taher");
        extentReport.setSystemInfo("os", System.getProperty("os.name"));
        extentReport.setSystemInfo("Environment", Configurations.web.targetBrowserName());
        extentReport.setSystemInfo("App", "NopCommerce Web App");
    }

    public static void finishReport() {
        extentReport.flush();
    }

    public static void export() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            File file = new File(REPORT_PATH);

            // Check if the file exists
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    LoggingManager.error("Unable to open Extent Report");
                    LoggingManager.error(e);
                }
            } else {
                LoggingManager.error("File not found: " + REPORT_PATH);
            }
        } else {
            LoggingManager.error("Desktop not supported. Cannot open file.");
        }
    }

    public static void logTest(String testName) {
        if (extentReport.equals(new ExtentReports()) && (!testName.equalsIgnoreCase(tempName))) {
            extentTest.set(extentReport.createTest(testName));
        }
        tempName = testName;
    }

    public static void logMethod(String methodName) {
        node = extentTest.get().createNode(methodName);
    }

    @SuppressWarnings("all")
    public static void saveResults(ITestResult result, WebDriver driver) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            node.log(Status.PASS, "Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            node.log(Status.FAIL, "Test Failed");
            node.log(Status.FAIL, result.getThrowable());
            String fullPath = System.getProperty("user.dir")
                  + ScreenshotHelper.captureScreenshot(driver, result.getName());
            node.addScreenCaptureFromPath(fullPath);

        } else {
            node.log(Status.SKIP, "Test Skipped");
        }
    }

    @SuppressWarnings("all")
    public static void saveResults(TestExecutionResult result, WebDriver driver) {
        if (result.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
            node.log(Status.PASS, "Test Passed");
        } else if (result.getStatus() == TestExecutionResult.Status.FAILED) {
            node.log(Status.FAIL, "Test Failed");
            node.log(Status.FAIL, String.valueOf(result.getThrowable()));
            String fullPath = System.getProperty("user.dir")
                  + ScreenshotHelper.captureScreenshot(driver, result.getClass().getName());
            node.addScreenCaptureFromPath(fullPath);

        } else {
            node.log(Status.SKIP, "Test Skipped");
        }
    }

    @SuppressWarnings("all")
    public static void testPassed() {
        node.log(Status.PASS, "Test Passed");
    }

    public static void remove() {
        extentTest.remove();
    }


}



