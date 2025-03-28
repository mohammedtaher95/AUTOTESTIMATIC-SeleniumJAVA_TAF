package browseractions;

import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import utilities.LoggingManager;

public class BrowserActions {

    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    final JavascriptExecutor javascriptExecutor;
    private final Actions actions;

    public BrowserActions(WebDriver driver) {
        driverThreadLocal.set(driver);
        actions = new Actions(driverThreadLocal.get());
        javascriptExecutor = (JavascriptExecutor) driverThreadLocal.get();
    }

    /***************************** URL Controlling and Navigation ********************************/

    public String getCurrentUrl() {
        return driverThreadLocal.get().getCurrentUrl();
    }

    public BrowserActions getToUrl(String url) {
        driverThreadLocal.get().get(url);
        return this;
    }

    public BrowserActions navigateToUrl(String url) {
        driverThreadLocal.get().navigate().to(url);
        return this;
    }

    public BrowserActions navigateForward() {
        driverThreadLocal.get().navigate().forward();
        return this;
    }

    public BrowserActions navigateBack() {
        driverThreadLocal.get().navigate().back();
        return this;
    }

    public String getPageTitle() {
        return driverThreadLocal.get().getTitle();
    }

    public BrowserActions refreshPage() {
        driverThreadLocal.get().navigate().refresh();
        return this;
    }

    public BrowserActions scrollToBottom() {
        LoggingManager.info("Scrolling to Page Bottom");
        actions.scrollByAmount(0, 2500);
        return this;
    }

    public BrowserActions scrolltoAmount(int width, int height) {
        LoggingManager.info("Scrolling by X: " + width + " and Y: " + height);
        actions.scrollByAmount(width, height);
        return this;
    }

    public String getPageSource() {
        return driverThreadLocal.get().getPageSource();
    }

    /******************************** Cookies *************************************/
    public BrowserActions addCookie(Cookie cookie) {
        driverThreadLocal.get().manage().addCookie(cookie);
        return this;
    }


    public BrowserActions deleteCookieWithName(String name) {
        driverThreadLocal.get().manage().deleteCookieNamed(name);
        return this;
    }


    public BrowserActions deleteAllCookies() {
        driverThreadLocal.get().manage().deleteAllCookies();
        return this;
    }

    public BrowserActions deleteCookie(Cookie cookie) {
        driverThreadLocal.get().manage().deleteCookie(cookie);
        return this;
    }

    /***************************** Window Control *******************************/

    public Dimension getWindowSize() {
        LoggingManager.info("Getting Window Size");
        return driverThreadLocal.get().manage().window().getSize();
    }

    public void setWindowSize(int width, int height) {
        LoggingManager.info("Setting Window size to: " + width + "x" + height);
        driverThreadLocal.get().manage().window().setSize(new Dimension(width, height));
    }

    public void maximizeWindow() {
        LoggingManager.info("Maximizing Window");
        driverThreadLocal.get().manage().window().maximize();
    }

    public void minimizeWindow() {
        LoggingManager.info("Minimizing Window");
        driverThreadLocal.get().manage().window().minimize();
    }

    public void setWindowToFullScreen() {
        LoggingManager.info("Setting Window to Full Screen");
        driverThreadLocal.get().manage().window().fullscreen();
    }

    public void switchToNewTab() {
        LoggingManager.info("Switching to a new tab");
        driverThreadLocal.get().switchTo().newWindow(WindowType.TAB);
    }

    public void switchToNewWindow() {
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


    public void close() {
        LoggingManager.info("Closing Window...");
        driverThreadLocal.get().close();
    }

    public void closeAllWindows() {
        driverThreadLocal.get().quit();
    }

    @SuppressWarnings("all")
    public void removeDriver() {
        driverThreadLocal.remove();
    }

}
