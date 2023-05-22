package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;
import pages.homepage.HomePage;
import utilities.UserFormData;

public class ContactUsTest{

    public static ThreadLocal<driverfactory.Webdriver> driver;
    UserFormData newUser = new UserFormData();
    String successMessage = "Your enquiry has been successfully sent to the store owner.";

    @Test
    public void UserCanContactWebsiteOwner()
    {
        new HomePage(driver.get().makeAction())
                .openContactUsPage()
                .fillContactInfoForm(newUser.getFullName(), newUser.getEmail(), newUser.getMessage())
                .clickOnSubmitButton()
                .successMessageShouldBeDisplayed(successMessage);
    }

    @BeforeClass(description = "Setup Driver")
    public synchronized void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }

}
