package tests.nopcommerce;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.*;
import java.time.Duration;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import pages.nopcommerce.homepage.HomePage;
import utilities.UserFormData;



public class TestClass{

    public ThreadLocal<WebDriver> driver;
    UserFormData newUser;

    @Issue(" ")
    @TmsLink("Nop Commerce_1-User Registration")
    @Description("User can access registration page and register successfully")
    @Test(description = "User Register on website successfully")
    public void testMethod() {
//        newUser = new UserFormData();
//
//        new HomePage(driver.get())
//                .openRegistrationPage()
//                .validateThatUserNavigatedToRegistrationPage()
//                .fillUserRegistrationForm(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getOldPassword())
//                .clickOnRegisterButton()
//                .validateThatSuccessMessageShouldBeDisplayed();
        driver.get().element().fillField(By.id("user-name"), "standard_user")
              .fillField(By.id("password"), "secret_sauce")
              .click(By.id("login-button"))
              .click(By.cssSelector("div.bm-burger-button"));
        driver.get().getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get().assertThat().element(By.cssSelector("div.bm-burger-button")).isDisplayed();

    }

    @BeforeClass(description = "Setup Driver")
    public void setUp()  {
//        new WebCapabilities.SetProperty().targetBrowserName("edge");
//        Properties.timeouts.set().waitForLazyLoading(true);
//        Properties.web.set().targetBrowserName("edge");
//        Properties.executionOptions.set().crossBrowserMode("PARALLEL");
//        Properties.web.set().baseURL("http://www.amazon.com");
        driver = new ThreadLocal<>();
        driver.set(new WebDriver());

        driver.get().browser().navigateToUrl("https://www.saucedemo.com/v1/index.html");
//        driver.get().browser().navigateToURL("http://www.amazon.com");
//        Cookie cookie = new Cookie("session-id", "143-7375436-4060747", ".amazon.com","/", null ,false,false);

//        driver.get().getDriver().manage().addCookie(cookie);
//        driver.get().browser().refreshPage();



    }

    @AfterClass(description = "Tear down")
    public void tearDown(){
        driver.get().browser().deleteAllCookies();
        driver.get().quit();
        driver.remove();
    }

}


