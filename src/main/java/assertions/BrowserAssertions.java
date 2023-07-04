package assertions;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import utilities.LoggingManager;

public class BrowserAssertions {

    private final Assertion assertion;
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private String actual;

    public BrowserAssertions(Assertion assertion, WebDriver driver){
        this.assertion = assertion;
        driverThreadLocal.set(driver);
    }

    public Validations url(){
        this.actual = driverThreadLocal.get().getCurrentUrl();
        return new Validations();
    }

    public Validations title(){
        this.actual = driverThreadLocal.get().getTitle();
        return new Validations();
    }


    public void removeDriver(){
        driverThreadLocal.remove();
    }

    public class Validations{

        private Validations(){

        }

        public void contains(String expected){
            try {
                assertion.assertTrue(actual.contains(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);

            }
            catch (AssertionError e){
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        public void doesNotContain(String expected){
            try {
                assertion.assertTrue(actual.contains(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            }
            catch (AssertionError e){
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        public void isNotEqualTo(String expected){
            try {
                assertion.assertTrue(!actual.equalsIgnoreCase(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            }
            catch (AssertionError e){
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

        public void isEqualTo(String expected){
            try {
                assertion.assertTrue(actual.equalsIgnoreCase(expected));
                LoggingManager.info("Expected: " + expected + ", Actual: " + actual);
            }
            catch (AssertionError e){
                LoggingManager.error("Expected: " + expected + ", but Actual: " + actual);
                throw e;
            }
        }

    }
}
