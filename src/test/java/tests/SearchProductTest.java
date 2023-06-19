package tests;

import driverfactory.WebDriver;
import org.testng.annotations.*;
import pages.SearchPage;

public class SearchProductTest{

    public static ThreadLocal<driverfactory.WebDriver> driver;
    String ProductName = "Apple MacBook Pro 13-inch";

    @Test
    public void UserCanSearchForProducts() {
        new SearchPage(driver.get())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
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
