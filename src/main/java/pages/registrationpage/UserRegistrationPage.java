package pages.registrationpage;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementactions.ElementActions.*;


public class UserRegistrationPage
{

	private final WebDriver driver;

	By genderMaleRadioBtn = By.id("gender-male");
	By firstName = By.id("FirstName");
	By lastName = By.id("LastName");
	By emailField = By.id("Email");
	By passwordField = By.id("Password");
	By confirmPassword = By.id("ConfirmPassword");
	By registerBtn = By.id("register-button");
	By successMessage = By.cssSelector("div.result");

	public UserRegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Then User should be Navigated to Registration page")
	public UserRegistrationPage validateThatUserNavigatedToRegistrationPage(){

		//waitForVisibility(firstName);
		Assert.assertTrue(driver.getCurrentUrl().contains("register"));
		return this;
	}

	@Step("When he fills registration form")
	public UserRegistrationPage fillUserRegistrationForm(String firstname, String lastname, String email, String password) {
		clickButton(genderMaleRadioBtn);
		waitForVisibility(firstName);
		fillField(firstName, firstname);
		waitForVisibility(lastName);
		fillField(lastName, lastname);
		waitForVisibility(emailField);
		clearField(emailField);
		fillField(emailField, email);
		waitForVisibility(passwordField);
		fillField(passwordField, password);
		waitForVisibility(confirmPassword);
		fillField(confirmPassword, password);
		return this;
	}

	@Step("And clicks on Register Button")
	public UserRegistrationPage clickOnRegisterButton(){
		waitForVisibility(registerBtn);
		Assert.assertTrue(elementDisplayed(registerBtn));
		clickButton(registerBtn);
		return this;
	}

	@Step("Then Success Message should be displayed")
	public UserRegistrationPage validateThatSuccessMessageShouldBeDisplayed(){
		//waitForVisibility(successMessage);
		//Assert.assertTrue(elementDisplayed(successMessage));
		Assert.assertTrue(getElementText(successMessage).contains("Your registration completed"));
		return this;
	}


}
