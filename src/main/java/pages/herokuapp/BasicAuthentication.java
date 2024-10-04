package pages.herokuapp;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BasicAuthentication {

    private final WebDriver driver;

    private final By successMessage = By.cssSelector("p");

    public BasicAuthentication(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Then an alert should be displayed")
    public BasicAuthentication checkThatBasicAuthAlertShouldBeDisplayed() {
        String text = driver.element().getAlertText();
        Assert.assertEquals(text, "Sign in to access this site");
        return this;
    }

    @Step("Then Authentication should be successful")
    public BasicAuthentication checkThatBasicAuthIsSuccessful() {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("Congratulations! You must have the proper credentials.");
        return this;
    }


}
