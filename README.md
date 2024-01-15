# AUTOTESTIMATIC, A Selenium JAVA Test Automation Framework
- This project is an open-source Test automation Framework that allows you to perform multiple actions to test a web application's functionality, behaviour, 
which provides easy to use syntax, and easy to setup environment according to the needed requirements for testing
- This project is based on Selenium WebDriver, TestNG Runner, and Maven


## Features
### Driver Initialization:
- Support Running Testing on Following Browsers locally: Chrome, Firefox, & Edge either Normal or Headless
- Support Cross-Browsing Mode
- Support Running on Selenium Grid via Docker, with starting Grid automatically along with test execution, i.e: no need to be started separately in the terminal, just Docker engine should be up and running
- Support Running on BrowserStack
- Initializing Driver in a Decorated way

### Test Execution
- Can run Tests via Maven Commands
- Handling running Tests via TestNG XML configuration files
- Support TestNG Runner with Implementing custom Listener
- Support JUnit5 Jupiter with Implementing custom Listener
- Handling Test Runner Detection via Google AutoService API, So NO NEED to then Add `tools.listeners.testng.TestNGListener` to TestNG Configuration
- Handling Retry Failed Tests via TestNG Retry Analyzer

### Reporting
- Generate Allure Report automatically after Test Execution with screenshots
- Generate Extent Report automatically after Test Execution with screenshots
- Auto Cleaning Reports before Execution

### Framework Configurations
- Changing Framework Configurations via Properties files
- Also, Changing Framework Configurations programmatically
- Generating Properties Files with Default Settings if not exists

## Tech Stack
- Java
- Maven
- TestNG
- JUnit5 Jupiter
- Selenium WebDriver
- Allure Reports
- Extent Reports
- Docker for Setting up Selenium Grid
- Jenkins

## How to use:

### Step 1: Setup Project
- Create a new Java/Maven project using Eclipse, IntelliJ or your favourite IDE
- Copy the highlighted contents of this [pom.xml](https://github.com/mohammedtaher95/testJARProject/blob/master/pom.xml#L15-L255) into yours inside `<project>` tag


### Step 2: Create a new Test Class
- Create a new Package "tests" under src/test/java and create a new Java Class TestClass under that package.
- Copy the following imports into your Test Class
```
import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;
import org.testng.annotations.*;
```
- Copy the Following snippet to your Test Class Body:
```
    driverfactory.Webdriver driver;
    By registerLink = By.linkText("Register");

    @BeforeClass
    public void setUp() throws IOException {
        driver = new WebDriver();
    }

    @Test
    public void testMethod(){

        driver.browser().navigateToURL("http://demo.nopcommerce.com");
        driver.element().clickButton(registerLink);
        driver.assertThat().browser().url().contains("register");
    }

    @AfterClass
    public void tearDown() throws IOException {
        driver.quit();
    }
```
  
### Step 3: Running Tests
- Run your TestClass.java as a TestNG Test Class.
- The execution report will open automatically in your default web browser after the test run is completed
- After Running your test, properties files will be generated automatically in the following directory
  `src\main\resources\properties`, so you can edit them according to the needed options you want to use
