package pages;

import driverfactory.Webdriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.homepage.HomePage;

public class MyAccountPage{

    private final Webdriver driver;

    By changePasswordLink = By.linkText("Change password");
    By customerInfo = By.linkText("Customer info");
    By oldPasswordTxt = By.id("OldPassword");
    By newPasswordTxt = By.id("NewPassword");
    By confirmPasswordTxt = By.id("ConfirmNewPassword");
    By changePasswordBtn = By.cssSelector("button.button-1.change-password-button");
    By changeResult = By.cssSelector("p.content");
    By messageCloseBtn = By.cssSelector("span.close");
    By logoutLink = By.cssSelector("a.ico-logout");

	public MyAccountPage(Webdriver driver) {
		this.driver = driver;
	}

    @Step("And clicks on Change Password link")
    public MyAccountPage openChangePasswordpage()
    {
        driver.element().click(changePasswordLink);
        return this;
    }

    @Step("And Fills old and new passwords")
    public MyAccountPage changePassword(String oldPass, String newPass)
    {
        driver.element().fillField(oldPasswordTxt, oldPass);
        driver.element().fillField(newPasswordTxt, newPass);
        driver.element().fillField(confirmPasswordTxt, newPass);
        return this;
    }

    @Step("And Clicks on Confirm button")
    public MyAccountPage clickOnConfirm()
    {
        driver.element().click(changePasswordBtn);
        return this;
    }

    @Step("Then Confirmation message should be displayed that password was changed")
    public MyAccountPage checkThatChangeMessageShouldBeDisplayed()
    {
        driver.element().waitForVisibility(changeResult);
        Assert.assertTrue(driver.element().getElementText(changeResult).contains("Password was changed"));
        return this;
    }

    @Step("And User can dismiss message")
    public MyAccountPage closeMessage()
    {
        driver.element().click(messageCloseBtn);
        return this;
    }

    @Step("And User can logout")
    public HomePage clickOnLogoutButton()
    {
        driver.element().waitForVisibility(logoutLink);
        Assert.assertTrue(driver.element().waitForElementToBeClickable(logoutLink));
        driver.element().click(logoutLink);
        return new HomePage(driver);
    }
    

}
