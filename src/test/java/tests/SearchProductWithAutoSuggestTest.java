package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.SearchPage;

public class SearchProductWithAutoSuggestTest extends TestBase{

    String ProductName = "Apple MacBook Pro 13-inch";

    @Test
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(Webdriver.getDriver())
                    .productSearchUsingAutoSuggest("Mac")
                    .checkThatProductPageShouldBeDisplayed(ProductName);
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred " + e.getMessage());
        }

    }
}
