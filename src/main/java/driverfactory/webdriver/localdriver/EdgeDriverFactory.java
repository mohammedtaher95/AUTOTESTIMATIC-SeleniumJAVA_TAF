package driverfactory.webdriver.localdriver;

import driverfactory.webdriver.helpers.MobileEmulation;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import tools.properties.Properties;

import java.time.Duration;



public class EdgeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--" + Properties.web.executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(Properties.timeouts.scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(Properties.timeouts.pageLoadTimeout()));

        if(Properties.web.isMobileEmulation()){
            options.setExperimentalOption("mobileEmulation", MobileEmulation.setEmulationSettings());
        }

        if(!Properties.executionOptions.proxySettings().isEmpty()){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(Properties.executionOptions.proxySettings());
            proxy.setSslProxy(Properties.executionOptions.proxySettings());
            options.setProxy(proxy);
        }

        driver = new EdgeDriver(options);
    }
}
