package tests;

import driverFactory.Webdriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductDetailsPage;
import pages.SearchPage;

public class SearchProductTest extends TestBase{

    String ProductName = "Apple MacBook Pro 13-inch";
    SearchPage SearchObject;
    ProductDetailsPage ProductObject;

    @Test
    public void UserCanSearchForProducts() {
        new SearchPage(Webdriver.getDriver())
                .ProductSearch(ProductName)
                .OpenProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }
}
