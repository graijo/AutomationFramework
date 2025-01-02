package org.mine.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mine.listeners.WebDriverEventListenerClass;
import org.mine.models.User;
import org.mine.pages.manager.PageObjectManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.jdbc.core.RowMapper;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger("executionLogger");
    protected Properties properties;
    protected PageObjectManager pageObjectManager;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "password";
    private Connection connection;
    String appUrl;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        //Initialize reporting tools, database connections.
        // Load configuration

        logger.info("Test Suite started");
        logger.info("Executing BeforeSuiteMethod . test tag name is--" + context.getName());

        clearAllureResults();
        setupDatabaseConnection();
        loadConfig("config/framework.properties");
        String implicitWait = properties.getProperty("implicitwait");
        logger.info("implicitWait------" + implicitWait);

    }

    @BeforeTest
    public void beforeTest(ITestContext context) {
        //Configure test environment, e.g., setting API base URLs or loading test data.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeTest . test tag name is--" + context.getName() + "  threadId is " + threadId);
        loadConfig("config/framework.properties");
//         appUrl=properties.getProperty("test.Url");
        appUrl = properties.getProperty("test.Url");
    }

    @BeforeClass
    public void beforeClass() {
        // - Prepare common test data for the class
        // Framework-Level Setup:configure shared utilities, dependency injection contexts, or mocks.
        //-Initialize static objects used across test methods.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeClass threadId is " + threadId);

    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setUpBeforeMethod(@Optional("chrome") String browser, ITestContext context) {
        //Prepare preconditions for each test method.
        //initializing WebDriver, API client
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeMethod . test tag name is--" + context.getName() + " threadId is " + threadId);
        logger.info("Setting up WebDriver for browser: " + browser);

        // Initialize WebDriver based on browser parameter
        driver = createDriver(browser);

        // Register WebDriver event listener
        // Wrap WebDriver in EventFiringWebDriver to listen to WebDriver events
        EventFiringWebDriver eventFiringDriver = new EventFiringWebDriver(driver);
        // Register your WebDriverEventListener
        WebDriverEventListenerClass eventListener = new WebDriverEventListenerClass();
        // Assign the eventFiringWebDriver to your original driver reference
        eventFiringDriver.register(eventListener);
        // Use eventFiringDriver instead of driver in your tests
        driver = eventFiringDriver;


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        //for thread safety when running test methods in paralel
        pageObjectManager = new PageObjectManager(driver);

    }

    @AfterMethod
    public void tearDownAfterMethod() {
        //Method-level cleanup,
        //  Delete cookies, close WebDriver instances, or clear API client sessions.
        logger.info("Executing AfterMethod ");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
    }

    @AfterClass
    public void afterClass() {
        // class-level resources Clean up
        logger.info("Executing AfterClass ");
    }

    @AfterTest
    public void afterTest() {
        // .Clean up test environment.
        //Reset environment variables, resetting API base URLs, release test-level resources
        logger.info("Executing AfterTest ");
    }

    @AfterSuite
    public void afterSuite() {
        //Close database connections, generate reports
        logger.info("Executing AfterSuite ");
        tearDownDataBaseConnection();
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

    private void loadConfig(String filePath) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader()
                    .getResourceAsStream(filePath));
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

    public WebDriver getDriver() {
        return driver;
    }

    //The allure-results directory is not cleared automatically before a new test run.
    //To need to clear it manually or configure your test execution process to do so.
    //Automating cleanup (via Maven, @BeforeSuite, or CI) is recommended to ensure consistency and prevent outdated results from being included in the report.
    public void clearAllureResults() {
        File allureResultsDir = new File("allure-results");
        if (allureResultsDir.exists()) {
            for (File file : allureResultsDir.listFiles()) {
                file.delete();
            }
            allureResultsDir.delete();
        }
    }

    public void setupDatabaseConnection() {

        loadConfig("config/database.properties");
         String DB_USER = properties.getProperty("DB_USER");
         logger.info("DB_USER is "+DB_USER);
        String DB_PASSWORD = properties.getProperty("DB_PASSWORD");
        logger.info("DB_PASSWORD is "+DB_PASSWORD);
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("SQLException is "+e);
        }
    }

    // Including executeQuery method in the base test class allows all tests to easily execute queries against the database.
    public List<User> executeQueryForUsers(String query) {
        List<User> users = new ArrayList<>(); //creates an empty list to store multiple objects of user-defined class User. We need this because a database query can return multiple rows, and each row will become a object (User class represents a data structure that maps to the database table) containing the corresponding data from that database row.
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));// getString() comes from the ResultSet class in java.sql package. It's a JDBC method to get data from database columns.
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public <T> List<T> executeQuery(String query, RowMapper<T> rowMapper) {
        List<T> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            int rowNum = 0; // Initialize row number counter
            while (rs.next()) {
                T item = rowMapper.mapRow(rs,rowNum++);
                results.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


    public void tearDownDataBaseConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
