package driverfactory.webdriver.localdriver;

import driverfactory.webdriver.helpers.MobileEmulation;
import java.time.Duration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tools.engineconfigurations.Configurations;

public class ChromeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--log-level=SEVERE");
        options.addArguments("--enable-logging");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("useAutomationExtension", "false");
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
        driver = new ChromeDriver(options);
    }
}
