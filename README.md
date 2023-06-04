# Selenium JAVA Test Automation Framework
- This project is an open-source Test automation Framework that allows you to perform multiple actions to test a web application's functionality, behaviour, 
which provides easy to use syntax, and easy to setup environment according to the needed requirements for testing
- This project is based on Selenium WebDriver, TestNG Runner, and Maven


## Features
- Support Running Testing on Following Browsers locally: Chrome, Firefox, & Edge either Normal or Headless
- Support Cross-Browsing Mode
- Support Running on Selenium Grid via Docker 
- Generate Allure Report automatically after Test Execution with screenshots
- Changing Framework settings via Properties files

## Tech Stack
- Java
- Maven
- TestNG
- Selenium WebDriver
- Allure
- Docker for Setting up Selenium Grid
- Jenkins

## How to use:

### Step 1: Setup Project
- Create a new Java/Maven project using Eclipse, IntelliJ or your favourite IDE
- Copy the highlighted contents of this [pom.xml](https://github.com/mohammedtaher95/testJARProject/blob/9905f207dfa95ce1d44b92cc574ead9852064d10/pom.xml#L15-L126) into yours inside `<project>` tag
- Click on Run dropdown menu and then select Edit Configuration


### Step 2: Create a new Test Class
- Create a new Package "tests" under src/test/java and create a new Java Class TestClass under that package.
- Copy the following imports into your Test Class
```
import driverfactory.Webdriver;
import org.openqa.selenium.By;
import org.testng.annotations.*;
```
- Copy the Following snippet to your Test Class Body:
```
    driverfactory.Webdriver driver;
    By registerLink = By.linkText("Register");

    @BeforeClass
    public void setUp() throws IOException {
        driver = new Webdriver();
    }

    @Test
    public void testMethod(){

        driver.browser().navigateToURL("http://demo.nopcommerce.com");
        driver.element().clickButton(registerLink);
        Assert.assertTrue(driver.browser().getCurrentURL().contains("register"));

    }

    @AfterClass
    public void tearDown() throws IOException {
        driver.quit();
    }
```
  
### Step 3: Running Tests
- Run your TestClass.java as a TestNG Test Class.
- The execution report will open automatically in your default web browser after the test run is completed
- After Running your test, propeties files will be generated automatically in the following directory
  `src\main\resources\properties`, so you can edit them according to the needed options you want to use
