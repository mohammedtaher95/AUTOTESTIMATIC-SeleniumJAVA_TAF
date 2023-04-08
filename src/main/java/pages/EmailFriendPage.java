package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementactions.ElementActions.*;

public class EmailFriendPage{

    private WebDriver driver;

    By friendEmailField = By.id("FriendEmail");
    By yourEmailField = By.id("YourEmailAddress");
    By messageField = By.id("PersonalMessage");
    By sendBtn = By.cssSelector("button.button-1.send-email-a-friend-button");
    By successMessage = By.cssSelector("div.result");

    public EmailFriendPage(WebDriver driver) {
        this.driver = driver;
    }

    public EmailFriendPage fillEmailFriendForm(String friendEmail, String message) {
        fillField(friendEmailField, friendEmail);
        fillField(messageField,message);
        return this;

    }

    public EmailFriendPage clickOnSendButton()
    {
        clickButton(sendBtn);
        return this;
    }

    public EmailFriendPage checkThatSuccessMessageShouldBeDisplayed(String message)
    {
        waitForVisibility(successMessage);
        Assert.assertTrue(getElementText(successMessage).equalsIgnoreCase(message));
        return this;
    }


}
