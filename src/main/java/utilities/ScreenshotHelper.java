package utilities;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class ScreenshotHelper {

    static String screenshotsDirectoryPath = "./screenshots";
    private static final File screenshotsDirectory = new File(screenshotsDirectoryPath);
    private ScreenshotHelper(){

    }
    public static Path captureScreenshot(WebDriver driver, String screenshotName){

        if(!screenshotsDirectory.exists()){
            boolean created = screenshotsDirectory.mkdirs();
            if(created){
                LoggingManager.info("Screenshots Directory Created");
            }
        }

        Path destination = Paths.get("./screenshots", screenshotName + ".jpg");
        byte[] byteArray = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(destination, byteArray, StandardOpenOption.CREATE);
        } catch (IOException e) {
            LoggingManager.error("Unable to capture Screenshot " + e.getMessage());
        }
        return destination;
    }

}
