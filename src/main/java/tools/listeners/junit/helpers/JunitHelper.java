package tools.listeners.junit.helpers;

import driverfactory.webdriver.WebDriver;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import tools.listeners.junit.JunitListener;
import utilities.LoggingManager;
import java.lang.reflect.Field;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class JunitHelper {

    private JunitHelper() {

    }

    public static WebDriver getDriverInstance(TestIdentifier testIdentifier) {
        WebDriver driver = null;
        ThreadLocal<WebDriver> driverThreadlocal;
        Object currentClass = testIdentifier.getClass();
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
