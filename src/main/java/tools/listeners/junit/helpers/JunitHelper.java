package tools.listeners.junit.helpers;

import driverfactory.webdriver.WebDriver;
import org.junit.runner.notification.Failure;
import org.testng.ITestResult;
import utilities.LoggingManager;

import java.lang.reflect.Field;

public class JunitHelper {

    public static WebDriver getDriverInstance(Failure failure) {
        WebDriver driver = null;
        ThreadLocal<WebDriver> driverThreadlocal;
        Object currentClass = failure.getDescription().getTestClass();
        if (currentClass != null) {
            Field[] fields = currentClass.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getType() == WebDriver.class) {
                        field.setAccessible(true);
                        driver = (WebDriver) field.get(currentClass);
                    }

                    if (field.getType() == ThreadLocal.class) {
                        field.setAccessible(true);
                        driverThreadlocal = (ThreadLocal<WebDriver>) field.get(currentClass);
                        driver = driverThreadlocal.get();
                    }

                } catch (IllegalAccessException e) {
                    LoggingManager.error("Unable to access field: " + field.getName());
                }
            }
        }
        return driver;
    }

}
