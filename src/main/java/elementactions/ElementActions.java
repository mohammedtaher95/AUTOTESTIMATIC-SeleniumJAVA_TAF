package elementactions;

import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import utilities.LoggingManager;


public class ElementActions {
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final FluentWait<WebDriver> driverWait;

    final JavascriptExecutor javascriptExecutor;
    Actions action;

    public ElementActions(WebDriver driver, FluentWait<WebDriver> wait) {
        driverThreadLocal.set(driver);
        this.driverWait = wait;
        action = new Actions(driverThreadLocal.get());
        javascriptExecutor = (JavascriptExecutor) driverThreadLocal.get();
    }

    public ElementActions click(By btn) {
        try {
            driverThreadLocal.get().findElement(btn).click();
        } catch (ElementClickInterceptedException | NoSuchElementException
                 | StaleElementReferenceException | TimeoutException exception) {
            scrollToElement(btn);
            clickUsingJavaScript(btn);
        }

        return this;
    }

    public ElementActions hoverOnItem(By item) {
        LoggingManager.info("hover on" + item.toString().split(":", 2)[1] + " button");
        action.moveToElement(driverThreadLocal.get().findElement(item)).click().build().perform();
        return this;
    }

    public ElementActions clickUsingJavaScript(By btn) {
        LoggingManager.info("Click on" + btn.toString().split(":", 2)[1]
                + " button using JavaScript");
        javascriptExecutor.executeScript("arguments[0].click();",
                driverThreadLocal.get().findElement(btn));
        return this;
    }

    public ElementActions scrollToElement(By element) {
        LoggingManager.info("Scrolling to " + element.toString().split(":", 2)[1]);
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);",
                driverThreadLocal.get().findElement(element));
        return this;
    }

    public ElementActions fillField(By field, String value) {
        clearField(field);
        driverThreadLocal.get().findElement(field).sendKeys(value);
        return this;
    }

    public ElementActions clearField(By field) {
        driverThreadLocal.get().findElement(field).clear();
        return this;
    }

    public boolean isDisplayed(By by) {
        LoggingManager.info("Checking" + by.toString().split(":", 2)[1] + " if Displayed");
        return driverThreadLocal.get().findElement(by).isDisplayed();
    }

    public boolean isClickable(By by) {
        LoggingManager.info("Checking" + by.toString().split(":", 2)[1] + " if Clickable");
        return driverThreadLocal.get().findElement(by).isEnabled();
    }

    public boolean isSelected(By by) {
        LoggingManager.info("Checking" + by.toString().split(":", 2)[1] + " if Selected");
        return driverThreadLocal.get().findElement(by).isSelected();
    }

    public ElementActions selectItemByIndex(By by, int index) {
        LoggingManager.info("Select item no." + index + " from dropdown: "
                + by.toString().split(":", 2)[1]);
        new Select(driverThreadLocal.get().findElement(by)).selectByIndex(index);
        return this;
    }

    public ElementActions selectItemByText(By by, String text) {
        LoggingManager.info("Select" + text + " from dropdown: " + by.toString().split(":", 2)[1]);
        waitForElementToBeClickable(by);
        new Select(driverThreadLocal.get().findElement(by)).selectByVisibleText(text);
        return this;
    }

    public String getText(By by) {
        waitForVisibility(by);
        String text =  driverThreadLocal.get().findElement(by).getText();
        LoggingManager.info("Getting" + text + " from element: " + by.toString().split(":", 2)[1]);
        return text;
    }

    public ElementActions acceptAlert() {
        Alert alert = driverThreadLocal.get().switchTo().alert();
        alert.accept();
        LoggingManager.info("Alert Accepted");
        return this;
    }

    public ElementActions dismissAlert() {
        Alert alert = driverThreadLocal.get().switchTo().alert();
        alert.dismiss();
        LoggingManager.info("Alert Dismissed");
        return this;
    }

    public String getAlertText() {
        Alert alert = driverThreadLocal.get().switchTo().alert();
        LoggingManager.info("Getting Alert Text");
        return alert.getText();
    }

    public ElementActions addTextForAlert(String text) {
        Alert alert = driverThreadLocal.get().switchTo().alert();
        alert.sendKeys(text);
        return this;
    }

    public ElementActions waitForVisibility(By by) {
        LoggingManager.info("Wait for" + by.toString().split(":", 2)[1] + " to be visible");
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException exception) {
            throw new org.openqa.selenium.NoSuchElementException("Element doesn't exist");
        }
        return this;
    }

    public ElementActions waitForInvisibility(By by) {
        LoggingManager.info("Wait for" + by.toString().split(":", 2)[1] + " to be invisible");
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        return this;
    }

    public ElementActions waitForElementToBeClickable(By by) {
        LoggingManager.info("Wait for" + by.toString().split(":", 2)[1] + " to be clickable");
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (ElementNotInteractableException exception) {
            LoggingManager.error("Element isn't interactable");
            throw exception;
        }
        return this;
    }


    public List<WebElement> findElements(By by) {
        LoggingManager.info("Finding List of Elements by:" + by.toString().split(":", 2)[1]);
        return driverThreadLocal.get().findElements(by);
    }

    public void removeDriver() {
        driverThreadLocal.remove();
    }

}
