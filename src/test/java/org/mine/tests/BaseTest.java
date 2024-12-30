package org.mine.tests;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mine.pages.manager.PageObjectManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger("executionLogger");
    protected Properties properties;
    protected PageObjectManager pageObjectManager;
    String appUrl;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        //Initialize reporting tools, database connections.
        // Load configuration
        logger.info("Executing BeforeSuiteMethod . test tag name is--"+context.getName());
        loadConfig();
        String implicitWait=properties.getProperty("implicitwait");
        logger.info("Test Suite started");
        logger.info("implicitWait------"+implicitWait);

    }

    @BeforeTest
    public void beforeTest(ITestContext context){
        //Configure test environment, e.g., setting API base URLs or loading test data.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeTest . test tag name is--"+context.getName()+"  threadId is "+threadId);
        loadConfig();
//         appUrl=properties.getProperty("test.Url");
        appUrl=properties.getProperty("test.Url");
    }
    @BeforeClass
    public void beforeClass(){
        // - Prepare common test data for the class
       // Framework-Level Setup:configure shared utilities, dependency injection contexts, or mocks.
        //-Initialize static objects used across test methods.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeClass threadId is "+threadId);

    }
    @Parameters({"browser"})
    @BeforeMethod
    public void setUpBeforeMethod(@Optional("chrome") String browser,ITestContext context) {
        //Prepare preconditions for each test method.
        //initializing WebDriver, API client
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeMethod . test tag name is--"+context.getName()+" threadId is "+threadId);
        logger.info("Setting up WebDriver for browser: " + browser);

        // Initialize WebDriver based on browser parameter
        driver = createDriver(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        //for thread safety when running test methods in paralel
        pageObjectManager=new PageObjectManager(driver);

    }

    @AfterMethod
    public void tearDownAfterMethod() {
        //Method-level cleanup,
        //  Delete cookies, close WebDriver instances, or clear API client sessions.
        logger.info("Executing AfterMethod ");
        TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
        File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(sourceFile,new File("C:\\DATAFOLDER\\Udemy\\repo\\SCREENSHOTS\\failed.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
    }
    @AfterClass
    public void afterClass(){
        // class-level resources Clean up
        logger.info("Executing AfterClass ");
    }
    @AfterTest
    public void afterTest(){
        // .Clean up test environment.
        //Reset environment variables, resetting API base URLs, release test-level resources
        logger.info("Executing AfterTest ");
    }
    @AfterSuite
    public void afterSuite(){
        //Close database connections, generate reports
        logger.info("Executing AfterSuite ");
    }


    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return new FirefoxDriver();
            case "chrome":
            default:
                // Enable verbose logging
                System.setProperty("webdriver.chrome.verboseLogging", "true");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(options);
//                return new ChromeDriver();
        }
    }

    private void loadConfig() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader()
                    .getResourceAsStream("config/framework.properties"));
        } catch (Exception e) {
            logger.error("Error loading configuration", e);
        }
//        if (properties == null) {
//            properties = new Properties();
//            try (InputStream input = new FileInputStream("src/main/resources/config/framework.properties")) {
//                properties.load(input);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}
