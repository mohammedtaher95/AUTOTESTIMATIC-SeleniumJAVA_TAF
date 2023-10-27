package utilities;

import com.google.auto.service.processor.AutoServiceProcessor;
import org.junit.platform.launcher.LauncherSessionListener;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import javax.imageio.spi.ServiceRegistry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestRunningManager {

    private static final Path testNG = Paths.get("target/classes/META-INF/services/org.testng.ITestNGListener");
    private static final Path junit = Paths.get("target/classes/META-INF/services/org.junit.platform.launcher.LauncherSessionListener");
    private static boolean isJunitRunBool = false;
    private static boolean isTestNGRunBool = false;


    private TestRunningManager() {

    }

    public static void initializeRunConfigurations() {

        identifyRunType();

        if (isTestNGRunBool) {

            if(Files.exists(junit)){
                try {
                    Files.delete(junit);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ServiceLoader<ITestNGListener> serviceLoader = ServiceLoader.load(ITestNGListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via TestNG Runner");
        }

        if (isJunitRunBool) {
            if(Files.exists(testNG)){
                try {
                    Files.delete(testNG);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ServiceLoader<LauncherSessionListener> serviceLoader =
                    ServiceLoader.load(LauncherSessionListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via JUnit 5 Runner");
        }


    }

    private static void identifyRunType() {
        Supplier<Stream<?>> stacktraceSupplier = () -> Arrays.stream((new Throwable()).getStackTrace()).map(StackTraceElement::getClassName);
        var isUsingJunitDiscovery = stacktraceSupplier.get().anyMatch(org.junit.platform.launcher.core.EngineDiscoveryOrchestrator.class.getCanonicalName()::equals);
        var isUsingTestNG = stacktraceSupplier.get().anyMatch(TestNG.class.getCanonicalName()::equals);
        if (isUsingJunitDiscovery || isUsingTestNG) {
            isTestNGRunBool = true;
        } else {
            isJunitRunBool = true;
        }
    }

}
