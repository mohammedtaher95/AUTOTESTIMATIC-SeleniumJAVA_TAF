package utilities.javascript;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.engineconfigurations.Configurations;
import utilities.LoggingManager;

public class JavaScriptWaitManager {
    private static final int DURATION = Configurations.timeouts.lazyLoadingTimeout();
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
        JavascriptExecutor jse = (JavascriptExecutor) jsWaitDriver.get();

        if (Configurations.timeouts.waitForLazyLoading()) {
            try {
                waitForJqueryLoadIfDefined(jse);
                waitForAngularIfDefined(jse);
                waitForJsLoadIfDefined(jse);
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

    private void waitForJqueryLoadIfDefined(JavascriptExecutor jse) {
        Boolean jqueryDefined = (Boolean) jse.executeScript("return typeof jQuery != 'undefined'");
        if (Boolean.TRUE.equals(jqueryDefined)) {
            ExpectedCondition<Boolean> jqueryLoad = null;
            try {
                // Wait for jQuery to load
                jqueryLoad = driver -> ((Long) ((JavascriptExecutor) jsWaitDriver.get())
                      .executeScript("return jQuery.active") == 0);
            } catch (NullPointerException e) {
                // do nothing
            }
            // Get JQuery is Ready
            boolean jqueryReady = (Boolean) jse.executeScript("return jQuery.active==0");

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
                              .until(jqueryLoad);
                        //  (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION)))
                        //  .until(jQueryLoad);
                    } catch (NullPointerException e) {
                        // do nothing
                    }

                    tryCounter++;
                    jqueryReady = (Boolean) jse.executeScript("return jQuery.active == 0");
                }
            }
        }
    }

    private void waitForAngularLoad(JavascriptExecutor jse) {

        String angularReadyScript =
              "return angular.element(document).injector()"
                   + ".get('$http').pendingRequests.length === 0";

        // Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> Boolean
              .valueOf(((JavascriptExecutor) Objects.requireNonNull(driver)).executeScript(
                    angularReadyScript).toString());

        // Get Angular is Ready
        boolean angularReady =
              Boolean.parseBoolean(jse.executeScript(angularReadyScript).toString());

        if (!angularReady) {
            // Wait ANGULAR until it is Ready!
            int tryCounter = 0;
            while ((!angularReady) && (tryCounter < 5)) {
                // Wait for Angular to load
                (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION))).until(
                      angularLoad);

                // More Wait for stability (Optional)
                tryCounter++;
                angularReady =
                      Boolean.parseBoolean(jse.executeScript(angularReadyScript).toString());
            }
        }
    }

    private void waitForJsLoadIfDefined(JavascriptExecutor jse) {

        // Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> jse
              .executeScript(SCRIPT).toString().trim()
              .equalsIgnoreCase(STATE);

        // Get JS is Ready
        boolean jsReady = jse.executeScript(SCRIPT).toString().trim()
              .equalsIgnoreCase(STATE);

        // Wait Javascript until it is Ready!
        if (!jsReady) {
            // Wait JS until it is Ready!
            int tryCounter = 0;
            while ((!jsReady) && (tryCounter < 5)) {
                // Wait for Javascript to load
                try {
                    (new WebDriverWait(jsWaitDriver.get(), Duration.ofSeconds(DURATION))).until(
                          jsLoad);
                } catch (org.openqa.selenium.TimeoutException e) {
                    //do nothing
                }
                // More Wait for stability (Optional)
                tryCounter++;
                jsReady = jse.executeScript(SCRIPT).toString().trim().equalsIgnoreCase(STATE);
            }
        }
    }

    private void waitForAngularIfDefined(JavascriptExecutor jse) {
        try {
            Boolean angularDefined =
                  !((Boolean) jse.executeScript("return window.angular === undefined"));
            if (Boolean.TRUE.equals(angularDefined)) {
                Boolean angularInjectorDefined = !((Boolean) jse
                      .executeScript("return angular.element(document).injector() === undefined"));

                if (Boolean.TRUE.equals(angularInjectorDefined)) {
                    waitForAngularLoad(jse);
                }
            }
        } catch (WebDriverException e) {
            // do nothing
        }
    }


    public void removeDriver() {
        jsWaitDriver.remove();
    }

}
