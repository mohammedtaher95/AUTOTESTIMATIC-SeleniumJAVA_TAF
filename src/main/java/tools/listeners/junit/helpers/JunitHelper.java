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

    public static void initializeLauncherSession() {
        LauncherConfig launcherConfig = LauncherConfig.builder()
                .enableTestEngineAutoRegistration(true)
                .enableLauncherSessionListenerAutoRegistration(false)
                .enableTestExecutionListenerAutoRegistration(false)
                .addLauncherSessionListeners(new JunitListener())
                .build();

        LauncherDiscoveryRequest discoveryRequest = LauncherDiscoveryRequestBuilder.request().build();
        Launcher launcher = LauncherFactory.create(launcherConfig);
        launcher.execute(discoveryRequest);

//        SummaryGeneratingListener listener = new SummaryGeneratingListener();
//        launcher.execute(discoveryRequest);



//        try(LauncherSession session = LauncherFactory.openSession(launcherConfig)) {
////            // Register a listener of your choice
//            Launcher launcher = session.getLauncher();
//            launcher.execute(discoveryRequest);
////            session.getLauncher().discover(discoveryRequest);
////            launcher.registerTestExecutionListeners(new JunitExecutionListener());
//            // Discover tests and build a test plan
//            // Execute test plan
//
//            // Alternatively, execute the request directly
////            launcher.execute(discoveryRequest);
//        }

        LoggingManager.info("Start Running via JUnit5");

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
