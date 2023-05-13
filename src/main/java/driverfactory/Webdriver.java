package driverfactory;

import constants.DriverType;
import constants.EnvType;
import driverfactory.localdriver.*;
import elementactions.ElementActions;
import lombok.SneakyThrows;
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

    public Webdriver(){

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL){
            localDriverInit();
        }

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID){
            gridInit();
        }
        ElementActions elementActions = new ElementActions(getDriver());
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }

    public static synchronized void localDriverInit(){
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
            setDriver(ThreadGuard.protect(driver));
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
        }
    }


    protected static void setDriver(WebDriver driver){
        Driver.set(driver);
    }

    protected static void setDriver(RemoteWebDriver driver){
        Driver.set(driver);
    }


    public static WebDriver makeAction(){
        return Driver.get();
    }

    public static WebDriver getDriver(){
        return Driver.get();
    }

}