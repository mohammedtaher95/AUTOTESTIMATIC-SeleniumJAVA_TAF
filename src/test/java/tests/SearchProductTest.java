package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;
import pages.SearchPage;

public class SearchProductTest{

    public static ThreadLocal<driverfactory.Webdriver> driver;
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
        driver.set(new Webdriver());
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }
}
