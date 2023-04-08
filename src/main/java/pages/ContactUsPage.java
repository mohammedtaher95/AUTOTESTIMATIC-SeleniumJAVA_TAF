package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementactions.ElementActions.*;

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
        fillField(nameField, name);
        fillField(emailField, email);
        fillField(enquiryField, enquiry);
        return this;
    }

    @Step("And Clicks on Submit button")
    public ContactUsPage clickOnSubmitButton(){
        clickButton(submitBtn);
        return this;
    }

    @Step("Then success Message Should Be Displayed")
    public ContactUsPage successMessageShouldBeDisplayed(String message){
        waitForVisibility(successMessage);
        Assert.assertTrue(elementDisplayed(successMessage));
        Assert.assertTrue(getElementText(successMessage).equalsIgnoreCase(message));

        return this;
    }
}
