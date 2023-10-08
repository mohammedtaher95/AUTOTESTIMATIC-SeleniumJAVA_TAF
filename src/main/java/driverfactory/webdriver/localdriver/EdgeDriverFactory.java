package driverfactory.webdriver.localdriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

import static tools.properties.PropertiesHandler.*;


public class EdgeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(getTimeouts().scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(getTimeouts().pageLoadTimeout()));

        if(!getPlatform().proxySettings().isEmpty()){
            options.addArguments("--proxy-server=http://", getPlatform().proxySettings());
        }

        driver = new EdgeDriver(options);
    }
}
