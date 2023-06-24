package tests;

import driverfactory.WebDriver;
import org.testng.annotations.*;
import pages.SearchPage;
import utilities.JSONFileHandler;

public class SearchProductTest{

    public static ThreadLocal<driverfactory.WebDriver> driver;

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
