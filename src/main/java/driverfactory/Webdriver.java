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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static tools.properties.PropertiesHandler.*;


public class Webdriver{

    private static final ThreadLocal<WebDriver> Driver = new ThreadLocal<>();
    private static final ThreadLocal<ElementActions> elementActions = new ThreadLocal<>();
    private static final ThreadLocal<BrowserActions> browserActions = new ThreadLocal<>();

    public Webdriver(){

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL){
            localDriverInit();
        }

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID){
            gridInit();
        }
        elementActions.set(new ElementActions(getDriver()));
        browserActions.set(new BrowserActions(getDriver()));


        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }

    public static synchronized void localDriverInit(){
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        driver.manage().window().maximize();
        String baseURL = getCapabilities().baseURL();
        if (!baseURL.isEmpty()) {
            driver.navigate().to(baseURL);
        }
        setDriver(ThreadGuard.protect(driver));

    }

    public static synchronized void gridInit(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            setRemoteDriver(driver);
            String baseURL = getCapabilities().baseURL();
            if (!baseURL.isEmpty()) {
                getDriver().navigate().to(baseURL);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void quit(){
        WebDriver driver = Driver.get();
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            Driver.remove();
            elementActions.remove();
            browserActions.remove();
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
        return Driver.get();
    }

}