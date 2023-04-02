package tests;

import driverFactory.Webdriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmailFriendPage;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import pages.homePage.HomePage;
import pages.registrationPage.UserRegistrationPage;
import utilities.UserFormData;

import static driverFactory.Webdriver.getDriver;

public class EmailFriendTest extends TestBase{

    HomePage homeObject;
    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Your message has been sent.";

    UserFormData newUser = new UserFormData();

    @Test(priority = 1, alwaysRun = true)
    public void UserCanRegisterSuccessfully(){
        homeObject = new HomePage(Webdriver.getDriver());
        homeObject.openRegistrationPage();

        new UserRegistrationPage(Webdriver.getDriver())
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();
    }

    @Test(priority = 2, alwaysRun = true)
    public void RegisteredUserCanLogin()
    {
        homeObject.openLoginPage();
        new LoginPage(getDriver())
                .userLogin(newUser.getEmail(), newUser.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test(priority = 3, alwaysRun = true)
    public void UserCanSearchForProducts(){
        new SearchPage(Webdriver.getDriver())
                .ProductSearch(ProductName)
                .OpenProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test(priority = 4, alwaysRun = true)
    public void RegisteredUserCanEmailHisFriend() {
        new ProductDetailsPage(Webdriver.getDriver())
                .EmailFriend()
                .FillEmailFriendForm(newUser.getFriendEmail(), newUser.getMessage())
                .clickOnSendButton()
                .checkThatSuccessMessageShouldBeDisplayed(SuccessMessage);
    }

    @Test(priority = 5, alwaysRun = true)
    public void RegisteredUserCanLogout()
    {
        new LoginPage(Webdriver.getDriver())
                .clickOnLogoutButton();
    }
}
