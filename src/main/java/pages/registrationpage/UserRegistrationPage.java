package pages.registrationpage;

import driverfactory.Webdriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class UserRegistrationPage
{

	private final Webdriver driver;

	By genderMaleRadioBtn = By.id("gender-male");
	By firstName = By.id("FirstName");
	By lastName = By.id("LastName");
	By emailField = By.id("Email");
	By passwordField = By.id("Password");
	By confirmPassword = By.id("ConfirmPassword");
	By registerBtn = By.id("register-button");
	By successMessage = By.cssSelector("div.result");

	By continueBtn = By.cssSelector("a.button-1.register-continuebutton");

	public UserRegistrationPage(Webdriver driver) {
		this.driver = driver;
	}

	@Step("Then User should be Navigated to Registration page")
	public UserRegistrationPage validateThatUserNavigatedToRegistrationPage(){
		driver.assertThat().browser().url().contains("register");
		//Assert.assertTrue(driver.browser().getCurrentURL().contains("register"));
		return this;
	}

	@Step("When he fills registration form")
	public UserRegistrationPage fillUserRegistrationForm(String firstname, String lastname, String email, String password) {
		driver.element().click(genderMaleRadioBtn);
		driver.element().waitForVisibility(firstName);
		driver.element().fillField(firstName, firstname);
		driver.element().waitForVisibility(lastName);
		driver.element().fillField(lastName, lastname);
		driver.element().waitForVisibility(emailField);
		driver.element().clearField(emailField);
		driver.element().fillField(emailField, email);
		driver.element().waitForVisibility(passwordField);
		driver.element().fillField(passwordField, password);
		driver.element().waitForVisibility(confirmPassword);
		driver.element().fillField(confirmPassword, password);
		return this;
	}

	@Step("And clicks on Register Button")
	public UserRegistrationPage clickOnRegisterButton(){
		driver.element().waitForVisibility(registerBtn);
		driver.assertThat().element(registerBtn).isDisplayed();
		driver.element().click(registerBtn);
		return this;
	}

	@Step("Then Success Message should be displayed")
	public UserRegistrationPage validateThatSuccessMessageShouldBeDisplayed(){
		driver.element().waitForVisibility(continueBtn);
		driver.assertThat().element(continueBtn).isDisplayed();
		//Assert.assertTrue(elementDisplayed(successMessage));
		//Assert.assertTrue(getElementText(successMessage).contains("Your registration completed"));
		return this;
	}


}
