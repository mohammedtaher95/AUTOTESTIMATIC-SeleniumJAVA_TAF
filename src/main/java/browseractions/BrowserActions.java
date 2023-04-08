package browseractions;

import driverfactory.Webdriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    public static final JavascriptExecutor jSE = (JavascriptExecutor) Webdriver.getDriver();

    private BrowserActions(){

    }

    /******************************** URL Controlling and Navigation *************************************/

    public static String getCurrentURL(){
        return Webdriver.getDriver().getCurrentUrl();
    }

    public static void getToURL(String url){
        Webdriver.getDriver().get(url);
    }

    public static void navigateToURL(String url){
        Webdriver.getDriver().navigate().to(url);
    }

    public static String getPageTitle(){
        return Webdriver.getDriver().getTitle();
    }

    public static void refreshPage(){
        Webdriver.getDriver().navigate().refresh();
    }

    public static void scrollToBottom()
    {
        jSE.executeScript("scrollBy(0,2500)");
    }

    /******************************** Cookies *************************************/

    public static void deleteCookies() {
        Webdriver.getDriver().manage().deleteAllCookies();
    }

    /******************************** Window Control *************************************/

    public static Dimension getWindowSize(){
        return Webdriver.getDriver().manage().window().getSize();
    }

    public static void setWindowSize(int width, int height){
        Webdriver.getDriver().manage().window().setSize(new Dimension(width, height));
    }

    public static void maximizeWindow(){
        Webdriver.getDriver().manage().window().maximize();
    }

    public static void minimizeWindow(){
        Webdriver.getDriver().manage().window().minimize();
    }

    public static void setWindowToFullScreen(){
        Webdriver.getDriver().manage().window().fullscreen();
    }

    public static void switchToNewTab(){
        Webdriver.getDriver().switchTo().newWindow(WindowType.TAB);
    }

    public static void switchToNewWindow(){
        Webdriver.getDriver().switchTo().newWindow(WindowType.WINDOW);
    }

}
