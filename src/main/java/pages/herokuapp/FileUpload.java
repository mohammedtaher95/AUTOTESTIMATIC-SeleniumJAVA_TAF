package pages.herokuapp;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;

public class FileUpload {

    public final WebDriver driver;

    By chooseFileButton = By.id("file-upload");
    By uploadFileButton = By.id("file-submit");
    By successMessage = By.cssSelector("h3");

    public FileUpload(WebDriver driver){
        this.driver = driver;
    }

    public FileUpload uploadingFile(){
        driver.element().fillField(chooseFileButton,"C:\\Users\\moham\\Downloads\\Cover_Letter.docx");
        return this;
    }

    public FileUpload clickOnSubmitButton(){
        driver.element().click(uploadFileButton);
        return this;
    }

    public FileUpload checkThatFileShouldBeUploaded(){
        driver.assertThat().element(successMessage).text().isEqualTo("File Uploaded!");
        return this;
    }
}
