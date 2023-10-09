package elementactions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import utilities.LoggingManager;
import java.util.List;
import java.util.NoSuchElementException;


public class ElementActions {
    private final ThreadLocal<WebDriver> eActionsDriver = new ThreadLocal<>();
    private final FluentWait<WebDriver> driverWait;

    final JavascriptExecutor jSE;
    Actions action;

    public ElementActions(WebDriver driver, FluentWait<WebDriver> wait){
        eActionsDriver.set(driver);
        this.driverWait = wait;
        action = new Actions(eActionsDriver.get());
        jSE = (JavascriptExecutor) eActionsDriver.get();
    }

    public ElementActions click(By btn){
        try{
            eActionsDriver.get().findElement(btn).click();
        }
        catch (ElementClickInterceptedException | NoSuchElementException |
                StaleElementReferenceException | TimeoutException exception) {
            scrollToElement(btn);
            clickUsingJavaScript(btn);
        }

        return this;
    }

    public ElementActions hoverOnItem(By item){
        LoggingManager.info("hover on" + item.toString().split(":",2)[1] + " button");
        action.moveToElement(eActionsDriver.get().findElement(item)).click().build().perform();
        return this;
    }

    public ElementActions clickUsingJavaScript(By btn){
        LoggingManager.info("Click on" + btn.toString().split(":",2)[1] + " button using JavaScript");
        jSE.executeScript("arguments[0].click();", eActionsDriver.get().findElement(btn));
        return this;
    }

    public ElementActions scrollToElement(By element) {
        LoggingManager.info("Scrolling to " + element.toString().split(":",2)[1]);
        jSE.executeScript("arguments[0].scrollIntoView(true);", eActionsDriver.get().findElement(element));
        return this;
    }

    public ElementActions fillField(By field, String value){
        clearField(field);
        eActionsDriver.get().findElement(field).sendKeys(value);
        return this;
    }

    public ElementActions clearField(By field){
        eActionsDriver.get().findElement(field).clear();
        return this;
    }

    public boolean isDisplayed(By by){
        LoggingManager.info("Checking" + by.toString().split(":",2)[1] + " if Displayed");
        return eActionsDriver.get().findElement(by).isDisplayed();
    }

    public boolean isClickable(By by){
        LoggingManager.info("Checking" + by.toString().split(":",2)[1] + " if Clickable");
        return eActionsDriver.get().findElement(by).isEnabled();
    }

    public boolean isSelected(By by){
        LoggingManager.info("Checking" + by.toString().split(":",2)[1] + " if Selected");
        return eActionsDriver.get().findElement(by).isSelected();
    }

    public ElementActions selectItemByIndex(By by, int index)
    {
        LoggingManager.info("Select item no." + index + " from dropdown: " + by.toString().split(":",2)[1]);
        new Select(eActionsDriver.get().findElement(by)).selectByIndex(index);
        return this;
    }

    public ElementActions selectItemByText(By by, String text)
    {
        LoggingManager.info("Select" + text + " from dropdown: " + by.toString().split(":",2)[1]);
        waitForElementToBeClickable(by);
        new Select(eActionsDriver.get().findElement(by)).selectByVisibleText(text);
        return this;
    }

    public String getText(By by){
        waitForVisibility(by);
        String text =  eActionsDriver.get().findElement(by).getText();
        LoggingManager.info("Getting" + text + " from element: " + by.toString().split(":",2)[1]);
        return text;
    }

    public ElementActions acceptAlert(){
        Alert alert = eActionsDriver.get().switchTo().alert();
        alert.accept();
        LoggingManager.info("Alert Accepted");
        return this;
    }

    public ElementActions dismissAlert(){
        Alert alert = eActionsDriver.get().switchTo().alert();
        alert.dismiss();
        LoggingManager.info("Alert Dismissed");
        return this;
    }

    public String getAlertText(){
        Alert alert = eActionsDriver.get().switchTo().alert();
        LoggingManager.info("Getting Alert Text");
        return alert.getText();
    }

    public ElementActions addTextForAlert(String text){
        Alert alert = eActionsDriver.get().switchTo().alert();
        alert.sendKeys(text);
        return this;
    }

    public ElementActions waitForVisibility(By by){
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be visible");
        try{
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        catch (TimeoutException exception) {
            throw new org.openqa.selenium.NoSuchElementException("Element doesn't exist");
        }
        return this;
    }

    public ElementActions waitForInvisibility(By by) {
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be invisible");
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        return this;
    }

    public ElementActions waitForElementToBeClickable(By by)
    {
        LoggingManager.info("Wait for" + by.toString().split(":",2)[1] + " to be clickable");
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch (ElementNotInteractableException exception){
            LoggingManager.error("Element isn't interactable");
            throw exception;
        }
        return this;
    }


    public List<WebElement> findElements(By by){
        LoggingManager.info("Finding List of Elements by:" + by.toString().split(":",2)[1]);
        return eActionsDriver.get().findElements(by);
    }

    public void removeDriver(){
        eActionsDriver.remove();
    }

}
