package driverfactory.webdriver.localdriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static tools.properties.PropertiesHandler.*;

public class ChromeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--log-level=SEVERE");
        options.addArguments("--enable-logging");
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }
}
