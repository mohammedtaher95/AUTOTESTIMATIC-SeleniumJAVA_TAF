package tests;

import com.github.javafaker.Faker;
import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.homepage.HomePage;

public class ContactUsTest extends TestBase{

    Faker faker = new Faker();
    String FullName = faker.name().fullName();
    String Email = faker.internet().emailAddress();
    String Message = faker.address().fullAddress();
    String successMessage = "Your enquiry has been successfully sent to the store owner.";

    @Test
    public void UserCanContactWebsiteOwner()
    {
        new HomePage(Webdriver.getDriver())
                .openContactUsPage()
                .fillContactInfoForm(FullName, Email, Message)
                .clickOnSubmitButton()
                .successMessageShouldBeDisplayed(successMessage);
    }


}
