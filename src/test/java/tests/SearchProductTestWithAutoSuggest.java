package tests;

import driverFactory.Webdriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductDetailsPage;
import pages.SearchPage;

public class SearchProductTestWithAutoSuggest extends TestBase{

    String ProductName = "Apple MacBook Pro 13-inch";
    SearchPage SearchObject;
    ProductDetailsPage ProductObject;

    @Test
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(Webdriver.getDriver())
                    .ProductSearchUsingAutoSuggest("Mac")
                    .checkThatProductPageShouldBeDisplayed(ProductName);
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred " + e.getMessage());
        }

    }
}
