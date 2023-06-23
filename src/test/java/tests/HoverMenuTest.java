package tests;

import driverfactory.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.homepage.HomePage;

public class HoverMenuTest {

    public static ThreadLocal<driverfactory.WebDriver> driver;

    @Test
    public void UserCanHoverOnMenu() {
        HomePage home = new HomePage(driver.get());
        home.SelectNotebookMenu();
        driver.get().assertThat().browser().url().contains("notebooks");
    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteCookies();
        driver.get().quit();
        driver.remove();
    }
}
