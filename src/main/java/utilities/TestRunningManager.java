package utilities;

import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestRunningManager {

    static String servicesDirectoryPath = "src/test/resources/META-INF/services/";
    static Path testNG = Paths.get("src/test/resources/META-INF/services/org.testng.ITestNGListener");
    static Path junit = Paths.get("src/test/resources/META-INF/services/org.junit.platform.launcher.LauncherSessionListener");
    private static boolean isJunitRunBool = false;
    private static boolean isTestNGRunBool = false;


    private TestRunningManager(){

    }

    public static void initializeRunConfigurations(){

        identifyRunType();
        File servicesDirectory = new File(servicesDirectoryPath);

        if(!servicesDirectory.exists()){
            LoggingManager.info("Services aren't configured....Generating Configurations");
            boolean created = servicesDirectory.mkdirs();
            if(created){
                LoggingManager.info("Services Directory Created");
            }
        }

        if(servicesDirectory.exists()){
            if(Files.exists(testNG) && (isJunitRunBool)){
                    try {
                        Files.delete(testNG);
                    } catch (IOException e) {
                        // Do nothing
                    }
            }

            if(Files.exists(junit) && (isTestNGRunBool)){
                try {
                    Files.delete(junit);
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }

        if(isTestNGRunBool && !Files.exists(testNG)) {
            try {
                Files.writeString(testNG,"tools.listeners.testng.TestNGListener",
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                // Do nothing
            }
        }

        if(isJunitRunBool && !Files.exists(junit)) {
            try {
                Files.writeString(junit,"tools.listeners.junit.JunitListener",
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                // Do nothing
            }
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


    public enum ServiceType {TESTNG, JUNIT}
}
