package pages.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;

public class EmailFriendPage {

    private final WebDriver driver;

    By friendEmailField = By.id("FriendEmail");
    By yourEmailField = By.id("YourEmailAddress");
    By messageField = By.id("PersonalMessage");
    By sendBtn = By.cssSelector("button.button-1.send-email-a-friend-button");
    By successMessage = By.cssSelector("div.result");

    public EmailFriendPage(WebDriver driver) {
        this.driver = driver;
    }

    public EmailFriendPage fillEmailFriendForm(String friendEmail, String message) {
        driver.element().fillField(friendEmailField, friendEmail);
        driver.element().fillField(messageField, message);
        return this;

    }

    public EmailFriendPage clickOnSendButton() {
        driver.element().click(sendBtn);
        return this;
    }

    public EmailFriendPage checkThatSuccessMessageShouldBeDisplayed(String message) {
        driver.element().waitForVisibility(successMessage);
        driver.assertThat().element(successMessage).text().isEqualTo(message);
        return this;
    }


}
