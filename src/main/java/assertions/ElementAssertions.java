package assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import utilities.LoggingManager;

public class ElementAssertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final By by;
    private String actual;

    public ElementAssertions(Assertion assertion, By by, WebDriver driver) {
        this.assertion = assertion;
        this.by = by;
        driverThreadLocal.set(driver);
    }

    public Validations text() {
        this.actual = driverThreadLocal.get().findElement(by).getText();
        return new Validations();
    }

    public Validations attribute(String addedAttribute) {
        this.actual = driverThreadLocal.get().findElement(by).getDomAttribute(addedAttribute);
        return new Validations();
    }

    @SuppressWarnings("all")
    public void isDisplayed() {
        try {
            this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isDisplayed());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is Displayed");

        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1]
                    + " is NOT Displayed");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isHidden() {
        try {
            this.assertion.assertTrue(!driverThreadLocal.get().findElement(by).isDisplayed());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is NOT Displayed");
        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1] + " is DISPLAYED");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isEnabled() {
        try {
            this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isEnabled());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is Enabled");
        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1] + " is NOT Enabled");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isDisabled() {
        try {
            this.assertion.assertTrue(!driverThreadLocal.get().findElement(by).isEnabled());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is Disabled");
        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1] + " is NOT Disabled");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isSelected() {
        try {
            this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isSelected());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is Selected");
        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1] + " is NOT Selected");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void isNotSelected() {
        try {
            this.assertion.assertTrue(!driverThreadLocal.get().findElement(by).isSelected());
            LoggingManager.info("Element: " + by.toString().split(":", 2)[1] + " is not Selected");

        } catch (AssertionError e) {
            LoggingManager.error("Element: " + by.toString().split(":", 2)[1] + " is SELECTED");
            throw e;
        }
    }

    @SuppressWarnings("all")
    public void removeDriver() {
        driverThreadLocal.remove();
    }

    public class Validations {

        private Validations() {

        }

        @SuppressWarnings("all")
        public void contains(String expected) {
            try {
                assertion.assertTrue(actual.contains(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            } catch (AssertionError e) {
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        @SuppressWarnings("all")
        public void doesNotContain(String expected) {
            try {
                assertion.assertTrue(actual.contains(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            } catch (AssertionError e) {
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        @SuppressWarnings("all")
        public void isNotEqualTo(String expected) {
            try {
                assertion.assertTrue(!actual.equalsIgnoreCase(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            } catch (AssertionError e) {
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        @SuppressWarnings("all")
        public void isEqualTo(String expected) {
            try {
                assertion.assertTrue(actual.equalsIgnoreCase(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            } catch (AssertionError e) {
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

    }

}
