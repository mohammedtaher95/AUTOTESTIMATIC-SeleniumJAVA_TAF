package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;
import pages.SearchPage;

public class SearchProductWithAutoSuggestTest{

    public static ThreadLocal<driverfactory.Webdriver> driver;
    String ProductName = "Apple MacBook Pro 13-inch";

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }


    @Test
    public void UserCanSearchForProductWithAutoSuggest()
    {
        try {
            new SearchPage(driver.get().makeAction())
                    .productSearchUsingAutoSuggest("Mac")
                    .checkThatProductPageShouldBeDisplayed(ProductName);
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
