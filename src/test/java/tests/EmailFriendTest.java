package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import pages.homepage.HomePage;
import pages.registrationpage.UserRegistrationPage;
import utilities.UserFormData;

public class EmailFriendTest extends TestBase{

    HomePage homeObject;
    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Your message has been sent.";

    UserFormData newUser = new UserFormData();

    @Test(priority = 1, alwaysRun = true)
    public void UserCanRegisterSuccessfully(){
        homeObject = new HomePage(driver.getDriver());
        homeObject.openRegistrationPage();

        new UserRegistrationPage(driver.getDriver())
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();
    }

    @Test(priority = 2, alwaysRun = true)
    public void RegisteredUserCanLogin()
    {
        homeObject.openLoginPage();
        new LoginPage(driver.getDriver())
                .userLogin(newUser.getEmail(), newUser.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test(priority = 3, alwaysRun = true)
    public void UserCanSearchForProducts(){
        new SearchPage(driver.getDriver())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test(priority = 4, alwaysRun = true)
    public void RegisteredUserCanEmailHisFriend() {
        new ProductDetailsPage(driver.getDriver())
                .emailFriend()
                .fillEmailFriendForm(newUser.getFriendEmail(), newUser.getMessage())
                .clickOnSendButton()
                .checkThatSuccessMessageShouldBeDisplayed(SuccessMessage);
    }

    @Test(priority = 5, alwaysRun = true)
    public void RegisteredUserCanLogout()
    {
        new LoginPage(driver.getDriver())
                .clickOnLogoutButton();
    }
}
