package driverfactory;

import assertions.Assertions;
import browseractions.BrowserActions;
import constants.DriverType;
import constants.EnvType;
import driverfactory.localdriver.*;
import elementactions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Reporter;
import utilities.LoggingManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


import static tools.properties.PropertiesHandler.*;


public class Webdriver{

    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public Webdriver() {
        LoggingManager.info("Initializing WebDriver.....");
        createWebDriver();
        if(driverThreadLocal.get() == null){
            createWebDriver();
        }
    }

    private synchronized void createWebDriver() {
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL) {
            localDriverInit();
        }

        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID) {
            gridInit();
        }
        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }


    public synchronized void localDriverInit() {
        String baseURL = getCapabilities().baseURL();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        LoggingManager.info("Starting " + browserName + " Driver Locally in " + getCapabilities().executionMethod() + " mode");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

    }

    public synchronized void gridInit() {
        String baseURL = getCapabilities().baseURL();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        LoggingManager.info("Starting Selenium Grid on: " + getPlatform().remoteURL());
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            setRemoteDriver(driver);
        } catch (MalformedURLException e) {
            LoggingManager.error("Unable to create Remote WebDriver: " + e.getMessage());
        }
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }
    }


    private synchronized void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    private synchronized void setRemoteDriver(RemoteWebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public synchronized WebDriver getDriver() {
        if(driverThreadLocal.get() == null){
            createWebDriver();
        }
        assert driverThreadLocal.get() != null;
        return driverThreadLocal.get();
    }

    public synchronized void quit() {
        LoggingManager.info("Quitting Driver.....");
        assert driverThreadLocal.get() != null;
        driverThreadLocal.get().manage().deleteAllCookies();
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();
    }

    public synchronized ElementActions element(){
        return new ElementActions(getDriver());
    }

    public synchronized BrowserActions browser(){
        return new BrowserActions(getDriver());
    }

    public synchronized Assertions assertThat(){
        return new Assertions(getDriver());
    }

}