package driverfactory.webdriver.localdriver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import static tools.properties.PropertiesHandler.*;

public class ChromeDriverFactory extends DriverAbstract {

    @Override
    protected void startDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--log-level=SEVERE");
        options.addArguments("--enable-logging");
        options.addArguments("--" + getCapabilities().executionMethod(), "--window-size=1920,1080");
        options.setScriptTimeout(Duration.ofSeconds(getTimeouts().scriptTimeout()));
        options.setPageLoadTimeout(Duration.ofSeconds(getTimeouts().pageLoadTimeout()));


        if(!getPlatform().proxySettings().isEmpty()){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(getPlatform().proxySettings());
            proxy.setSslProxy(getPlatform().proxySettings());
            options.setProxy(proxy);
        }
        driver = new ChromeDriver(options);
    }
}
