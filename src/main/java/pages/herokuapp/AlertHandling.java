package pages.herokuapp;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class AlertHandling {

    private final WebDriver driver;

    By title = By.cssSelector("h3");
    By clickForJSAlert = By.xpath("//button[contains(text(),'Click for JS Alert')]");
    By clickForJSConfirm = By.xpath("//button[contains(text(),'Click for JS Confirm')]");
    By clickForJSPrompt = By.xpath("//button[contains(text(),'Click for JS Prompt')]");
    By successMessage = By.cssSelector("p#result");

    public AlertHandling(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Then JS alerts page should be displayed")
    public AlertHandling checkThatJSAlertsPageShouldBeOpened()
    {
        driver.assertThat().element(title).text().isEqualTo("JavaScript Alerts");
        return this;
    }

    @Step("When user clicks on JS Alert button")
    public AlertHandling clickOnJSAlertButtonAndAcceptAlert()
    {
        driver.element().click(clickForJSAlert);
        String text = driver.element().getAlertText();
        Assert.assertEquals(text, "I am a JS Alert");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJSAlertIsAccepted()
    {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You successfully clicked an alert");
        return this;
    }

    @Step("When user clicks on JS Confirm button")
    public AlertHandling clickOnJSConfirmButtonAndAcceptAlert()
    {
        driver.element().click(clickForJSConfirm);
        String text = driver.element().getAlertText();
        Assert.assertEquals(text, "I am a JS Confirm");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJSConfirmIsAccepted()
    {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You clicked: Ok");
        return this;
    }

    @Step("When user clicks on JS Prompt button")
    public AlertHandling clickOnJSPromptButtonAndAcceptAlert()
    {
        driver.element().click(clickForJSPrompt);
        driver.element().addTextForAlert("Welcome");
        driver.element().acceptAlert();
        return this;
    }

    @Step("Then Alert should be Accepted")
    public AlertHandling checkThatJSPromptIsAccepted()
    {
        driver.assertThat().element(successMessage).text()
                .isEqualTo("You entered: Welcome");
        return this;
    }
}
