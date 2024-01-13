package driverfactory.webdriver.localdriver;

import driverfactory.webdriver.helpers.MobileEmulation;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tools.properties.Properties;
import tools.properties.Properties;

import java.time.Duration;


public class ChromeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--log-level=SEVERE");
        options.addArguments("--enable-logging");
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
        driver = new ChromeDriver(options);
    }
}
