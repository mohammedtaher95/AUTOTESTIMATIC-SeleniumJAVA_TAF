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


public class Webdriver extends ThreadLocal<WebDriver> {

    private static final ThreadLocal<WebDriver> Driver = new ThreadLocal<>();
    private static final ThreadLocal<ElementActions> elementActions = new ThreadLocal<>();
    private static final ThreadLocal<BrowserActions> browserActions = new ThreadLocal<>();

    public Webdriver(){

        String baseURL = getCapabilities().baseURL();
        createWebDriver();
        elementActions.set(new ElementActions(getDriver()));
        browserActions.set(new BrowserActions(getDriver()));
        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());

        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

    }

    private static synchronized void createWebDriver(){
        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL){
            localDriverInit();
        }

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID){
            gridInit();
        }
    }

    @Override
    protected synchronized WebDriver initialValue() {
        WebDriver driver = null;
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL) {
            driver = DriverFactory.getDriverFactory(DriverType.CHROME).getDriver();
        }
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            try {
                driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));
        elementActions.set(new ElementActions(getDriver()));
        browserActions.set(new BrowserActions(getDriver()));
        LoggingManager.info("INITIAL THREAD: " + Thread.currentThread().getId() + ", " +"DRIVER = " + getDriver());
        return driver;
    }


    public static synchronized void localDriverInit(){
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));

    }

    public static synchronized void gridInit(){
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
    }

    public synchronized void quit(){
        if (Driver.get() != null) {
            Driver.get().manage().deleteAllCookies();
            Driver.get().quit();
            elementActions.get().removeDriver();
            browserActions.get().removeDriver();
            elementActions.remove();
            browserActions.remove();
            Driver.remove();
        }
    }


    protected static void setDriver(WebDriver driver){
        Driver.set(driver);
    }

    protected static void setRemoteDriver(RemoteWebDriver driver){
        Driver.set(driver);
    }


    public WebDriver makeAction(){
        return Driver.get();
    }

    public static WebDriver getDriver(){
        WebDriver driver = Driver.get();
        if(driver == null){
            createWebDriver();
            driver = Driver.get();
        }
        return driver;
    }

}