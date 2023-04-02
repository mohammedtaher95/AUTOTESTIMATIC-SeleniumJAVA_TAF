package driverFactory;

import browserActions.BrowserActions;
import constants.DriverType;
import constants.EnvType;
import driverFactory.localDriver.*;
import elementActions.ElementActions;
import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.Reporter;
import tools.properties.DefaultProperties;
import utilities.LoggingManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public class Webdriver{

    //ITest Context from TestNG to read XML File
    //Reporter.getCurrentTestResult().
    //                getTestContext().getCurrentXmlTest().getParameter("browserName")

    private static final ThreadLocal<WebDriver> Driver = new ThreadLocal<>();
    public static LoggingManager logMan;

    public Webdriver(){

        if(EnvType.valueOf(DefaultProperties.platform.EnvironmentType()) == EnvType.LOCAL){
            localDriverInit(Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName"));
        }

        if(EnvType.valueOf(DefaultProperties.platform.EnvironmentType()) == EnvType.GRID){
            gridInit();
        }


        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());

        BrowserActions browserActions = new BrowserActions();
        ElementActions elementActions = new ElementActions();

        //logMan = new LoggingManager();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        if(!DefaultProperties.capabilities.baseURL().isEmpty())
        {
            getDriver().navigate().to(DefaultProperties.capabilities.baseURL());
        }
    }


    public void localDriverInit(String browserName){
        setDriver(DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver());
    }

    public static void gridInit(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName =Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setCapability("browserName", browserName);
        try {
            setDriver(new RemoteWebDriver(new URL(DefaultProperties.platform.RemoteURL()), capabilities));
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
