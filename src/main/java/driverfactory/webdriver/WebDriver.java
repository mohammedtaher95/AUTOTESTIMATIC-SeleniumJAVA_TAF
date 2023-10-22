package driverfactory.webdriver;

import assertions.Assertions;
import browseractions.BrowserActions;
import constants.DriverType;
import constants.EnvType;
import driverfactory.webdriver.localdriver.DriverFactory;
import elementactions.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Reporter;
import tools.listeners.junit.helpers.JunitHelper;
import tools.listeners.webdriver.WebDriverListeners;
import utilities.JSONFileHandler;
import utilities.LoggingManager;
import utilities.TestRunningManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import static tools.properties.PropertiesHandler.*;

public class WebDriver {

    private final ThreadLocal<org.openqa.selenium.WebDriver> driverThreadLocal = new ThreadLocal<>();
    private String browserName;

    private final FluentWait<org.openqa.selenium.WebDriver> driverWait;

    JSONFileHandler config = new JSONFileHandler("parallel.conf.json");

    public WebDriver() {
        TestRunningManager.initializeRunConfigurations();
        try{
            browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        }
        catch (NullPointerException e) {
            LoggingManager.info("Start Running Tests via JUnit 5 Runner");
            browserName = getCapabilities().targetBrowserName();
        }

        LoggingManager.info("Initializing WebDriver.....");
        createWebDriver();
        if (driverThreadLocal.get() == null) {
            createWebDriver();
        }
        driverWait = new FluentWait<>(driverThreadLocal.get())
                .withTimeout(Duration.ofSeconds(getTimeouts().elementIdentificationTimeout()))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    private void createWebDriver() {

        try {
            if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL) {
                localDriverInit();
            }

            if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID) {
                gridInit();
            }

            if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.CLOUD) {
                cloudInit();
            }
        }
        catch (IllegalArgumentException exception){
            LoggingManager.error("Environment Failure: Environment is not Specified");
            throw exception;
        }

    }


    private void localDriverInit() {
        String baseURL = getCapabilities().baseURL();
        LoggingManager.info("Starting " + browserName + " Driver Locally in " + getCapabilities().executionMethod() + " mode");
        org.openqa.selenium.WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(
                new EventFiringDecorator<>(org.openqa.selenium.WebDriver.class, new WebDriverListeners(driver))
                .decorate(driver)));
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }
        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());

    }

    private void gridInit() {
        String baseURL = getCapabilities().baseURL();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browserName);
        if(!getPlatform().proxySettings().isEmpty()){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(getPlatform().proxySettings());
            proxy.setSslProxy(getPlatform().proxySettings());
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }
        LoggingManager.info("Starting Selenium Grid on: " + getPlatform().remoteURL());
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            driver.manage().window().maximize();
            setRemoteDriver(new EventFiringDecorator<>(org.openqa.selenium.remote.RemoteWebDriver.class, new WebDriverListeners(driver))
                    .decorate(driver));
        } catch (MalformedURLException e) {
            LoggingManager.error("Unable to create Remote WebDriver: " + e.getMessage());
        }
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());

    }

    private void cloudInit() {
        // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
        String baseURL = getCapabilities().baseURL();
        String server = config.getData("server");
        String user = config.getData("user");
        String key = config.getData("key");
        String os =  config.getData("environments.env1.options.os");
        String osVersion = config.getData("environments.env1.options.osVersion");
        String browserVersion =  config.getData("capabilities.options.browserVersion");


        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", browserName);

        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", os);
        browserstackOptions.put("osVersion", osVersion);
        browserstackOptions.put("browserVersion", browserVersion);
        browserstackOptions.put("local", config.getData("capabilities.options.local"));
        browserstackOptions.put("seleniumVersion", "4.10.0");
        capabilities.setCapability("bstack:options", browserstackOptions);

        ClientConfig customConfig = ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(15))
                .connectionTimeout(Duration.ofMinutes(15));

        // Starts the Local instance with the required arguments.
        try {
            LoggingManager.info("Start Running on BrowserStack Grid......");
            RemoteWebDriver driver = (RemoteWebDriver) RemoteWebDriver.builder()
                    .config(customConfig)
                    .address(new URL("http://" + user + ":" + key + "@" + server))
                    .oneOf(capabilities)
                    .build();
            driver.manage().window().maximize();
            setRemoteDriver(new EventFiringDecorator<>(org.openqa.selenium.remote.RemoteWebDriver.class, new WebDriverListeners(driver))
                    .decorate(driver));
            LoggingManager.info("BrowserStack Started on " + browserName + ", " + os + " " + osVersion);
        } catch (Exception e) {
            LoggingManager.error("Failed to Start BrowserStack Instance, " + e.getMessage());
        }

        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

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

        assert driverThreadLocal.get() != null;
        driverThreadLocal.get().manage().deleteAllCookies();
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();

    }

    public ElementActions element() {
        return new ElementActions(getDriver(), driverWait);
    }

    public BrowserActions browser() {
        return new BrowserActions(getDriver());
    }

    public Assertions assertThat() {
        return new Assertions(getDriver(), driverWait);
    }

}