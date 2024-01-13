package driverfactory.webdriver.localdriver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import tools.properties.Properties;

import java.time.Duration;

public class FirefoxDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--" + Properties.web.executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(Properties.timeouts.scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(Properties.timeouts.pageLoadTimeout()));


        if(!Properties.executionOptions.proxySettings().isEmpty()){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(Properties.executionOptions.proxySettings());
            proxy.setSslProxy(Properties.executionOptions.proxySettings());
            options.setProxy(proxy);
        }

        driver = new FirefoxDriver(options);
    }
}
