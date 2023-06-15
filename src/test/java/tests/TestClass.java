package tests;

import driverfactory.Webdriver;
import io.qameta.allure.*;
import org.testng.annotations.*;
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

        new HomePage(driver.get())
                .openRegistrationPage()
                .validateThatUserNavigatedToRegistrationPage()
                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
                .clickOnRegisterButton()
                .validateThatSuccessMessageShouldBeDisplayed();

    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        driver = new ThreadLocal<>();
        driver.set(new Webdriver());
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteCookies();
        driver.get().quit();
        driver.remove();
    }

}


