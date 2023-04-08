package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScreenshotHelper {

    private ScreenshotHelper(){

    }
    public static Path captureScreenshot(WebDriver driver, String screenshotName) throws IOException {

        Path destination = Paths.get("./screenshots", screenshotName + ".jpg");
        byte[] byteArray = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.write(destination, byteArray, StandardOpenOption.CREATE);
        return destination;
    }

}
