package assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

public class ElementAssertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final By by;
    private String actual;
    private String elementAttribute;

    public ElementAssertions(Assertion assertion, By by, WebDriver driver){
        this.assertion = assertion;
        this.by = by;
        driverThreadLocal.set(driver);
    }

    public ElementAssertions text(){
        this.actual = driverThreadLocal.get().findElement(by).getText();
        return this;
    }

    public ElementAssertions attribute(String addedAttribute){
        this.actual = driverThreadLocal.get().findElement(by).getAttribute(addedAttribute);
        return this;
    }

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

    public void isDisplayed(){
        this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isDisplayed());
    }

    public void isEnabled(){
        this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isEnabled());
    }

    public void isDisabled(){
        this.assertion.assertTrue(!driverThreadLocal.get().findElement(by).isEnabled());
    }

    public void isSelected(){
        this.assertion.assertTrue(driverThreadLocal.get().findElement(by).isSelected());
    }

    public void isNotSelected(){
        this.assertion.assertTrue(!driverThreadLocal.get().findElement(by).isSelected());
    }


}
