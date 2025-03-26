package assertions;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import utilities.LoggingManager;

public class ObjectAssertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final Object actualObject;

    public ObjectAssertions(Assertion assertion, Object object, WebDriver driver) {
        this.assertion = assertion;
        this.actualObject = object;
        driverThreadLocal.set(driver);
    }

    @SuppressWarnings("all")
    public void contains(@NotNull Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().contains(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());

        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void doesNotContain(@NotNull Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().contains(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isNotEqualTo(@NotNull Object expected) {
        try {
            assertion.assertTrue(!actualObject.toString().equalsIgnoreCase(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isEqualTo(@NotNull Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().equalsIgnoreCase(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void removeDriver() {
        driverThreadLocal.remove();
    }

}
