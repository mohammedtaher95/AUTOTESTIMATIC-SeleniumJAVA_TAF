package pages.registrationPage;

import static io.qameta.allure.Allure.step;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementActions.ElementActions.*;


public class UserRegistrationPage
{

	private WebDriver driver;

	By genderMaleRadioBtn = By.id("gender-male");
	By FirstName = By.id("FirstName");
	By LastName = By.id("LastName");;
	By Email = By.id("Email");;
	By Password = By.id("Password");;
	By ConfirmPassword = By.id("ConfirmPassword");;
	By registerBtn = By.id("register-button");;
	public By successMessage = By.cssSelector("div.result");

	public UserRegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Given User Navigated to Registration page")
	public UserRegistrationPage validateThatUserNavigatedToRegistrationPage(){
		//("Given User Navigated to Registration page");
		waitForVisibility(FirstName);
		Assert.assertTrue(driver.getCurrentUrl().contains("register"));
		return this;
	}

	@Step("When he fills registration form")
	public UserRegistrationPage fillUserRegistrationForm(String Firstname, String Lastname, String email, String password) {
		//step("When he fills registration form");
		clickButton(genderMaleRadioBtn);
		waitForVisibility(FirstName);
		Fill_in(FirstName, Firstname);
		waitForVisibility(LastName);
		Fill_in(LastName, Lastname);
		waitForVisibility(Email);
		clearField(Email);
		Fill_in(Email, email);
		waitForVisibility(Password);
		Fill_in(Password, password);
		waitForVisibility(ConfirmPassword);
		Fill_in(ConfirmPassword, password);
		return this;
	}

	@Step("And clicks on Register Button")
	public UserRegistrationPage clickOnRegisterButton(){
		//step("And clicks on Register Button");
		//waitForVisibility(registerBtn);
		Assert.assertTrue(ElementDisplayed(registerBtn));
		clickButton(registerBtn);
		return this;
	}

	@Step("Then Success Message should be displayed")
	public UserRegistrationPage validateThatSuccessMessageShouldBeDisplayed(){
		//step("Then Success Message should be displayed");
		//waitForVisibility(successMessage);
		Assert.assertTrue(ElementDisplayed(successMessage));
		Assert.assertTrue(getElementText(successMessage).contains("Your registration completed"));
		return this;
	}


}
