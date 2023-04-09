package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.homepage.HomePage;
import utilities.UserFormData;


public class MyAccountTest extends TestBase{

	UserFormData user = new UserFormData();
	
	@Test(priority = 1)
	public void UserCanRegisterSuccessfully()
	{
		new HomePage(Webdriver.getDriver())
				.openRegistrationPage()
				.validateThatUserNavigatedToRegistrationPage()
				.fillUserRegistrationForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOldPassword())
				.clickOnRegisterButton()
				.validateThatSuccessMessageShouldBeDisplayed();
	}

	@Test(priority = 2, dependsOnMethods = {"UserCanRegisterSuccessfully"})
	public void RegisteredUserCanLogin()
	{
		new HomePage(Webdriver.getDriver())
				.openLoginPage()
				.userLogin(user.getEmail(), user.getOldPassword())
				.clickOnLoginButton()
				.checkThatLogoutButtonShouldBeDisplayed();
	}

	@Test(priority = 3, dependsOnMethods = {"RegisteredUserCanLogin"})
	public void RegisteredUserCanChangePassword()
	{
		new HomePage(Webdriver.getDriver())
				.openMyAccountPage()
				.openChangePasswordpage()
				.changePassword(user.getOldPassword(), user.getNewPassword())
				.clickOnConfirm()
				.checkThatChangeMessageShouldBeDisplayed()
				.closeMessage()
				.clickOnLogoutButton();
	}

	@Test(priority = 4, dependsOnMethods = {"RegisteredUserCanLogin"})
	public void UserLoginWithNewPassword()
	{
		new HomePage(Webdriver.getDriver())
				.openLoginPage()
				.userLogin(user.getEmail(), user.getNewPassword())
				.clickOnLoginButton()
				.checkThatLogoutButtonShouldBeDisplayed();
	}

}
