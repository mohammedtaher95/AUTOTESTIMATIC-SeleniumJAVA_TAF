package driverfactory.webdriver.gridservice;

import driverfactory.webdriver.helpers.MobileEmulation;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.LoggingManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static tools.properties.PropertiesHandler.*;

public class GridFactory {

    private GridFactory() {

    }

    public static void gridUp(){
        try {
            LoggingManager.info("Initializing Selenium Grid via Docker Compose");
            if(SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("dockerComposeUp.bat");
            }
            else {
                Runtime.getRuntime().exec("sh dockerComposeUp.sh");
            }
            Thread.sleep(6000);
        } catch (IOException e) {
            LoggingManager.error("Unable to Start Selenium Grid");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void gridTearDown() {
        LoggingManager.info("Tearing down Selenium Grid....");
        try {
            if(SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("dockerComposeDown.bat");
            }
            else {
                Runtime.getRuntime().exec("sh dockerComposeDown.sh");
            }
        } catch (IOException e) {
            LoggingManager.warn("Selenium Grid isn't up and running");
        }
    }

    public static RemoteWebDriver getRemoteDriver(String browserName) {

        RemoteWebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browserName);

        if(getCapabilities().isMobileEmulation()){
            if(browserName.equalsIgnoreCase("chrome")){
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("mobileEmulation", MobileEmulation.setEmulationSettings());
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            }
            if(browserName.equalsIgnoreCase("edge")){
                EdgeOptions options = new EdgeOptions();
                options.setExperimentalOption("mobileEmulation", MobileEmulation.setEmulationSettings());
                capabilities.setCapability(EdgeOptions.CAPABILITY, options);
            }
        }

        if(!getPlatform().proxySettings().isEmpty()){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(getPlatform().proxySettings());
            proxy.setSslProxy(getPlatform().proxySettings());
            capabilities.setCapability(CapabilityType.PROXY, proxy);
        }
        try {
            driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
        } catch (MalformedURLException e) {
            LoggingManager.error("Unable to create Remote WebDriver: " + e.getMessage());
        }
        return driver;
    }
}
