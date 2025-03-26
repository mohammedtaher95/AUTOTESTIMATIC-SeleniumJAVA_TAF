package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.testng.annotations.*;
import pages.nopcommerce.SearchPage;
import utilities.JsonFileHandler;

public class SearchProductTest{

    public static ThreadLocal<WebDriver> driver;

    JsonFileHandler testData;
    String productName;

    @Test
    public void UserCanSearchForProducts() {
        productName = testData.getData("searchQueryList.FirstItem");
        new SearchPage(driver.get())
                .productSearch(productName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(productName);
    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());
        testData = new JsonFileHandler("simpleFile.json");

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }
}
