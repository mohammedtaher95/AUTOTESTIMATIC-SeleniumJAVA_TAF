package driverfactory;

import constants.DriverType;
import constants.EnvType;
import driverfactory.localdriver.*;
import elementactions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import utilities.LoggingManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static tools.properties.PropertiesHandler.*;


public class Webdriver{

    private static final ThreadLocal<WebDriver> Driver = new ThreadLocal<>();

    public Webdriver(){

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL){
            localDriverInit(Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName"));
        }

        if(EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID){
            gridInit();
        }

        ElementActions elementActions = new ElementActions();

        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        if(!getCapabilities().baseURL().isEmpty())
        {
            getDriver().navigate().to(getCapabilities().baseURL());
        }
    }


    public void localDriverInit(String browserName){
        setDriver(DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver());
    }

    public static void gridInit(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName =Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        try {
            setDriver(new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void quit(){
        getDriver().manage().deleteAllCookies();
        getDriver().quit();
        Driver.remove();
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
