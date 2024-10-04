package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.platform.launcher.LauncherSessionListener;
import org.testng.ITestNGListener;
import org.testng.TestNG;

public class TestRunningManager {

    private static boolean isJunitRunBool = false;
    private static boolean isTestNGRunBool = false;
    private static final File servicesDirectory = new File("src/test/resources/META-INF/services/");
    private static final Path testNGServiceFile =
          Paths.get("src/test/resources/META-INF/services/org.testng.ITestNGListener");
    private static final Path junit5ServiceFile = Paths.get(
          "src/test/resources/META-INF/services/"
                + "org.junit.platform.launcher.LauncherSessionListener");

    private TestRunningManager() {

    }

    public static void initializeRunConfigurations() {

        identifyRunType();

        if (isTestNGRunBool) {
            ServiceLoader<ITestNGListener> serviceLoader =
                  ServiceLoader.load(ITestNGListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via TestNG Runner");

            loadingServices();
        }

        if (isJunitRunBool) {
            ServiceLoader<LauncherSessionListener> serviceLoader =
                  ServiceLoader.load(LauncherSessionListener.class);
            serviceLoader.reload();
            LoggingManager.info("Start Running Tests via JUnit 5 Runner");

            loadingServices();
        }


    }


    private static void identifyRunType() {

        Supplier<Stream<?>> stacktraceSupplier =
              () -> Arrays.stream((new Throwable()).getStackTrace())
                    .map(StackTraceElement::getClassName);
        boolean isUsingJunitDiscovery = stacktraceSupplier.get().anyMatch(
              org.junit.platform.launcher.core
                    .EngineDiscoveryOrchestrator.class.getCanonicalName()::equals);
        boolean isUsingTestNg =
              stacktraceSupplier.get().anyMatch(TestNG.class.getCanonicalName()::equals);

        if (isUsingJunitDiscovery || isUsingTestNg) {
            isTestNGRunBool = true;

        } else {
            isJunitRunBool = true;
        }
    }

    private static void loadingServices() {

        if (!servicesDirectory.exists()) {
            boolean created = servicesDirectory.mkdirs();
            if (created) {
                LoggingManager.info("Services Directory Created");
            }
        }

        if (isTestNGRunBool) {
            if (!Files.exists(testNGServiceFile)) {
                try {
                    Files.writeString(testNGServiceFile, "tools.listeners.testng.TestNGListener",
                          StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    LoggingManager.error("Unable to create TestNG Service File");
                }
            }

            if (Files.exists(junit5ServiceFile)) {
                try {
                    Files.delete(junit5ServiceFile);
                } catch (IOException e) {
                    LoggingManager.error("JUnit5 Service File is Removed" + e.getMessage());
                }
            }
        }

        if (isJunitRunBool) {
            if (!Files.exists(junit5ServiceFile)) {
                try {
                    Files.writeString(junit5ServiceFile, "tools.listeners.junit.JunitListener",
                          StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    LoggingManager.error("Unable to create JUnit5 Service File" + e.getMessage());
                }
            }

            if (Files.exists(testNGServiceFile)) {
                try {
                    Files.delete(testNGServiceFile);
                } catch (IOException e) {
                    LoggingManager.error("TestNG Service File is Removed");
                }
            }
        }
    }


}
