package tests;

import driverFactory.Webdriver;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import pages.homePage.HomePage;
import utilities.UserFormData;


public class TestClass{

    driverFactory.Webdriver driver;
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

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        //.setProperty("BASE_URL", "http://demo.nopcommerce.com");

        driver = new Webdriver();
        //System.out.println(Reporter.getCurrentTestResult().getTestClass().getXmlTest().getParameter("browserName"));
        //navigateToURL("http://demo.nopcommerce.com");
    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.quit();
    }

}
