package pages;

import driverfactory.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ContactUsPage{

    private final WebDriver driver;

    By nameField = By.id("FullName");
    By emailField = By.id("Email");
    By enquiryField = By.id("Enquiry");
    By submitBtn = By.name("send-email");
    By successMessage = By.cssSelector("div.result");

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("When he fills contact info Form")
    public ContactUsPage fillContactInfoForm(String name, String email, String enquiry)
    {
        driver.element().fillField(nameField, name);
        driver.element().fillField(emailField, email);
        driver.element().fillField(enquiryField, enquiry);
        return this;
    }

    @Step("And Clicks on Submit button")
    public ContactUsPage clickOnSubmitButton(){
        driver.element().click(submitBtn);
        return this;
    }

    @Step("Then success Message Should Be Displayed")
    public ContactUsPage successMessageShouldBeDisplayed(String message){
        driver.element().waitForVisibility(successMessage);
        Assert.assertTrue(driver.element().isDisplayed(successMessage));
        Assert.assertTrue(driver.element().getElementText(successMessage).equalsIgnoreCase(message));

        return this;
    }
}
