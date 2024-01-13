package utilities.javascript;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.properties.Properties;
import utilities.LoggingManager;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;


public class JavaScriptWaitManager {
    private static final int DURATION = Properties.timeouts.lazyLoadingTimeout();
    private static final String SCRIPT = "return document.readyState";
    private static final String STATE = "complete";
    private final ThreadLocal<WebDriver> jsWaitDriver = new ThreadLocal<>();


    public JavaScriptWaitManager(WebDriver driver) {
        jsWaitDriver.set(driver);
    }
    

    /**
     * Waits for jQuery, Angular, and/or Javascript if present on the current page.
     */
    public void waitForLazyLoading() {
        JavascriptExecutor jSE = (JavascriptExecutor) jsWaitDriver.get();
        
        if (Properties.timeouts.waitForLazyLoading()) {
            try {
                waitForJQueryLoadIfDefined(jSE);
                waitForAngularIfDefined(jSE);
                waitForJSLoadIfDefined(jSE);
            } catch (NoSuchSessionException | NullPointerException e) {
                // do nothing
            } catch (WebDriverException e) {
                if (!e.getMessage().contains("jQuery is not defined")) {
                    LoggingManager.warn(e);
                }
                // else do nothing
            } catch (Exception e) {
                LoggingManager.error(e);
            }
        }
    }

    private void waitForJQueryLoadIfDefined(JavascriptExecutor jSE) {
        Boolean jQueryDefined = (Boolean) jSE.executeScript("return typeof jQuery != 'undefined'");
        if (Boolean.TRUE.equals(jQueryDefined)) {
            ExpectedCondition<Boolean> jQueryLoad = null;
            try {
                // Wait for jQuery to load
                jQueryLoad = driver -> ((Long) ((JavascriptExecutor) jsWaitDriver.get())
                        .executeScript("return jQuery.active") == 0);
            } catch (NullPointerException e) {
                // do nothing
            }
            // Get JQuery is Ready
            boolean jqueryReady = (Boolean) jSE.executeScript("return jQuery.active==0");

            if (!jqueryReady) {
                // Wait JQuery until it is Ready!
                int tryCounter = 0;
                while ((!jqueryReady) && (tryCounter < 5)) {
                    try {
                        // Wait for jQuery to load
                        new FluentWait<>(jsWaitDriver.get())
                                .withTimeout(Duration.ofSeconds(DURATION))
                                .pollingEvery(Duration.ofMillis(500))
                                .ignoring(NoSuchElementException.class)
                                .ignoring(StaleElementReferenceException.class)
                                .until(jQueryLoad);
//                        (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION))).until(jQueryLoad);
                    } catch (NullPointerException e) {
                        // do nothing
                    }

                    tryCounter++;
                    jqueryReady = (Boolean) jSE.executeScript("return jQuery.active == 0");
                }
            }
        }
    }

    private void waitForAngularLoad(JavascriptExecutor jSE) {

        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

        // Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> Boolean
                .valueOf(((JavascriptExecutor) Objects.requireNonNull(driver)).executeScript(angularReadyScript).toString());

        // Get Angular is Ready
        boolean angularReady = Boolean.parseBoolean(jSE.executeScript(angularReadyScript).toString());

        if (!angularReady) {
            // Wait ANGULAR until it is Ready!
            int tryCounter = 0;
            while ((!angularReady) && (tryCounter < 5)) {
                // Wait for Angular to load
                (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION))).until(angularLoad);

                // More Wait for stability (Optional)
                tryCounter++;
                angularReady = Boolean.parseBoolean(jSE.executeScript(angularReadyScript).toString());
            }
        }
    }

    private void waitForJSLoadIfDefined(JavascriptExecutor jSE) {

        // Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> jSE
                .executeScript(SCRIPT).toString().trim()
                .equalsIgnoreCase(STATE);

        // Get JS is Ready
        boolean jsReady = jSE.executeScript(SCRIPT).toString().trim()
                .equalsIgnoreCase(STATE);

        // Wait Javascript until it is Ready!
        if (!jsReady) {
            // Wait JS until it is Ready!
            int tryCounter = 0;
            while ((!jsReady) && (tryCounter < 5)) {
                // Wait for Javascript to load
                try {
                    (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION))).until(jsLoad);
                } catch (org.openqa.selenium.TimeoutException e) {
                    //do nothing
                }
                // More Wait for stability (Optional)
                tryCounter++;
                jsReady = jSE.executeScript(SCRIPT).toString().trim().equalsIgnoreCase(STATE);
            }
        }
    }

    private void waitForAngularIfDefined(JavascriptExecutor jSE) {
        try {
            Boolean angularDefined = !((Boolean) jSE.executeScript("return window.angular === undefined"));
            if (Boolean.TRUE.equals(angularDefined)) {
                Boolean angularInjectorDefined = !((Boolean) jSE
                        .executeScript("return angular.element(document).injector() === undefined"));

                if (Boolean.TRUE.equals(angularInjectorDefined)) {
                    waitForAngularLoad(jSE);
                }
            }
        } catch (WebDriverException e) {
            // do nothing
        }
    }


    public void removeDriver(){
        jsWaitDriver.remove();
    }

}
