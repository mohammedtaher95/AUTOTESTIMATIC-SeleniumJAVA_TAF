package elementactions;


import driverfactory.Webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementActions {
    private static WebDriver driver;
    private static FluentWait<WebDriver> driverWait;
    Actions action;

    public ElementActions(){
        driver = Webdriver.getDriver();
        driverWait = new FluentWait<>(driver);
        action = new Actions(driver);
    }

    public static void clickButton(By btn){
        driver.findElement(btn).click();
    }

    public static void fillField(By field, String value){

        WebElement txtField = driver.findElement(field);
        txtField.clear();
        txtField.sendKeys(value);
    }

    public static void clearField(By field){
        WebElement txtField = driver.findElement(field);
        txtField.clear();
    }

    public static boolean elementDisplayed(By by){
        return driver.findElement(by).isDisplayed();
    }

    public static void selectItemByIndex(By by, int index)
    {
        new Select(driver.findElement(by)).selectByIndex(index);
    }

    public static void selectItemByText(By by, String text)
    {
        new Select(driver.findElement(by)).selectByVisibleText(text);
    }

    public static String getElementText(By by){
        return driver.findElement(by).getText();
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
        return driver.findElement(by).isEnabled();
    }


    public static List<WebElement> findElements(By by){
        return driver.findElements(by);
    }
}
