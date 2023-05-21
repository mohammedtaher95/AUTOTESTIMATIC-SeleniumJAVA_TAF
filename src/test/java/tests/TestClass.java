package tests;

import driverfactory.Webdriver;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.*;

import org.testng.annotations.Test;
import pages.homepage.HomePage;
import utilities.UserFormData;


public class TestClass{

    public static ThreadLocal<driverfactory.Webdriver> driver;
    UserFormData newUser;

    @Issue(" ")
    @TmsLink("Nop Commerce_1-User Registration")
    @Description("User can access registration page and register successfully")
    @Test(description = "User Register on website successfully")
    public void testMethod(){
        newUser = new UserFormData();

        new HomePage(Webdriver.getDriver())
                .openRegistrationPage()
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();

    }

    @BeforeMethod(description = "Setup Driver")
    public synchronized void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

    @AfterMethod(description = "Tear down")
    public void tearDown(){
        driver.get().quit();
        driver.remove();
    }

}
