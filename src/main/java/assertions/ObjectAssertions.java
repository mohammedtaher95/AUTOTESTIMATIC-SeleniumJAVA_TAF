package assertions;

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

    public void contains(Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().contains(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());

        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    public void doesNotContain(Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().contains(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    public void isNotEqualTo(Object expected) {
        try {
            assertion.assertTrue(!actualObject.toString().equalsIgnoreCase(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    public void isEqualTo(Object expected) {
        try {
            assertion.assertTrue(actualObject.toString().equalsIgnoreCase(expected.toString()));
            LoggingManager.info("Expected: " + expected + ", Actual: " + actualObject.toString());
        } catch (AssertionError e) {
            LoggingManager.error(
                  "Expected: " + expected + ", but Actual: " + actualObject.toString());
            throw e;
        }
    }

    public void removeDriver() {
        driverThreadLocal.remove();
    }

}
