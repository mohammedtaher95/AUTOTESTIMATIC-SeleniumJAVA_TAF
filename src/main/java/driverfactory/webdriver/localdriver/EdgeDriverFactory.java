package driverfactory.webdriver.localdriver;

import driverfactory.webdriver.helpers.MobileEmulation;
import java.time.Duration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import tools.engineconfigurations.Configurations;

public class EdgeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--" + Configurations.web.executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(Configurations.timeouts.scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(Configurations.timeouts.pageLoadTimeout()));

        if (Configurations.web.isMobileEmulation()) {
            options.setExperimentalOption("mobileEmulation",
                    MobileEmulation.setEmulationSettings());
        }

        if (!Configurations.executionOptions.proxySettings().isEmpty()) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(Configurations.executionOptions.proxySettings());
            proxy.setSslProxy(Configurations.executionOptions.proxySettings());
            options.setProxy(proxy);
        }

        driver = new EdgeDriver(options);
    }
}
