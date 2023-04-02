//package coreConfig;
//
//import org.openqa.selenium.MutableCapabilities;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//public class remoteServiceConfig {
//
//    // declare BrowserStack credentials as environment variables
//    public static final String USERNAME = (System.getenv("BROWSERSTACK_USERNAME") != null) ? System.getenv("BROWSERSTACK_USERNAME") : "mohammedtaher3";
//    public static final String AUTOMATE_KEY = (System.getenv("BROWSERSTACK_ACCESS_KEY") != null) ? System.getenv("BROWSERSTACK_ACCESS_KEY") : "zzrpfakxLdpeBWmsSTzB";
//    // declare remote URL in a variable
//    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
//    // intialize Selenium WebDriver
//    MutableCapabilities capabilities = new MutableCapabilities();
//    WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);
//}
