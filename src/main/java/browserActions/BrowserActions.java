package browserActions;

import driverFactory.Webdriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserActions {

    private static WebDriver driver;
    public static JavascriptExecutor JSE;
    public Select dropDown;
    public static WebDriverWait wait;

    public BrowserActions(){
        this.driver = Webdriver.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JSE = (JavascriptExecutor) driver;
    }

    /******************************** URL Controlling and Navigation *************************************/

    public static String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    public static void getToURL(String URL){
        driver.get(URL);
    }

    public static void navigateToURL(String URL){
        driver.navigate().to(URL);
    }

    public static String getPageTitle(){
        return driver.getTitle();
    }

    public static void refreshPage(){
        driver.navigate().refresh();
    }

    public static void scrollToBottom()
    {
        JSE.executeScript("scrollBy(0,2500)");
    }

    /******************************** Cookies *************************************/

    public static void deleteCookies() {
        driver.manage().deleteAllCookies();
    }

    /******************************** Window Control *************************************/

    public static Dimension getWindowSize(){
        return driver.manage().window().getSize();
    }

    public static void setWindowSize(int width, int height){
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public static void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public static void minimizeWindow(){
        driver.manage().window().minimize();
    }

    public static void setWindowToFullScreen(){
        driver.manage().window().fullscreen();
    }

    public static void switchToNewTab(){
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public static void switchToNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

}
