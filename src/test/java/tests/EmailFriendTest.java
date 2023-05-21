package tests;

import driverfactory.Webdriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import pages.homepage.HomePage;
import pages.registrationpage.UserRegistrationPage;
import utilities.UserFormData;

public class EmailFriendTest{

    HomePage homeObject;
    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Your message has been sent.";
    public static ThreadLocal<driverfactory.Webdriver> driver;
    UserFormData newUser = new UserFormData();

    @BeforeClass(description = "Setup Driver")
    public synchronized void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

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

    @Test(priority = 2, alwaysRun = true, dependsOnMethods = {"UserCanRegisterSuccessfully"})
    public void RegisteredUserCanLogin()
    {
        homeObject.openLoginPage();
        new LoginPage(Webdriver.getDriver())
                .userLogin(newUser.getEmail(), newUser.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test(priority = 3, alwaysRun = true, dependsOnMethods = {"RegisteredUserCanLogin"})
    public void UserCanSearchForProducts(){
        new SearchPage(Webdriver.getDriver())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test(priority = 4, alwaysRun = true, dependsOnMethods = {"UserCanSearchForProducts"})
    public void RegisteredUserCanEmailHisFriend() {
        new ProductDetailsPage(Webdriver.getDriver())
                .emailFriend()
                .fillEmailFriendForm(newUser.getFriendEmail(), newUser.getMessage())
                .clickOnSendButton()
                .checkThatSuccessMessageShouldBeDisplayed(SuccessMessage);
    }

    @Test(priority = 5, alwaysRun = true, dependsOnMethods = {"RegisteredUserCanEmailHisFriend"})
    public void RegisteredUserCanLogout()
    {
        new LoginPage(Webdriver.getDriver())
                .clickOnLogoutButton();
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }
}
