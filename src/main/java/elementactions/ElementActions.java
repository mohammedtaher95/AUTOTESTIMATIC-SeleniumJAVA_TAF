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
    private static final ThreadLocal<WebDriver> eActionsDriver = new ThreadLocal<>();
    private static FluentWait<WebDriver> driverWait;
    Actions action;

    public ElementActions(WebDriver driver){
        eActionsDriver.set(driver);
        driverWait = new FluentWait<>(eActionsDriver.get()).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        action = new Actions(eActionsDriver.get());
    }

    public void click(By btn){
        eActionsDriver.get().findElement(btn).click();
    }

    public void fillField(By field, String value){
        clearField(field);
        eActionsDriver.get().findElement(field).sendKeys(value);
    }

    public void clearField(By field){
        eActionsDriver.get().findElement(field).clear();
    }

    public boolean isDisplayed(By by){
        return eActionsDriver.get().findElement(by).isDisplayed();
    }

    public void selectItemByIndex(By by, int index)
    {
        new Select(eActionsDriver.get().findElement(by)).selectByIndex(index);
    }

    public void selectItemByText(By by, String text)
    {
        new Select(eActionsDriver.get().findElement(by)).selectByVisibleText(text);
    }

    public String getElementText(By by){
        return eActionsDriver.get().findElement(by).getText();
    }

    public void waitForVisibility(By by){
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForInvisibility(By by) {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public boolean waitForElementToBeClickable(By by)
    {
        waitForVisibility(by);
        return eActionsDriver.get().findElement(by).isEnabled();
    }


    public List<WebElement> findElements(By by){
        return eActionsDriver.get().findElements(by);
    }

}
