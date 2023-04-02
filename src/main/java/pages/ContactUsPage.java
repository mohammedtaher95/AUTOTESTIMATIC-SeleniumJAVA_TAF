package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static elementActions.ElementActions.*;

public class ContactUsPage{

    private WebDriver driver;

    By nameField = By.id("FullName");
    By emailField = By.id("Email");
    By enquiryField = By.id("Enquiry");
    By submitBtn = By.name("send-email");
    By successMessage = By.cssSelector("div.result");

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("When he fills contact info Form")
    public ContactUsPage FillContactInfoForm(String Name, String Email, String Enquiry)
    {
        Fill_in(nameField, Name);
        Fill_in(emailField, Email);
        Fill_in(enquiryField, Enquiry);
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
        Assert.assertTrue(ElementDisplayed(successMessage));
        Assert.assertTrue(getElementText(successMessage).equalsIgnoreCase(message));

        return this;
    }
}
