package tests;

import driverfactory.Webdriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.homepage.HomePage;
import pages.registrationpage.UserRegistrationPage;
import utilities.UserFormData;

import static driverfactory.Webdriver.getDriver;

public class MyAccountTest extends TestBase{
	
	HomePage homeObject;
	UserFormData user = new UserFormData();
	
	@Test(priority = 1)
	public void UserCanRegisterSuccessfully()
	{

		homeObject = new HomePage(Webdriver.getDriver());
		homeObject.openRegistrationPage();
		new UserRegistrationPage(Webdriver.getDriver())
				.validateThatUserNavigatedToRegistrationPage()
				.fillUserRegistrationForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOldPassword())
				.clickOnRegisterButton()
				.validateThatSuccessMessageShouldBeDisplayed();
	}

	@Test(priority = 2, dependsOnMethods = {"UserCanRegisterSuccessfully"})
	public void RegisteredUserCanLogin()
	{
		homeObject.openLoginPage();
		new LoginPage(getDriver())
				.userLogin(user.getEmail(), user.getOldPassword())
				.clickOnLoginButton()
				.checkThatLogoutButtonShouldBeDisplayed();
	}
//
//	@Test(priority = 3, dependsOnMethods = {"RegisteredUserCanLogin"})
//	public void RegisteredUserCanChangePassword()
//	{
//		new HomePage(Webdriver.getDriver())
//				.openMyAccountPage()
//				.openChangePasswordpage()
//				.changePassword(user.getOldPassword(), user.getNewPassword())
//				.clickOnConfirm()
//				.checkThatChangeMessageShouldBeDisplayed()
//				.closeMessage()
//				.clickOnLogoutButton();
//	}
//
//	@Test(priority = 4, dependsOnMethods = {"RegisteredUserCanLogin"})
//	public void UserLoginWithNewPassword()
//	{
//		homeObject.openLoginPage();
//		new LoginPage(getDriver())
//				.userLogin(user.getEmail(), user.getNewPassword())
//				.clickOnLoginButton()
//				.checkThatLogoutButtonShouldBeDisplayed();
//	}

}
