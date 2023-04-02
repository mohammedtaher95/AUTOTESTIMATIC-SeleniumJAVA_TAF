package tests;

import driverFactory.Webdriver;
import org.testng.annotations.Test;
import pages.SearchPage;
import pages.homePage.HomePage;

public class ChangeCurrencyTest extends TestBase{

    HomePage homeObject;

    @Test(priority = 1, alwaysRun = true)
    public void UserCanChangeCurrency()
    {
        homeObject = new HomePage(Webdriver.getDriver());
        homeObject.changeCurrency(1);
    }

    @Test(priority = 2, alwaysRun = true)
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(Webdriver.getDriver())
                    .ProductSearch("Mac")
                    .OpenProductPage()
                    .checkCurrency("€");
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred " + e.getMessage());
        }

    }
}
