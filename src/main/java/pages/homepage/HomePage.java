package pages.homepage;

import driverfactory.webdriver.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.ContactUsPage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.registrationpage.UserRegistrationPage;


public class HomePage{

	private final WebDriver driver;

	By registerLink = By.linkText("Register");
	By loginLink = By.linkText("Log in");
	By contactUsLink = By.linkText("Contact us");
	By currencyDropDownList = By.id("customerCurrency");
	By computersMenu = By.linkText("Computers");
	By notebooksMenu = By.linkText("Notebooks");
	By myAccountLink = By.linkText("My account");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Given user clicks on Registration page link")
	public UserRegistrationPage openRegistrationPage()
	{
		driver.element().click(registerLink);
		return new UserRegistrationPage(driver);
	}

	@Step("When user clicks on Login Link")
	public LoginPage openLoginPage()
	{
		driver.element().click(loginLink);
		return new LoginPage(driver);
	}

	@Step("Given user opened Contact us link")
	public ContactUsPage openContactUsPage()
	{
		driver.browser().scrollToBottom();
		driver.element().click(contactUsLink);
		return new ContactUsPage(driver);
	}

	@Step("When user clicks on My Account link")
	public MyAccountPage openMyAccountPage()
	{
		driver.element().click(myAccountLink);
		return new MyAccountPage(driver);
	}

	public void changeCurrency(int index)
	{
		driver.element().selectItemByIndex(currencyDropDownList, index);
	}

	public void selectNotebookMenu()
	{
		driver.element().hoverOnItem(computersMenu).hoverOnItem(notebooksMenu);
	}

}