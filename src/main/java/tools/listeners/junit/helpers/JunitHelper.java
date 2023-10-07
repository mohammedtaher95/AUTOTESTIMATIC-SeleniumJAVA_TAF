package tools.listeners.junit.helpers;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Allure;
import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.*;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import tools.listeners.junit.JunitListener;
import utilities.LoggingManager;

import java.lang.reflect.Field;

public class JunitHelper implements LauncherSessionListener {

    private JunitHelper() {

    }

    @Override
    public void launcherSessionOpened(LauncherSession session) {
        RunNotifier notifier = new RunNotifier();
        session.getLauncher().registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void testPlanExecutionStarted(TestPlan testPlan) {
                TestExecutionListener.super.testPlanExecutionStarted(testPlan);
                notifier.addListener(new JunitListener());
            }
        });
        Allure.getLifecycle();
        LoggingManager.info("Starting Test Run via Junit");
        //Add Listener. This will register our JUnit Listener.
        //Get each test notifier
//        EachTestNotifier testNotifier = new EachTestNotifier(notifier,
//                getDescription());
//        try {
//            //In order capture testRunStarted method
//            //at the very beginning of the test run, we will add below code.
//            //Invoke here the run testRunStarted() method
//            notifier.fireTestRunStarted(getDescription());
//            Statement statement = classBlock(notifier);
//            statement.evaluate();
//        }
//        catch (AssumptionViolatedException e) {
//            testNotifier.fireTestIgnored();
//        }
//        catch (StoppedByUserException e) {
//            throw e;
//        }
//        catch (Throwable e) {
//            testNotifier.addFailure(e);
//        }
    }

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
