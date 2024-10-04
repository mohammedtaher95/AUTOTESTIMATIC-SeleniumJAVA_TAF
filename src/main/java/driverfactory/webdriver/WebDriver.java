package driverfactory.webdriver;

import assertions.Assertions;
import browseractions.BrowserActions;
import constants.CrossBrowserMode;
import constants.DriverType;
import constants.EnvType;
import driverfactory.webdriver.gridservice.GridFactory;
import driverfactory.webdriver.localdriver.DriverFactory;
import elementactions.ElementActions;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Reporter;
import tools.listeners.webdriver.WebDriverListeners;
import tools.properties.Properties;
import utilities.JsonFileHandler;
import utilities.LoggingManager;
import utilities.TestRunningManager;


public class WebDriver {

    private final ThreadLocal<org.openqa.selenium.WebDriver> driverThreadLocal =
          new ThreadLocal<>();
    private String browserName;
    private final FluentWait<org.openqa.selenium.WebDriver> driverWait;

    JsonFileHandler config = new JsonFileHandler("parallel.conf.json");

    public WebDriver() {
        TestRunningManager.initializeRunConfigurations();

        try {
            if (CrossBrowserMode.valueOf(Properties.executionOptions.crossBrowserMode())
                  == CrossBrowserMode.OFF) {
                browserName = Properties.web.targetBrowserName();
            } else {
                browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest()
                      .getParameter("browserName");
            }
        } catch (NullPointerException e) {
            browserName = Properties.web.targetBrowserName();
        }
        String osName = System.getProperty("os.name");
        LoggingManager.info("Running AUTOTESTIMATIC Framework on " + osName);

        createWebDriver();
        if (driverThreadLocal.get() == null) {
            createWebDriver();
        }
        driverWait = new FluentWait<>(driverThreadLocal.get())
              .withTimeout(Duration.ofSeconds(Properties.timeouts.elementIdentificationTimeout()))
              .pollingEvery(Duration.ofMillis(500))
              .ignoring(NoSuchElementException.class)
              .ignoring(StaleElementReferenceException.class);
    }

    private void createWebDriver() {

        try {
            if (EnvType.valueOf(Properties.executionOptions.environmentType()) == EnvType.LOCAL) {
                localDriverInit();
            }

            if (EnvType.valueOf(Properties.executionOptions.environmentType()) == EnvType.GRID) {
                gridInit();
            }

            if (EnvType.valueOf(Properties.executionOptions.environmentType()) == EnvType.CLOUD) {
                cloudInit();
            }
        } catch (IllegalArgumentException exception) {
            LoggingManager.error("Environment Failure: Environment is not Specified");
            throw exception;
        }

    }


    private void localDriverInit() {
        String baseUrl = Properties.web.baseUrl();
        LoggingManager.info(
              "Starting " + browserName + " Driver Locally in " + Properties.web.executionMethod()
                   + " mode");
        org.openqa.selenium.WebDriver driver =
              DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase()))
                    .getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(
              new EventFiringDecorator<>(org.openqa.selenium.WebDriver.class,
                    new WebDriverListeners(driver))
                    .decorate(driver)));
        if (!baseUrl.isEmpty()) {
            getDriver().navigate().to(baseUrl);
        }
        LoggingManager.info(
              "CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = "
                   + getDriver());

    }

    private void gridInit() {

        GridFactory.gridUp();
        String baseUrl = Properties.web.baseUrl();

        LoggingManager.info(
              "Start Running via Selenium Grid on: " + Properties.executionOptions.remoteUrl());

        RemoteWebDriver driver = GridFactory.getRemoteDriver(browserName);
        driver.manage().window().maximize();
        setRemoteDriver(new EventFiringDecorator<>(org.openqa.selenium.remote.RemoteWebDriver.class,
              new WebDriverListeners(driver)).decorate(driver));

        if (!baseUrl.isEmpty()) {
            getDriver().navigate().to(baseUrl);
        }

        LoggingManager.info(
              "CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = "
                   + getDriver());

    }

    private void cloudInit() {
        // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
        String baseUrl = Properties.web.baseUrl();
        String server = config.getData("server");
        String user = config.getData("user");
        String key = config.getData("key");
        String os = config.getData("environments.env1.options.os");
        String osVersion = config.getData("environments.env1.options.osVersion");
        String browserVersion = config.getData("capabilities.options.browserVersion");


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
            setRemoteDriver(
                  new EventFiringDecorator<>(org.openqa.selenium.remote.RemoteWebDriver.class,
                        new WebDriverListeners(driver))
                        .decorate(driver));
            LoggingManager.info(
                  "BrowserStack Started on " + browserName + ", " + os + " " + osVersion);
        } catch (Exception e) {
            LoggingManager.error("Failed to Start BrowserStack Instance, " + e.getMessage());
        }

        if (!baseUrl.isEmpty()) {
            getDriver().navigate().to(baseUrl);
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

        if (EnvType.valueOf(Properties.executionOptions.environmentType()) == EnvType.GRID) {
            GridFactory.gridTearDown();
        }

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