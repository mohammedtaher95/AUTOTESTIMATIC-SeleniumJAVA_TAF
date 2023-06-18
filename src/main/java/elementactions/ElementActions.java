package elementactions;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import utilities.LoggingManager;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;


public class ElementActions {
    private final ThreadLocal<WebDriver> eActionsDriver = new ThreadLocal<>();
    private final FluentWait<WebDriver> driverWait;
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
        LoggingManager.info("Click on" + btn.toString().split(":",2)[1] + " button");
        eActionsDriver.get().findElement(btn).click();
    }

    public void fillField(By field, String value){
        clearField(field);
        LoggingManager.info("Fill" + field.toString().split(":",2)[1] + " field with: " + value);
        eActionsDriver.get().findElement(field).sendKeys(value);
    }

    public void clearField(By field){
        LoggingManager.info("Clear" + field.toString().split(":",2)[1]);
        eActionsDriver.get().findElement(field).clear();
    }

    public boolean isDisplayed(By by){
        LoggingManager.info("Checking" + by.toString().split(":",2)[1] + " if Displayed");
        return eActionsDriver.get().findElement(by).isDisplayed();
    }

    public void selectItemByIndex(By by, int index)
    {
        LoggingManager.info("Select item no." + index + " from dropdown: " + by.toString().split(":",2)[1]);
        new Select(eActionsDriver.get().findElement(by)).selectByIndex(index);
    }

    public void selectItemByText(By by, String text)
    {
        LoggingManager.info("Select" + text + " from dropdown: " + by.toString().split(":",2)[1]);
        new Select(eActionsDriver.get().findElement(by)).selectByVisibleText(text);
    }

    public String getElementText(By by){
        String text =  eActionsDriver.get().findElement(by).getText();
        LoggingManager.info("Getting" + text + " from element: " + by.toString().split(":",2)[1]);
        return text;
    }

    public void waitForVisibility(By by){
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be visible");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForInvisibility(By by) {
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be invisible");
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public boolean waitForElementToBeClickable(By by)
    {
        waitForVisibility(by);
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be clickable");
        return eActionsDriver.get().findElement(by).isEnabled();
    }


    public List<WebElement> findElements(By by){
        LoggingManager.info("Finding List of Elements by:" + by.toString().split(":",2)[1]);
        return eActionsDriver.get().findElements(by);
    }

    public void removeDriver(){
        eActionsDriver.remove();
    }

}
