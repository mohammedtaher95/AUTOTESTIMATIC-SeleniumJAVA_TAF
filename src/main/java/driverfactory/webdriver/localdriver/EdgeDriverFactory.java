package driverfactory.webdriver.localdriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import static tools.properties.PropertiesHandler.*;


public class EdgeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        driver = new EdgeDriver(options);
    }
}
