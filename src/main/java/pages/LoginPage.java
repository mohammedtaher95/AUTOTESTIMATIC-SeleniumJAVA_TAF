package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.homepage.HomePage;

import static elementactions.ElementActions.*;

public class LoginPage{

	private final WebDriver driver;

	By emailField = By.id("Email");
	By passwordField = By.id("Password");
	By loginBtn = By.cssSelector("button.button-1.login-button");
	By logoutLink = By.cssSelector("a.ico-logout");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage userLogin(String email, String password)
	{
		fillField(emailField, email);
		fillField(passwordField, password);
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
		Assert.assertTrue(elementDisplayed(logoutLink));
		return this;
	}

	public HomePage clickOnLogoutButton()
	{
		clickButton(logoutLink);
		return new HomePage(driver);
	}



}
