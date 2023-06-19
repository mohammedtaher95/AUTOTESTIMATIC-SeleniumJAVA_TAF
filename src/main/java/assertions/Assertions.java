package assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

public class Assertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public Assertions(WebDriver driver){
        driverThreadLocal.set(driver);
        assertion = new Assertion();
    }

    public ElementAssertions element(By by){
        return new ElementAssertions(this.assertion, by, driverThreadLocal.get());
    }

    public BrowserAssertions browser(){
        return new BrowserAssertions(this.assertion, driverThreadLocal.get());
    }

    public void removeDriver(){
        driverThreadLocal.remove();
    }
}
