package org.mine.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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

public class BaseAPITest {
    protected static final Logger logger = LogManager.getLogger("executionLogger");
    protected Properties properties;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
    private Connection connection;
    protected  RequestSpecification requestSpec;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        //Initialize reporting tools, database connections.
        // Load configuration

        logger.info("Test Suite started");
        logger.info("Executing BeforeSuiteMethod . test tag name is--" + context.getName());

        clearAllureResults();
        setupDatabaseConnection();

    }

    @BeforeTest
    public void beforeTest(ITestContext context) {
        //Configure test environment, e.g., setting API base URLs or loading test data.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeTest . test tag name is--" + context.getName() + "  threadId is " + threadId);
        RestAssured.baseURI = "https://reqres.in/";

    }

    @BeforeClass
    public void beforeClass() {
        // - Prepare common test data for the class
        // Framework-Level Setup:configure shared utilities, dependency injection contexts, or mocks.
        //-Initialize static objects used across test methods.
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeClass threadId is " + threadId);

    }

    @BeforeMethod
    public void setUpBeforeMethod(ITestContext context) {
        //Prepare preconditions for each test method.
        //initializing WebDriver, API client
        long threadId = Thread.currentThread().getId();
        logger.info("Executing BeforeMethod . test tag name is--" + context.getName() + " threadId is " + threadId);
        // Basic request setup
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/")
                .setContentType("application/json")
                .build();

    }

    @AfterMethod
    public void tearDownAfterMethod() {
        //Method-level cleanup,
        //  Delete cookies, close WebDriver instances, or clear API client sessions.

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




    private void loadConfig(String filePath) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader()
                    .getResourceAsStream(filePath));
        } catch (Exception e) {
            logger.error("Error loading configuration", e);
        }

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
