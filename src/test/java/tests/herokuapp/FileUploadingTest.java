package tests.herokuapp;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.herokuapp.HomePage;


public class FileUploadingTest {

    public static ThreadLocal<WebDriver> driver;

    @Issue(" ")
    @TmsLink("Nop Commerce_1-User Registration")
    @Description("User can access registration page and register successfully")
    @Test(description = "User Register on website successfully")
    public void checkFileUploading(){

        new HomePage(driver.get()).openFileUploadPage()
                .uploadingFile()
                .clickOnSubmitButton()
                .checkThatFileShouldBeUploaded();

    }

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
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
