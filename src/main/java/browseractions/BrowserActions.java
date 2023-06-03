package browseractions;

import org.openqa.selenium.*;

import java.util.Set;

public class BrowserActions {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static JavascriptExecutor jSE;

    public BrowserActions(WebDriver driver){
        driverThreadLocal.set(driver);
        jSE = (JavascriptExecutor) driver;
    }

    /******************************** URL Controlling and Navigation *************************************/

    public String getCurrentURL(){
        return driverThreadLocal.get().getCurrentUrl();
    }

    public void getToURL(String url){
        driverThreadLocal.get().get(url);
    }

    public void navigateToURL(String url){
        driverThreadLocal.get().navigate().to(url);
    }

    public void navigateForward(){
        driverThreadLocal.get().navigate().forward();
    }

    public void navigateBack(){
        driverThreadLocal.get().navigate().back();
    }

    public String getPageTitle(){
        return driverThreadLocal.get().getTitle();
    }

    public void refreshPage(){
        driverThreadLocal.get().navigate().refresh();
    }

    public void scrollToBottom()
    {
        jSE.executeScript("scrollBy(0,2500)");
    }

    public String getPageSource() {
        return driverThreadLocal.get().getPageSource();
    }

    /******************************** Cookies *************************************/

    public void deleteCookies() {
        driverThreadLocal.get().manage().deleteAllCookies();
    }

    public void deleteCookie(Cookie cookie){
        driverThreadLocal.get().manage().deleteCookie(cookie);
    }

    /******************************** Window Control *************************************/

    public Dimension getWindowSize(){
        return driverThreadLocal.get().manage().window().getSize();
    }

    public void setWindowSize(int width, int height){
        driverThreadLocal.get().manage().window().setSize(new Dimension(width, height));
    }

    public void maximizeWindow(){
        driverThreadLocal.get().manage().window().maximize();
    }

    public void minimizeWindow(){
        driverThreadLocal.get().manage().window().minimize();
    }

    public void setWindowToFullScreen(){
        driverThreadLocal.get().manage().window().fullscreen();
    }

    public void switchToNewTab(){
        driverThreadLocal.get().switchTo().newWindow(WindowType.TAB);
    }

    public void switchToNewWindow(){
        driverThreadLocal.get().switchTo().newWindow(WindowType.WINDOW);
    }

    public Set<String> getWindowHandles() {
        return driverThreadLocal.get().getWindowHandles();
    }

    public String getWindowHandle() {
        return driverThreadLocal.get().getWindowHandle();
    }


    public void close(){
        driverThreadLocal.get().close();
    }

    public void closeAllWindows(){
        driverThreadLocal.get().quit();
    }

    public void removeDriver(){
        driverThreadLocal.remove();
    }

}
