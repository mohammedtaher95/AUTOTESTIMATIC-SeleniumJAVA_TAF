package pages.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;
import pages.nopcommerce.homepage.HomePage;

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
		driver.element().fillField(emailField, email);
		driver.element().fillField(passwordField, password);
		return this;
	}

	public LoginPage clickOnLoginButton()
	{
		driver.element().click(loginBtn);
		return this;
	}

	public LoginPage checkThatLogoutButtonShouldBeDisplayed()
	{
		driver.element().waitForVisibility(logoutLink);
		driver.assertThat().element(logoutLink).isDisplayed();
		return this;
	}

	public HomePage clickOnLogoutButton()
	{
		driver.element().click(logoutLink);
		return new HomePage(driver);
	}



}
