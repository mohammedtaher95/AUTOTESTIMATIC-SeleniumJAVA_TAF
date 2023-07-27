package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.testng.annotations.*;
import pages.nopcommerce.SearchPage;
import utilities.JSONFileHandler;

public class SearchProductTest{

    public static ThreadLocal<WebDriver> driver;

    JSONFileHandler testData;
    String ProductName;

    @Test
    public void UserCanSearchForProducts() {
        ProductName = testData.getData("searchQueryList.FirstItem");
        new SearchPage(driver.get())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());
        testData = new JSONFileHandler("simpleFile.json");

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteCookies();
        driver.get().quit();
        driver.remove();
    }
}
