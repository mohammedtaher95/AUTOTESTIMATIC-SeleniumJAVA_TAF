package utilities;

import org.junit.platform.launcher.LauncherSessionListener;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestRunningManager {

    private static boolean isJunitRunBool = false;
    private static boolean isTestNGRunBool = false;

    private TestRunningManager() {

    }

    public static void initializeRunConfigurations() {

        identifyRunType();

        if (isTestNGRunBool) {
            ServiceLoader<ITestNGListener> serviceLoader = ServiceLoader.load(ITestNGListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via TestNG Runner");
        }

        if (isJunitRunBool) {
            ServiceLoader<LauncherSessionListener> serviceLoader =
                    ServiceLoader.load(LauncherSessionListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via JUnit 5 Runner");
        }


    }


    private static void identifyRunType() {

        Supplier<Stream<?>> stacktraceSupplier = () -> Arrays.stream((new Throwable()).getStackTrace()).map(StackTraceElement::getClassName);
        boolean isUsingJunitDiscovery = stacktraceSupplier.get().anyMatch(org.junit.platform.launcher.core.EngineDiscoveryOrchestrator.class.getCanonicalName()::equals);
        boolean isUsingTestNG = stacktraceSupplier.get().anyMatch(TestNG.class.getCanonicalName()::equals);

        if (isUsingJunitDiscovery || isUsingTestNG) {
            isTestNGRunBool = true;

        } else {
            isJunitRunBool = true;
        }
    }


}
