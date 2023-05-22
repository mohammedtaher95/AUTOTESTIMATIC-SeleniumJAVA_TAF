package browseractions;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    private static final ThreadLocal <WebDriver> driver = new ThreadLocal<>();
    private static JavascriptExecutor jSE;

    public BrowserActions(WebDriver driver){
        BrowserActions.driver.set(driver);
        jSE = (JavascriptExecutor) driver;
    }

    /******************************** URL Controlling and Navigation *************************************/

    public static String getCurrentURL(){
        return driver.get().getCurrentUrl();
    }

    public static void getToURL(String url){
        driver.get().get(url);
    }

    public static void navigateToURL(String url){
        driver.get().navigate().to(url);
    }

    public static String getPageTitle(){
        return driver.get().getTitle();
    }

    public static void refreshPage(){
        driver.get().navigate().refresh();
    }

    public static void scrollToBottom()
    {
        jSE.executeScript("scrollBy(0,2500)");
    }

    /******************************** Cookies *************************************/

    public static void deleteCookies() {
        driver.get().manage().deleteAllCookies();
    }

    /******************************** Window Control *************************************/

    public static Dimension getWindowSize(){
        return driver.get().manage().window().getSize();
    }

    public static void setWindowSize(int width, int height){
        driver.get().manage().window().setSize(new Dimension(width, height));
    }

    public static void maximizeWindow(){
        driver.get().manage().window().maximize();
    }

    public static void minimizeWindow(){
        driver.get().manage().window().minimize();
    }

    public static void setWindowToFullScreen(){
        driver.get().manage().window().fullscreen();
    }

    public static void switchToNewTab(){
        driver.get().switchTo().newWindow(WindowType.TAB);
    }

    public static void switchToNewWindow(){
        driver.get().switchTo().newWindow(WindowType.WINDOW);
    }

    public void removeDriver(){
        driver.remove();
    }

}
