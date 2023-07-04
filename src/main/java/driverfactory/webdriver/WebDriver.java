package driverfactory.webdriver;

import assertions.Assertions;
import browseractions.BrowserActions;
import com.browserstack.local.Local;
import constants.DriverType;
import constants.EnvType;
import driverfactory.webdriver.localdriver.DriverFactory;
import elementactions.ElementActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Reporter;
import utilities.JSONFileHandler;
import utilities.LoggingManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static tools.properties.PropertiesHandler.*;

public class WebDriver {

    private final ThreadLocal<org.openqa.selenium.WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final Local bsLocal = new Local();

    JSONFileHandler json = new JSONFileHandler("parallel.conf.json");

    public WebDriver() {
        LoggingManager.info("Initializing WebDriver.....");
        createWebDriver();
        if (driverThreadLocal.get() == null) {
            createWebDriver();
        }
    }

    private void createWebDriver() {
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL) {
            localDriverInit();
        }

        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID) {
            gridInit();
        }

        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.CLOUD) {
            cloudInit();
        }

        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }


    private void localDriverInit() {
        String baseURL = getCapabilities().baseURL();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        LoggingManager.info("Starting " + browserName + " Driver Locally in " + getCapabilities().executionMethod() + " mode");
        org.openqa.selenium.WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

    }

    private void gridInit() {
        String baseURL = getCapabilities().baseURL();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        LoggingManager.info("Starting Selenium Grid on: " + getPlatform().remoteURL());
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            driver.manage().window().maximize();
            setRemoteDriver(driver);
        } catch (MalformedURLException e) {
            LoggingManager.error("Unable to create Remote WebDriver: " + e.getMessage());
        }
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }
    }

    private void cloudInit() {
        // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
        HashMap<String, String> bsLocalArgs = new HashMap<>();

        bsLocalArgs.put("user",json.getData("user"));
        bsLocalArgs.put("key", json.getData("key"));
        bsLocalArgs.put("server", json.getData("server"));
        bsLocalArgs.put("browserName", "chrome");
        bsLocalArgs.put("os", "Windows");
        bsLocalArgs.put("osVersion", "10");
        bsLocalArgs.put("browserstack.local", "true");
        // Starts the Local instance with the required arguments.
        try {
            bsLocal.start(bsLocalArgs);
            LoggingManager.info("Start Running on BrowserStack Grid......");
        } catch (Exception e) {
            LoggingManager.error("Failed to Start BrowserStack Instance, " + e.getMessage());
        }
        // Checks if BrowserStack local instance is running.
        try {
            LoggingManager.info(bsLocal.isRunning());
        } catch (Exception e) {
            LoggingManager.error("BrowserStack Connection isn't started, " + e.getMessage());
        }
        // Your test code goes here, from creating the driver instance till the end, i.e. driver.quit.
        // Stops the Local instance.
    }


    private void setDriver(org.openqa.selenium.WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    private void setRemoteDriver(RemoteWebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public org.openqa.selenium.WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            createWebDriver();
        }
        assert driverThreadLocal.get() != null;
        return driverThreadLocal.get();
    }

    public void quit() {
        LoggingManager.info("Quitting Driver.....");
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.CLOUD){
            try {
                bsLocal.stop();
            } catch (Exception e) {
                LoggingManager.error("Failed to stop BrowserStack instance, " + e.getMessage());
            }
        }
        else {
            assert driverThreadLocal.get() != null;
            driverThreadLocal.get().manage().deleteAllCookies();
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

    public ElementActions element() {
        return new ElementActions(getDriver());
    }

    public BrowserActions browser() {
        return new BrowserActions(getDriver());
    }

    public Assertions assertThat() {
        return new Assertions(getDriver());
    }

}