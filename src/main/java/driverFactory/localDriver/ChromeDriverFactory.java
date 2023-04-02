package driverFactory.localDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tools.properties.DefaultProperties;

public class ChromeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--" + DefaultProperties.capabilities.executionMethod(), "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
}
