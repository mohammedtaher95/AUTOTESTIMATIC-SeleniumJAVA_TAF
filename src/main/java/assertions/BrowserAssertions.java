package assertions;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

public class BrowserAssertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private String actual;
    private String browserAttribute;

    public BrowserAssertions(Assertion assertion, WebDriver driver){
        this.assertion = assertion;
        driverThreadLocal.set(driver);
    }

    public BrowserAssertions url(){
        this.actual = driverThreadLocal.get().getCurrentUrl();
        return this;
    }

    public BrowserAssertions title(){
        this.actual = driverThreadLocal.get().getTitle();
        return this;
    }

//    public void attribute(String browserAttribute){
//        this.browserAttribute = driverThreadLocal.get().
//    }

    public void contains(String expected){
        this.assertion.assertTrue(actual.contains(expected));
    }

    public void doesNotContain(String expected){
        this.assertion.assertTrue(actual.contains(expected));
    }

    public void isNotEqualTo(String expectedText){
        this.assertion.assertNotEquals(actual, expectedText);

    }

    public void isEqualTo(String expectedText){
        this.assertion.assertEquals(actual, expectedText);
    }
}
