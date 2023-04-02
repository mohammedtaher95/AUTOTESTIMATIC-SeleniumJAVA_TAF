package utilities;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotHelper {

    //@Attachment(value = "Page screenshot", type = "image/png")
    public static Path captureScreenshot(WebDriver driver, String screenshotName) throws IOException {

        Path destination = Paths.get("./screenshots", screenshotName + ".jpg");
        Files.createDirectories(destination.getParent());
        FileOutputStream outputStream = new FileOutputStream(destination.toString());
        outputStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        outputStream.close();
        //Allure.addAttachment(screenshotName, FileUtils.openInputStream(destination.toFile()));

        return destination;
    }

}
