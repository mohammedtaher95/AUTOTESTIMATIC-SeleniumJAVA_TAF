package driverfactory.webdriver.localdriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

import static tools.properties.PropertiesHandler.*;

public class FirefoxDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(getTimeouts().scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(getTimeouts().pageLoadTimeout()));

        if(!getPlatform().proxySettings().isEmpty()){
            options.addArguments("--proxy-server=http://", getPlatform().proxySettings());
        }

        driver = new FirefoxDriver(options);
    }
}
