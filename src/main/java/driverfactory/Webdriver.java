package driverfactory;

import browseractions.BrowserActions;
import constants.DriverType;
import constants.EnvType;
import driverfactory.localdriver.*;
import elementactions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Reporter;
import utilities.LoggingManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static tools.properties.PropertiesHandler.*;


public class Webdriver implements WebDriver{

    private static final ThreadLocal<WebDriver> Driver = new ThreadLocal<>();
    private ThreadLocal<ElementActions> elementActions = new ThreadLocal<>();
    private ThreadLocal<BrowserActions> browserActions = new ThreadLocal<>();

    private static final String ERROR_MSG = "WebDriver instance NOT setup for current thread";

    public Webdriver() {
        createWebDriver();
    }

    private synchronized void createWebDriver() {
        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.LOCAL) {
            localDriverInit();
        }

        if (EnvType.valueOf(getPlatform().environmentType()) == EnvType.GRID) {
            gridInit();
        }
        elementActions.set(new ElementActions(getDriver()));
        browserActions.set(new BrowserActions(getDriver()));
        LoggingManager.info("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());
    }


    public synchronized void localDriverInit() {
        String baseURL = getCapabilities().baseURL();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        WebDriver driver = DriverFactory.getDriverFactory(DriverType.valueOf(browserName.toUpperCase())).getDriver();
        assert driver != null;
        driver.manage().window().maximize();
        setDriver(ThreadGuard.protect(driver));
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }

    }

    public synchronized void gridInit() {
        String baseURL = getCapabilities().baseURL();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserName = Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName");
        capabilities.setBrowserName(browserName);
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(getPlatform().remoteURL()), capabilities);
            assert driver != null;
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().window().maximize();
            setRemoteDriver(driver);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!baseURL.isEmpty()) {
            getDriver().navigate().to(baseURL);
        }
    }


    private synchronized void setDriver(WebDriver driver) {
        Driver.set(driver);
    }

    private synchronized void setRemoteDriver(RemoteWebDriver driver) {
        Driver.set(driver);
    }


    public synchronized WebDriver makeAction() {
        if(Driver.get() == null){
            createWebDriver();
        }
        return Optional.ofNullable(Driver.get())
                .orElseThrow(() -> new IllegalStateException(ERROR_MSG));
    }

    public synchronized WebDriver getDriver() {
        if(Driver.get() == null){
            createWebDriver();
        }
        return Optional.ofNullable(Driver.get())
                .orElseThrow(() -> new IllegalStateException(ERROR_MSG));
    }


    @Override
    public synchronized void get(String url) {
        Driver.get().get(url);
    }

    @Override
    public synchronized String getCurrentUrl() {
        return Driver.get().getCurrentUrl();
    }

    @Override
    public synchronized String getTitle() {
        return Driver.get().getTitle();
    }

    @Override
    public synchronized List<WebElement> findElements(By by) {
        return Driver.get().findElements(by);
    }

    @Override
    public synchronized WebElement findElement(By by) {
        return Driver.get().findElement(by);
    }

    @Override
    public synchronized String getPageSource() {
        return Driver.get().getPageSource();
    }

    @Override
    public synchronized void close() {
        Driver.get().close();
    }

    @Override
    public synchronized void quit() {
        assert Driver.get() != null;
        Driver.get().quit();
        Driver.remove();
    }

    @Override
    public synchronized Set<String> getWindowHandles() {
        return Driver.get().getWindowHandles();
    }

    @Override
    public synchronized String getWindowHandle() {
        return Driver.get().getWindowHandle();
    }

    @Override
    public synchronized WebDriver.TargetLocator switchTo() {
        return Driver.get().switchTo();
    }

    @Override
    public synchronized WebDriver.Navigation navigate() {
        return Driver.get().navigate();
    }

    @Override
    public synchronized WebDriver.Options manage() {
        return Driver.get().manage();
    }

}