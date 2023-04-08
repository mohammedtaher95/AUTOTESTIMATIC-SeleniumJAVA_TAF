package pages.homepage;


import elementactions.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.ContactUsPage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.registrationpage.UserRegistrationPage;

import static browseractions.BrowserActions.scrollToBottom;

public class HomePage extends ElementActions {

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
		clickButton(registerLink);
		return new UserRegistrationPage(driver);
	}

	@Step("When user clicks on Login Link")
	public LoginPage openLoginPage()
	{
		clickButton(loginLink);
		return new LoginPage(driver);
	}

	@Step("Given user opened Contact us link")
	public ContactUsPage openContactUsPage()
	{
		scrollToBottom();
		clickButton(contactUsLink);
		return new ContactUsPage(driver);
	}

	@Step("When user clicks on My Account link")
	public MyAccountPage openMyAccountPage()
	{
		clickButton(myAccountLink);
		return new MyAccountPage(driver);
	}

	public void changeCurrency(int index)
	{
		selectItemByIndex(currencyDropDownList, index);
	}

//	public void SelectNotebookMenu()
//	{
//		action.moveToElement(ComputersMenu)
//				.moveToElement(NotebooksMenu)
//				.click().build().perform();
//	}

}