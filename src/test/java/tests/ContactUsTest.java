package tests;

import com.github.javafaker.Faker;
import driverFactory.Webdriver;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.homePage.HomePage;

public class ContactUsTest extends TestBase{

    ContactUsPage ContactObject;
    Faker faker = new Faker();

    String FullName = faker.name().fullName();
    String Email = faker.internet().emailAddress();
    String Message = faker.address().fullAddress();

    String SuccessMessage = "Your enquiry has been successfully sent to the store owner.";

    @Test
    public void UserCanContactWebsiteOwner()
    {
        new HomePage(Webdriver.getDriver())
                .openContactUsPage()
                .FillContactInfoForm(FullName, Email, Message)
                .clickOnSubmitButton()
                .successMessageShouldBeDisplayed(Message);
    }


}
