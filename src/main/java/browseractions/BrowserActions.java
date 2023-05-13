package browseractions;

import driverfactory.Webdriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    static WebDriver driver = Webdriver.getDriver();
    public static final JavascriptExecutor jSE = (JavascriptExecutor) driver;

    private BrowserActions(){

    }

    /******************************** URL Controlling and Navigation *************************************/

    public static String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    public static void getToURL(String url){
        driver.get(url);
    }

    public static void navigateToURL(String url){
        driver.navigate().to(url);
    }

    public static String getPageTitle(){
        return driver.getTitle();
    }

    public static void refreshPage(){
        driver.navigate().refresh();
    }

    public static void scrollToBottom()
    {
        jSE.executeScript("scrollBy(0,2500)");
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
