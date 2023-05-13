package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.ProductDetailsPage;
import pages.SearchPage;

public class SearchProductTest extends TestBase{

    String ProductName = "Apple MacBook Pro 13-inch";
    SearchPage SearchObject;
    ProductDetailsPage ProductObject;

    @Test
    public void UserCanSearchForProducts() {
        new SearchPage(driver.getDriver())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }
}
