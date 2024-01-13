package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.*;
import org.testng.annotations.*;
import pages.nopcommerce.homepage.HomePage;
import tools.properties.Properties;
import tools.properties.WebCapabilities;
import utilities.UserFormData;


public class TestClass{

    public ThreadLocal<WebDriver> driver;
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
//        new WebCapabilities.SetProperty().targetBrowserName("edge");
//        Properties.timeouts.set().waitForLazyLoading(true);
//        Properties.web.set().targetBrowserName("edge");
//        Properties.executionOptions.set().crossBrowserMode("PARALLEL");
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());

    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }

}


