package driverfactory.webdriver.localdriver;

import java.time.Duration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import tools.engineconfigurations.Configurations;

public class FirefoxDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--" + Configurations.web.executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(Configurations.timeouts.scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(Configurations.timeouts.pageLoadTimeout()));


        if (!Configurations.executionOptions.proxySettings().isEmpty()) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(Configurations.executionOptions.proxySettings());
            proxy.setSslProxy(Configurations.executionOptions.proxySettings());
            options.setProxy(proxy);
        }

        driver = new FirefoxDriver(options);
    }
}
