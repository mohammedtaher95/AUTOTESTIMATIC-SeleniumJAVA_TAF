package tests;

import driverfactory.Webdriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.homepage.HomePage;
import utilities.UserFormData;


public class MyAccountTest{

	public static ThreadLocal<driverfactory.Webdriver> driver;
	UserFormData user = new UserFormData();

	@BeforeClass(description = "Setup Driver")
	public synchronized void setUp(){
		driver = new ThreadLocal<>();
		driver.set(new Webdriver());
	}
	
	@Test(priority = 1)
	public void UserCanRegisterSuccessfully()
	{
		new HomePage(driver.get().makeAction())
				.openRegistrationPage()
				.validateThatUserNavigatedToRegistrationPage()
				.fillUserRegistrationForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getOldPassword())
				.clickOnRegisterButton()
				.validateThatSuccessMessageShouldBeDisplayed();
	}

	@Test(priority = 2, dependsOnMethods = {"UserCanRegisterSuccessfully"})
	public void RegisteredUserCanLogin()
	{
		new HomePage(driver.get().makeAction())
				.openLoginPage()
				.userLogin(user.getEmail(), user.getOldPassword())
				.clickOnLoginButton()
				.checkThatLogoutButtonShouldBeDisplayed();
	}
//
//	@Test(priority = 3, dependsOnMethods = {"RegisteredUserCanLogin"})
//	public void RegisteredUserCanChangePassword()
//	{
//		new HomePage(Webdriver)
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
//		new HomePage(Webdriver)
//				.openLoginPage()
//				.userLogin(user.getEmail(), user.getNewPassword())
//				.clickOnLoginButton()
//				.checkThatLogoutButtonShouldBeDisplayed();
//	}

	@AfterClass(description = "Tear down")
	public void tearDown(){
		driver.get().quit();
		driver.remove();
	}
}
