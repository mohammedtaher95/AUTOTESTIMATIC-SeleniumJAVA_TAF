package tests;

import driverfactory.WebDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.SearchPage;
import pages.homepage.HomePage;
import utilities.UserFormData;

public class EmailFriendTest{

    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Your message has been sent.";
    public static ThreadLocal<driverfactory.WebDriver> driver;
    UserFormData user = new UserFormData();

    @BeforeClass(description = "Setup Driver")
    public synchronized void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());

    }

    @Test(priority = 1)
    public void UserCanRegisterSuccessfully()
    {
        new HomePage(driver.get())
                .openRegistrationPage()
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();
    }

    @Test(priority = 2, dependsOnMethods = {"UserCanRegisterSuccessfully"})
    public void RegisteredUserCanLogin()
    {
        new HomePage(driver.get())
                .openLoginPage()
                .userLogin(user.getEmail(), user.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test(priority = 3, alwaysRun = true, dependsOnMethods = {"RegisteredUserCanLogin"})
    public void UserCanSearchForProducts(){
        new SearchPage(driver.get())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test(priority = 4, alwaysRun = true, dependsOnMethods = {"UserCanSearchForProducts"})
    public void RegisteredUserCanEmailHisFriend() {
        new ProductDetailsPage(driver.get())
                .emailFriend()
                .fillEmailFriendForm(user.getFriendEmail(), user.getMessage())
                .clickOnSendButton()
                .checkThatSuccessMessageShouldBeDisplayed(SuccessMessage);
    }

    @Test(priority = 5, alwaysRun = true, dependsOnMethods = {"RegisteredUserCanEmailHisFriend"})
    public void RegisteredUserCanLogout()
    {
        new LoginPage(driver.get())
                .clickOnLogoutButton();
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteCookies();
        driver.get().quit();
        driver.remove();
    }
}
