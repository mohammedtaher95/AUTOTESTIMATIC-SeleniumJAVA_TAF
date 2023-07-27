package pages.herokuapp;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage {

    private final WebDriver driver;

    By basicAuthLink = By.xpath("//a[@href='/basic_auth']");
    By jsAlerts = By.xpath("//a[@href='/javascript_alerts']");
    By fileUpload = By.xpath("//a[@href='/upload']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("When user clicks on Alert page link")
    public AlertHandling openJSAlertsPage()
    {
        driver.element().click(jsAlerts);
        return new AlertHandling(driver);
    }

    @Step("When user clicks on Alert page link")
    public BasicAuthentication openBasicAuthPage()
    {
        driver.element().click(basicAuthLink);
        return new BasicAuthentication(driver);
    }

    @Step("When user clicks on Alert page link")
    public FileUpload openFileUploadPage()
    {
        driver.element().click(fileUpload);
        return new FileUpload(driver);
    }

}
