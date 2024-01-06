package utilities.docker;

import org.apache.commons.lang.SystemUtils;
import utilities.LoggingManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DockerFilesGenerator {

    private DockerFilesGenerator() {

    }

    public static void generateBatFiles() {
        Path upFile;
        Path downFile;

        if (SystemUtils.IS_OS_WINDOWS) {
            upFile = Paths.get("dockerComposeUp.bat");
            downFile = Paths.get("dockerComposeDown.bat");
        } else {
            upFile = Paths.get("dockerComposeUp.sh");
            downFile = Paths.get("dockerComposeDown.sh");
        }

        if (!Files.exists(upFile)) {
            try {
                Files.writeString(upFile, "docker-compose up -d\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                LoggingManager.error("Unable to create docker-compose up -d Batch File" + e.getMessage());
            }
        }

        if (!Files.exists(downFile)) {
            try {
                Files.writeString(downFile, "docker-compose down\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                LoggingManager.error("Unable to create docker-compose down Batch File" + e.getMessage());
            }
        }
    }
}
