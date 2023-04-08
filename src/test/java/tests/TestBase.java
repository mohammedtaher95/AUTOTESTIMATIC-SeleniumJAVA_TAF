package tests;

import driverfactory.Webdriver;
import org.testng.annotations.*;


public class TestBase {

    driverfactory.Webdriver driver;
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
