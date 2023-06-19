package browseractions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import utilities.LoggingManager;
import java.util.Set;

public class BrowserActions {

    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    final JavascriptExecutor jSE;
    private final Actions actions;

    public BrowserActions(WebDriver driver){
        driverThreadLocal.set(driver);
        actions = new Actions(driverThreadLocal.get());
        jSE = (JavascriptExecutor) driverThreadLocal.get();
    }

    /******************************** URL Controlling and Navigation *************************************/

    public String getCurrentURL(){
        String url = driverThreadLocal.get().getCurrentUrl();
        LoggingManager.info("Getting Current URL: " + url);
        return url;
    }

    public void getToURL(String url){
        LoggingManager.info("Getting to URL: " + url);
        driverThreadLocal.get().get(url);
    }

    public void navigateToURL(String url){
        LoggingManager.info("Navigating to URL: " + url);
        driverThreadLocal.get().navigate().to(url);
    }

    public void navigateForward(){
        LoggingManager.info("Navigating Forward");
        driverThreadLocal.get().navigate().forward();
    }

    public void navigateBack(){
        LoggingManager.info("Navigating Back");
        driverThreadLocal.get().navigate().back();
    }

    public String getPageTitle(){
        String title = driverThreadLocal.get().getTitle();
        LoggingManager.info("Getting Page Title: " + title);
        return title;
    }

    public void refreshPage(){
        LoggingManager.info("Refreshing Page...");
        driverThreadLocal.get().navigate().refresh();
    }

    public void scrollToBottom()
    {
        LoggingManager.info("Scrolling to Page Bottom");
        actions.scrollByAmount(0,2500);
    }

    public void scrolltoAmount(int width, int height)
    {
        LoggingManager.info("Scrolling by X: " + width + " and Y: " + height);
        actions.scrollByAmount(width,height);
    }

    public String getPageSource() {
        String source = driverThreadLocal.get().getPageSource();
        LoggingManager.info("Getting Page source: " + source);
        return source;
    }

    /******************************** Cookies *************************************/

    public void deleteCookies() {
        LoggingManager.info("Deleting All Cookies.....");
        driverThreadLocal.get().manage().deleteAllCookies();
    }

    public void deleteCookie(Cookie cookie){
        LoggingManager.info("Deleting Cookie: " + cookie + " ......");
        driverThreadLocal.get().manage().deleteCookie(cookie);
    }

    /******************************** Window Control *************************************/

    public Dimension getWindowSize(){
        LoggingManager.info("Getting Window Size");
        return driverThreadLocal.get().manage().window().getSize();
    }

    public void setWindowSize(int width, int height){
        LoggingManager.info("Setting Window size to: " + width + "x" + height);
        driverThreadLocal.get().manage().window().setSize(new Dimension(width, height));
    }

    public void maximizeWindow(){
        LoggingManager.info("Maximizing Window");
        driverThreadLocal.get().manage().window().maximize();
    }

    public void minimizeWindow(){
        LoggingManager.info("Minimizing Window");
        driverThreadLocal.get().manage().window().minimize();
    }

    public void setWindowToFullScreen(){
        LoggingManager.info("Setting Window to Full Screen");
        driverThreadLocal.get().manage().window().fullscreen();
    }

    public void switchToNewTab(){
        LoggingManager.info("Switching to a new tab");
        driverThreadLocal.get().switchTo().newWindow(WindowType.TAB);
    }

    public void switchToNewWindow(){
        LoggingManager.info("Switching to a new window");
        driverThreadLocal.get().switchTo().newWindow(WindowType.WINDOW);
    }

    public Set<String> getWindowHandles() {
        LoggingManager.info("Getting Window Handles");
        return driverThreadLocal.get().getWindowHandles();
    }

    public String getWindowHandle() {
        String windowHandle = driverThreadLocal.get().getWindowHandle();
        LoggingManager.info("Getting Window Handle: " + windowHandle);
        return windowHandle;
    }


    public void close(){
        LoggingManager.info("Closing Window...");
        driverThreadLocal.get().close();
    }

    public void closeAllWindows(){
        driverThreadLocal.get().quit();
    }

    public void removeDriver(){
        driverThreadLocal.remove();
    }

}
