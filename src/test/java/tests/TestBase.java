package tests;

import driverFactory.Webdriver;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.*;


public class TestBase {

    driverFactory.Webdriver driver;
    //public Logger log;

    @BeforeClass(description = "Setup Driver")
    public void setUp(){
        //.setProperty("BASE_URL", "http://demo.nopcommerce.com");

        driver = new Webdriver();
        //navigateToURL("http://demo.nopcommerce.com");
    }

    @AfterClass(description = "Teardown")
    public void tearDown(){
        driver.quit();
    }
}
