package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.homepage.HomePage;

import static elementactions.ElementActions.*;

public class MyAccountPage{

    private WebDriver driver;

    By changePasswordLink = By.linkText("Change password");
    By customerInfo = By.linkText("Customer info");
    By oldPasswordTxt = By.id("OldPassword");
    By newPasswordTxt = By.id("NewPassword");
    By confirmPasswordTxt = By.id("ConfirmNewPassword");
    By changePasswordBtn = By.cssSelector("button.button-1.change-password-button");
    By changeResult = By.cssSelector("p.content");
    By messageCloseBtn = By.cssSelector("span.close");
    By logoutLink = By.cssSelector("a.ico-logout");

	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
	}

    @Step("And clicks on Change Password link")
    public MyAccountPage openChangePasswordpage()
    {
    	clickButton(changePasswordLink);
        return this;
    }

    @Step("And Fills old and new passwords")
    public MyAccountPage changePassword(String oldPass, String newPass)
    {
    	fillField(oldPasswordTxt, oldPass);
    	fillField(newPasswordTxt, newPass);
    	fillField(confirmPasswordTxt, newPass);
        return this;
    }

    @Step("And Clicks on Confirm button")
    public MyAccountPage clickOnConfirm()
    {
        clickButton(changePasswordBtn);
        return this;
    }

    @Step("Then Confirmation message should be displayed that password was changed")
    public MyAccountPage checkThatChangeMessageShouldBeDisplayed()
    {
        waitForVisibility(changeResult);
        Assert.assertTrue(getElementText(changeResult).contains("Password was changed"));
        return this;
    }

    @Step("And User can dismiss message")
    public MyAccountPage closeMessage()
    {
    	clickButton(messageCloseBtn);
        return this;
    }

    @Step("And User can logout")
    public HomePage clickOnLogoutButton()
    {
        waitForVisibility(logoutLink);
        Assert.assertTrue(waitForElementToBeClickable(logoutLink));
        clickButton(logoutLink);
        return new HomePage(driver);
    }
    

}
