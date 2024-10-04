package tools.listeners.webdriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.properties.Properties;
import utilities.LoggingManager;
import utilities.javascript.JavaScriptWaitManager;

public class WebDriverListeners implements org.openqa.selenium.support.events.WebDriverListener {

    private final WebDriver driver;

    public WebDriverListeners(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        // this method is empty
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        LoggingManager.error(method.getName() + " action failed.");
        LoggingManager.error(e);
    }

    /******************************** Browser Actions Listeners ***********************************/

    @Override
    public void afterGet(WebDriver driver, String url) {
        LoggingManager.info("Getting to \"" + url + "\".");
    }

//    @Override
//    public void afterGetCurrentUrl(String result, WebDriver driver) {
//        LoggingManager.info("Current url is: \"" + result + "\".");
//    }

    @Override
    public void afterGetTitle(WebDriver driver, String result) {
        LoggingManager.info("Current Page Title is: \"" + result + "\".");
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        LoggingManager.info("Navigating to url \"" + url + "\".");
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, URL url) {
        LoggingManager.info("Navigating to url \"" + url + "\".");
    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        LoggingManager.info("Navigating back.");
    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        LoggingManager.info("Navigating forward.");
    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        LoggingManager.info("Refreshing current page......");
    }

    @Override
    public void afterGetPageSource(WebDriver driver, String result) {
        LoggingManager.info("Getting Page source: " + result);
    }

    @Override
    public void beforeDeleteCookie(WebDriver.Options options, Cookie cookie) {
        LoggingManager.info("Deleting Cookie: " + cookie + " ......");
    }

    @Override
    public void beforeDeleteAllCookies(WebDriver.Options options) {
        LoggingManager.info("Deleting All Cookies.....");
    }


    /******************************* Element Actions Listeners ************************************/


    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        new JavaScriptWaitManager(driver).waitForLazyLoading();
        try {
            new FluentWait<>(driver)
                  .withTimeout(
                        Duration.ofSeconds(Properties.timeouts.elementIdentificationTimeout()))
                  .pollingEvery(Duration.ofMillis(500))
                  .ignoring(NoSuchElementException.class)
                  .ignoring(StaleElementReferenceException.class)
                  .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            // In case the element was not found / not visible and the timeout expired
            LoggingManager.error(timeoutException.getMessage() + " || "
                  + timeoutException.getCause().getMessage()
                  .substring(0, timeoutException.getCause().getMessage().indexOf("\n")));
            throw timeoutException;
        }

    }

    @Override
    public void afterClose(WebDriver driver) {
        LoggingManager.info("Successfully Closed Driver.");
    }

    @Override
    public void afterQuit(WebDriver driver) {
        LoggingManager.info("Successfully Quit Driver.");
    }

    // WebElement

    @Override
    public void beforeClick(WebElement element) {
        LoggingManager.info("Wait for " + getElementName(element) + " to be clickable");

        try {
            (new WebDriverWait(this.driver,
                  Duration.ofSeconds(Properties.timeouts.elementIdentificationTimeout())))
                  .until(ExpectedConditions.elementToBeClickable(element));
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            LoggingManager.error(timeoutException);
            throw timeoutException;
        }

        try {
            LoggingManager.info("Click on " + getElementName(element) + ".");
        } catch (Exception throwable) {
            LoggingManager.info("Click.");
        }
    }

    @Override
    public void beforeSubmit(WebElement element) {
        try {
            LoggingManager.info("Submit " + getElementName(element) + ".");
        } catch (Exception throwable) {
            LoggingManager.info("Submit.");
        }
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(keysToSend).toList().forEach(stringBuilder::append);
        try {
            LoggingManager.info(
                  "Type \"" + stringBuilder + "\" into " + getElementName(element) + ".");
        } catch (Exception throwable) {
            LoggingManager.info("Type \"" + stringBuilder + "\".");
        }
    }

    @Override
    public void beforeSendKeys(Alert alert, String text) {
        LoggingManager.info("Type \"" + text + "\" into Alert.");
    }

    @Override
    public void beforeClear(WebElement element) {
        LoggingManager.info("Clear " + getElementName(element) + ".");
    }

    @Override
    public void afterGetAttribute(WebElement element, String name, String result) {
        try {
            LoggingManager.info("Get Attribute \"" + name + "\" from " + getElementName(element)
                  + ", value is \"" + result + "\".");
        } catch (Exception throwable) {
            LoggingManager.info("Get Attribute \"" + name + "\", value is \"" + result + "\".");
        }
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        try {
            LoggingManager.info(
                  "Get Text from " + getElementName(element) + ", text is \"" + result + "\".");
        } catch (Exception throwable) {
            LoggingManager.info("Get Text, text is :\"" + result + "\".");
        }
    }


    // Alert


    private String getElementName(WebElement element) {
        String accessibleName = element.getAccessibleName();
        if ("".equals(accessibleName)) {
            return "element";
        } else {
            return "\"" + accessibleName + "\"";
        }
    }
}
