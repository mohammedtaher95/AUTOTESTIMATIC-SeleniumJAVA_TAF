package driverfactory.webdriver.localdriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import static tools.properties.PropertiesHandler.*;

public class FirefoxDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        driver = new FirefoxDriver(options);
    }
}
