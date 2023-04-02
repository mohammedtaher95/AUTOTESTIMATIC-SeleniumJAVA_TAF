package elementActions;


import driverFactory.Webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ElementActions {

    private static WebDriver driver;
    public JavascriptExecutor JSE;
    public Select dropDown;
    public static FluentWait wait;
    public Actions action;

    public ElementActions(){
        this.driver = Webdriver.getDriver();
        wait = new FluentWait(driver);
        action = new Actions(driver);
    }

    public static void clickButton(By Btn){
        driver.findElement(Btn).click();
    }

    public static void Fill_in(By field, String value){

        WebElement Field = driver.findElement(field);
        Field.clear();
        Field.sendKeys(value);
    }

    public static void clearField(By field){
        WebElement Field = driver.findElement(field);
        Field.clear();
    }

    public static boolean ElementDisplayed(By by){
        return driver.findElement(by).isDisplayed();
    }

    public static void SelectItemByIndex(By by, int index)
    {
        new Select(driver.findElement(by)).selectByIndex(index);
    }

    public static void SelectItemByText(By by, String text)
    {
        new Select(driver.findElement(by)).selectByVisibleText(text);
    }

    public static String getElementText(By by){
        return driver.findElement(by).getText();
    }

    public static void waitForVisibility(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitForInvisibility(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static boolean waitForElementToBeClickable(By by)
    {
        waitForVisibility(by);
        return driver.findElement(by).isEnabled();
    }


    public static List<WebElement> FindElements(By by){
        return driver.findElements(by);
    }
}
