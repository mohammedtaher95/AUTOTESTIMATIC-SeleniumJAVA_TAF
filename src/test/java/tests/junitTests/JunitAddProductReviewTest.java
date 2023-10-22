package tests.junitTests;

import driverfactory.webdriver.WebDriver;
import org.junit.jupiter.api.*;
import pages.nopcommerce.LoginPage;
import pages.nopcommerce.ProductDetailsPage;
import pages.nopcommerce.SearchPage;
import pages.nopcommerce.homepage.HomePage;
import utilities.UserFormData;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JunitAddProductReviewTest {

    public static ThreadLocal<WebDriver> driver;
    String ProductName = "Apple MacBook Pro 13-inch";
    String SuccessMessage = "Product review is successfully added.";

    UserFormData newUser = new UserFormData();


    @BeforeAll
    public static void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());
    }

    @Test
    @Order(1)
    public void UserCanRegisterSuccessfully()  {
        new HomePage(driver.get())
                .openRegistrationPage()
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();
    }

    @Test
    @Order(2)
    public void RegisteredUserCanLogin()
    {
        new HomePage(driver.get())
                .openLoginPage()
                .userLogin(newUser.getEmail(), newUser.getOldPassword())
                .clickOnLoginButton()
                .checkThatLogoutButtonShouldBeDisplayed();
    }

    @Test
    @Order(3)
    public void UserCanSearchForProducts(){
        new SearchPage(driver.get())
                .productSearch(ProductName)
                .openProductPage()
                .checkThatProductPageShouldBeDisplayed(ProductName);
    }

    @Test
    @Order(4)
    public void RegisteredUserCanAddReviewForProduct() {
        new ProductDetailsPage(driver.get())
                .addReview()
                .fillReviewForm(newUser.getMessage(), newUser.getMessage())
                .clickOnSubmitButton()
                .verifyThatReviewShouldBeSubmittedSuccessfully(SuccessMessage, newUser.getMessage());
    }

    @Test
    @Order(5)
    public void RegisteredUserCanLogout()
    {
        new LoginPage(driver.get()).clickOnLogoutButton();
    }

    @AfterAll
    public static void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }
}
