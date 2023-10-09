package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.testng.annotations.*;
import pages.nopcommerce.homepage.HomePage;
import utilities.UserFormData;

public class ContactUsTest{

    public static ThreadLocal<WebDriver> driver;
    UserFormData newUser = new UserFormData();
    String successMessage = "Your enquiry has been successfully sent to the store owner.";

    @Test(threadPoolSize = 3)
    public void UserCanContactWebsiteOwner()
    {
        new HomePage(driver.get())
                .openContactUsPage()
                .fillContactInfoForm(newUser.getFullName(), newUser.getEmail(), newUser.getMessage())
                .clickOnSubmitButton()
                .successMessageShouldBeDisplayed(successMessage);
    }

    @BeforeClass(description = "Setup Driver")
    public synchronized void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }

}
