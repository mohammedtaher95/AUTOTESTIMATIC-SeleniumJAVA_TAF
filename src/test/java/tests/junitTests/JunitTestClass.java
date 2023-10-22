package tests.junitTests;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.nopcommerce.homepage.HomePage;

import utilities.UserFormData;

public class JunitTestClass {

    public static ThreadLocal<WebDriver> driver;
    UserFormData newUser;

    @Issue(" ")
    @TmsLink("Nop Commerce_1-User Registration")
    @Description("User can access registration page and register successfully")
    @Test
    public void testMethod(){
        newUser = new UserFormData();

        new HomePage(driver.get())
                .openRegistrationPage()
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();

    }

    @BeforeAll
    public static void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());
    }

    @AfterAll
    public static void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }

}


