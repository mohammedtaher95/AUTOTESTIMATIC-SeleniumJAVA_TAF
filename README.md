# Selenium JAVA Test Automation Framework
- This project is an open-source Test automation Framework that allows you to perform multiple actions to test a web application's functionality, behaviour, 
which provides easy to use syntax, and easy to setup environment according to the needed requirements for testing
- This project is based on Selenium WebDriver, TestNG Runner, and Maven


## Features
- Support Running Testing on Following Browsers: Chrome, Firefox, & Edge either Normal or Headless
- Support Cross-Browsing Mode
- Support Running on Selenium Grid
- Generate Allure Report automatically after Test Execution with screenshots

## How to use:
### Option 1: Using this Framework directly
- Pull this repository and use it directly to write your tests

### Option 2: Create New Project
#### Step 1: Setup Project
- Temporary step: pull this repository, and run `mvn clean install` in your favorite IDE to create maven dependency 
  on your local Maven Repository, "This step is temporary till uploading this project to Maven Central"
- Create a new Java/Maven project using Eclipse, IntelliJ or your favourite IDE
- Copy the highlighted contents of this [pom.xml](https://github.com/mohammedtaher95/testJARProject/blob/0d10e995b21505cc18e3f65bddf9ba35ef8f035b/pom.xml#L15-L120) into yours inside `<project>` tag
- Click on Run dropdown menu and then select Edit Configuration


#### Step 2: Create a new Test Class
- Create a new Package "tests" under src/test/java and create a new Java Class TestClass under that package.
- Copy the following imports into your Test Class
```
import driverFactory.Webdriver;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static browserActions.BrowserActions.*;
import static elementActions.ElementActions.*;
```
- Copy the Following snippet to your Test Class Body:
```
    Webdriver driver;
    By registerLink = By.linkText("Register");

    @BeforeClass
    public void setUp() throws IOException {
        driver = new Webdriver();
    }

    @Test
    public void testMethod(){

        navigateToURL("http://demo.nopcommerce.com");
        clickButton(registerLink);
        Assert.assertTrue(getCurrentURL().contains("register"));

    }

    @AfterClass
    public void tearDown() throws IOException {
        driver.quit();
    }
```
  
#### Step 3: Running Tests
- Run your TestClass.java as a TestNG Test Class.
- The execution report will open automatically in your default web browser after the test run is completed
- After Running your test, propeties files will be generated automatically in the following directory
  `src\main\resources\properties`, so you can edit them according to the needed options you want to use
