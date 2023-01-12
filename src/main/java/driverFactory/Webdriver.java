package driverFactory;

import constants.CrossBrowserMode;
import constants.DriverType;
import constants.EnvType;
import driverFactory.localDriver.*;
import driverFactory.remoteDriver.GridConfig;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import tools.properties.DefaultProperties;

import java.io.IOException;
import java.time.Duration;


public class Webdriver {


    private static ThreadLocal<org.openqa.selenium.WebDriver> Driver = new ThreadLocal<>();


    public Webdriver() throws IOException {

        if(EnvType.valueOf(DefaultProperties.platform.EnvironmentType()) == EnvType.LOCAL){
            localDriverInit(Reporter.getCurrentTestResult().
                    getTestContext().getCurrentXmlTest().getParameter("browserName"));
        }

        if(EnvType.valueOf(DefaultProperties.platform.EnvironmentType()) == EnvType.GRID){
            GridConfig.gridInit();
        }

        if(EnvType.valueOf(DefaultProperties.platform.EnvironmentType()) == EnvType.CLOUD){
            //remoteDriverInit(browserName);
        }

        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
        getDriver().navigate().to(DefaultProperties.capabilities.baseURL());
    }


    public void localDriverInit(String browserName){
        setDriver(DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase()))
                .getDriver());
    }


    public void quit() throws IOException {
        getDriver().manage().deleteAllCookies();
        getDriver().quit();
        Driver.remove();
    }


    protected static void setDriver(WebDriver driver){
        Driver.set(driver);
    }

    public static WebDriver getDriver(){
        return Driver.get();
    }

}