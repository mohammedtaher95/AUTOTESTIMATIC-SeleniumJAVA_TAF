package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;
import pages.SearchPage;
import pages.homepage.HomePage;

public class ChangeCurrencyTest{

    public static ThreadLocal<driverfactory.Webdriver> driver;
    HomePage homeObject;

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

    @Test(priority = 1, alwaysRun = true)
    public void UserCanChangeCurrency()
    {
        homeObject = new HomePage(driver.get().makeAction());
        homeObject.changeCurrency(1);
    }

    @Test(priority = 2, alwaysRun = true)
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(driver.get().makeAction())
                    .productSearch("Mac")
                    .openProductPage()
                    .checkCurrency("€");
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred " + e.getMessage());
        }

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }
}
