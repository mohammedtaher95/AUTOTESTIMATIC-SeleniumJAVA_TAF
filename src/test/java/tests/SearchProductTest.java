package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;
import pages.SearchPage;

public class SearchProductTest{

    public static ThreadLocal<driverfactory.Webdriver> driver;
    String ProductName = "Apple MacBook Pro 13-inch";

    @Test
    public void UserCanSearchForProducts() {
        new SearchPage(driver.get().makeAction())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @BeforeMethod(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

    @AfterMethod(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }
}
