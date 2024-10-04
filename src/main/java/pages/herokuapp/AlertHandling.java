package pages.herokuapp;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class AlertHandling {

    private final WebDriver driver;

    By title = By.cssSelector("h3");
    By clickForJavaScriptAlert = By.xpath("//button[contains(text(),'Click for JS Alert')]");
    By clickForJavaScriptConfirm = By.xpath("//button[contains(text(),'Click for JS Confirm')]");
    By clickForJavaScriptPrompt = By.xpath("//button[contains(text(),'Click for JS Prompt')]");
    By successMessage = By.cssSelector("p#result");

    public AlertHandling(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Then JS alerts page should be displayed")
    public AlertHandling checkThatJavaScriptAlertsPageShouldBeOpened() {
        driver.assertThat().element(title).text().isEqualTo("JavaScript Alerts");
        return this;
    }

    @Step("When user clicks on JS Alert button")
    public AlertHandling clickOnJavaScriptAlertButtonAndAcceptAlert() {
        driver.element().click(clickForJavaScriptAlert);
        String text = driver.element().getAlertText();
        Assert.assertEquals(text, "I am a JS Alert");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJavaScriptAlertIsAccepted() {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You successfully clicked an alert");
        return this;
    }

    @Step("When user clicks on JS Confirm button")
    public AlertHandling clickOnJavaScriptConfirmButtonAndAcceptAlert() {
        driver.element().click(clickForJavaScriptConfirm);
        String text = driver.element().getAlertText();
        Assert.assertEquals(text, "I am a JS Confirm");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJavaScriptConfirmIsAccepted() {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You clicked: Ok");
        return this;
    }

    @Step("When user clicks on JS Prompt button")
    public AlertHandling clickOnJavaScriptPromptButtonAndAcceptAlert() {
        driver.element().click(clickForJavaScriptPrompt);
        driver.element().addTextForAlert("Welcome");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJavaScriptPromptIsAccepted() {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You entered: Welcome");
        return this;
    }
}
