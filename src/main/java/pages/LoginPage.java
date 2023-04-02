package pages;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pages.homePage.HomePage;

import static elementActions.ElementActions.*;

public class LoginPage{

	private WebDriver driver;

	By emailField = By.id("Email");
	By passwordField = By.id("Password");
	By loginBtn = By.cssSelector("button.button-1.login-button");
	public By logoutLink = By.cssSelector("a.ico-logout");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage userLogin(String email, String password)
	{
		Fill_in(emailField, email);
		Fill_in(passwordField, password);
		return this;
	}

	public LoginPage clickOnLoginButton()
	{
		clickButton(loginBtn);
		return this;
	}

	public LoginPage checkThatLogoutButtonShouldBeDisplayed()
	{
		waitForVisibility(logoutLink);
		Assert.assertTrue(ElementDisplayed(logoutLink));
		return this;
	}

	public HomePage clickOnLogoutButton()
	{
		waitForVisibility(logoutLink);
		clickButton(logoutLink);
		return new HomePage(driver);
	}



}
