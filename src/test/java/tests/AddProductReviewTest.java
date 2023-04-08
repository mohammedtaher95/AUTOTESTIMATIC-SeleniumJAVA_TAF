package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import pages.homepage.HomePage;
import pages.registrationpage.UserRegistrationPage;
import utilities.UserFormData;

import static driverfactory.Webdriver.getDriver;

public class AddProductReviewTest extends TestBase{

    HomePage home;
    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Product review is successfully added.";

    UserFormData newUser = new UserFormData();

    @Test(priority = 1, alwaysRun = true)
    public void UserCanRegisterSuccessfully()  {
        home = new HomePage(Webdriver.getDriver());
        home.openRegistrationPage();

        new UserRegistrationPage(Webdriver.getDriver())
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();
    }

    @Test(priority = 2, alwaysRun = true)
    public void RegisteredUserCanLogin()
    {
        home.openLoginPage();
        new LoginPage(getDriver())
                .userLogin(newUser.getEmail(), newUser.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test(priority = 3, alwaysRun = true)
    public void UserCanSearchForProducts(){
        new SearchPage(Webdriver.getDriver())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test(priority = 4, alwaysRun = true)
    public void RegisteredUserCanAddReviewForProduct() {
        new ProductDetailsPage(Webdriver.getDriver())
                .addReview()
                .fillReviewForm(newUser.getMessage(), newUser.getMessage())
                .clickOnSubmitButton()
                .verifyThatReviewShouldBeSubmittedSuccessfully(SuccessMessage, newUser.getMessage());
    }

    @Test(priority = 5, alwaysRun = true)
    public void RegisteredUserCanLogout()
    {
        new LoginPage(Webdriver.getDriver())
                .clickOnLogoutButton();
    }
}
