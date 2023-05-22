package elementactions;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;


public class ElementActions {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static FluentWait<WebDriver> driverWait;
    Actions action;

    public ElementActions(WebDriver driver){
        ElementActions.driver.set(driver);
        driverWait = new FluentWait<>(ElementActions.driver.get()).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        action = new Actions(ElementActions.driver.get());
    }

    public static void clickButton(By btn){
        driver.get().findElement(btn).click();
    }

    public static void fillField(By field, String value){

        WebElement txtField = driver.get().findElement(field);
        txtField.clear();
        txtField.sendKeys(value);
    }

    public static void clearField(By field){
        WebElement txtField = driver.get().findElement(field);
        txtField.clear();
    }

    public static boolean elementDisplayed(By by){
        return driver.get().findElement(by).isDisplayed();
    }

    public static void selectItemByIndex(By by, int index)
    {
        new Select(driver.get().findElement(by)).selectByIndex(index);
    }

    public static void selectItemByText(By by, String text)
    {
        new Select(driver.get().findElement(by)).selectByVisibleText(text);
    }

    public static String getElementText(By by){
        return driver.get().findElement(by).getText();
    }

    public static void waitForVisibility(By by){
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitForInvisibility(By by) {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static boolean waitForElementToBeClickable(By by)
    {
        waitForVisibility(by);
        return driver.get().findElement(by).isEnabled();
    }


    public static List<WebElement> findElements(By by){
        return driver.get().findElements(by);
    }

    public void removeDriver(){
        driver.remove();
    }
}
