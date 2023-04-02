package pages.homePage;

import clojure.lang.Compiler;
import elementActions.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.ContactUsPage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.registrationPage.UserRegistrationPage;

import static browserActions.BrowserActions.scrollToBottom;

public class HomePage extends ElementActions {

	private WebDriver driver;

	By registerLink = By.linkText("Register");
	By loginLink = By.linkText("Log in");
	By ContactUsLink = By.linkText("Contact us");
	By CurrencyDropDownList = By.id("customerCurrency");
	By ComputersMenu = By.linkText("Computers");
	By NotebooksMenu = By.linkText("Notebooks");
	By MyAccountLink = By.linkText("My account");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Given user navigated to Registration page")
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
		clickButton(ContactUsLink);
		return new ContactUsPage(driver);
	}

	@Step("When user clicks on My Account link")
	public MyAccountPage openMyAccountPage()
	{
		clickButton(MyAccountLink);
		return new MyAccountPage(driver);
	}

	public void changeCurrency(int index)
	{
		SelectItemByIndex(CurrencyDropDownList, index);
	}

//	public void SelectNotebookMenu()
//	{
//		action.moveToElement(ComputersMenu)
//				.moveToElement(NotebooksMenu)
//				.click().build().perform();
//	}


}
