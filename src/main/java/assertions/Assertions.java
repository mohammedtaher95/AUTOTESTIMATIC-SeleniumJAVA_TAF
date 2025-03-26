package assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.asserts.Assertion;

public class Assertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final FluentWait<WebDriver> driverWait;

    public Assertions(WebDriver driver, FluentWait<WebDriver> wait) {
        driverThreadLocal.set(driver);
        assertion = new Assertion();
        this.driverWait = wait;
    }

    public ElementAssertions element(By by) {
        try {
            this.driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException exception) {
            throw new NoSuchElementException("Element doesn't exist");
        }
        return new ElementAssertions(this.assertion, by, driverThreadLocal.get());
    }

    public BrowserAssertions browser() {
        return new BrowserAssertions(this.assertion, driverThreadLocal.get());
    }

    public ObjectAssertions object(Object object) {
        return new ObjectAssertions(this.assertion, object, driverThreadLocal.get());
    }

    @SuppressWarnings("all")
    public void removeDriver() {
        driverThreadLocal.remove();
    }
}
