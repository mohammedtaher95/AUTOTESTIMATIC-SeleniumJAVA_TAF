package driverfactory;

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

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static final String ERROR_MSG = "WebDriver instance NOT setup for current thread";

    public Webdriver() {
        createWebDriver();
        if(driverThreadLocal.get() == null){
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
        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }


    public void localDriverInit() {
        String baseURL = getCapabilities().baseURL();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

    }

    public void gridInit() {
        String baseURL = getCapabilities().baseURL();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().window().maximize();
            setRemoteDriver(driver);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }
    }


    private void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    private void setRemoteDriver(RemoteWebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public WebDriver getDriver() {
        if(driverThreadLocal.get() == null){
            createWebDriver();
        }
        assert driverThreadLocal.get() != null;
        return driverThreadLocal.get();
    }

    public void quit() {
        assert driverThreadLocal.get() != null;
        driverThreadLocal.get().manage().deleteAllCookies();
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();
    }

    public ElementActions element(){
        return new ElementActions(getDriver());
    }

    public BrowserActions browser(){
        return new BrowserActions(getDriver());
    }

}