package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.SearchPage;
import pages.homepage.HomePage;

public class ChangeCurrencyTest extends TestBase{

    HomePage homeObject;

    @Test(priority = 1, alwaysRun = true)
    public void UserCanChangeCurrency()
    {
        homeObject = new HomePage(driver.getDriver());
        homeObject.changeCurrency(1);
    }

    @Test(priority = 2, alwaysRun = true)
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(driver.getDriver())
                    .productSearch("Mac")
                    .openProductPage()
                    .checkCurrency("€");
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred " + e.getMessage());
        }

    }
}
