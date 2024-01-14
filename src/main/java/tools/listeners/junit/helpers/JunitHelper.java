package tools.listeners.junit.helpers;

import constants.CrossBrowserMode;
import driverfactory.webdriver.WebDriver;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.engine.TestSource;
import org.junit.platform.launcher.*;
import tools.properties.Properties;
import utilities.LoggingManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;


public class JunitHelper implements BeforeAllCallback{

    private JunitHelper() {

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        boolean parallelExecutionEnabled =
                Properties.executionOptions.crossBrowserMode().equalsIgnoreCase(String.valueOf(CrossBrowserMode.PARALLEL));
        if (parallelExecutionEnabled) {
            // Enable parallel execution for the entire test suite
            LoggingManager.info("Cross Browsing Mode Enabled, Tests will run in Parallel Mode");
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .put("junit.jupiter.execution.parallel.enabled", Boolean.TRUE);
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .put("junit.jupiter.execution.parallel.mode.default", ExecutionMode.CONCURRENT);
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .put("junit.jupiter.execution.parallel.config.dynamic.factor", 2);
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .put("junit.jupiter.execution.parallel.config.fixed.parallelism", 2);

            // Update the parallel execution setting as needed
            // This may involve updating the ExtensionContext, TestTemplateInvocationContext, etc.

        } else {
            // Handle non-parallel execution
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .put("junit.jupiter.execution.parallel.enabled", Boolean.FALSE);
        }
    }

    public static WebDriver getDriverInstance(TestIdentifier testIdentifier) {
        WebDriver driver = null;
        ThreadLocal<WebDriver> driverThreadlocal;
        Class<?> currentClass = null;
        try {
            currentClass = Class.forName(testIdentifier.getUniqueIdObject().getSegments().get(1).getValue());
        } catch (ClassNotFoundException e) {
            LoggingManager.error("Class is Undefined");
            LoggingManager.error(e);
        }
        if (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getType() == WebDriver.class) {
                        driver = (WebDriver) field.get(currentClass);
                    }

                    if (field.getType() == ThreadLocal.class) {
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
