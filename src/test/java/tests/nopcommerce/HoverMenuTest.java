package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.nopcommerce.homepage.HomePage;

public class HoverMenuTest {

    public static ThreadLocal<WebDriver> driver;

    @Test
    public void UserCanHoverOnMenu() {
        HomePage home = new HomePage(driver.get());
        home.selectNotebookMenu();
        driver.get().assertThat().browser().url().contains("notebooks");
    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }
}
