package org.mine.listeners;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mine.tests.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNGListener  implements ITestListener {
    WebDriver driver; // Pass driver instance from your Base Test
    protected static final Logger logger = LogManager.getLogger("executionLogger");

    @Override
    @Step("Test Failure: {testName} -step defined ob above onTestFailure method of listner ")
    public void onTestFailure(ITestResult result) {
        logger.info("Test Failed: " + result.getName());
        Allure.step("Test Failed: " + result.getName());
        // Capture screenshot and attach to report
        Object testInstance = result.getInstance();
        this.driver = ((BaseTest) testInstance).getDriver();

        //save screenshot to local
         takeScreenshot(result.getName());
        //save screenshot to report-not working
        saveScreenshot(result.getName(),this.driver);
        //explicitly add attachment to Allure if above step failed
        Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(saveScreenshot(result.getName(),driver)));
        // Log failure details
        logger.info("Test Failed: " + result.getName());
        logger.info("Error: " + result.getThrowable().getMessage());
        //Integrate Allure with Log4j
        Allure.addAttachment("onTestFailure Log", "text/plain", "Logs for test from listner: " + result.getName());
    }

    //Screenshot to save locally
    private void takeScreenshot(String testName) {
        logger.info("Screenshot to capture for test: " + testName);
        try {
            // Add timestamp to the screenshot name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            //SimpleDateFormat: Used to format the timestamp in a yyyyMMdd_HHmmss format to ensure file names are unique and readable.
            // Date: Captures the current time when the screenshot is taken.

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File("./screenshots/" + testName + "_" + timestamp +  ".png");
            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot captured for test: " + testName);
        } catch (Exception e) {
            e.printStackTrace();
        }
//The screenshots will be saved in a "screenshots" folder in your project's root directory.
// If you don't see it, you need to:
//Create a "screenshots" directory in your project root
//Or modify the path in your code to an existing directory
//The "./" in the path means "current directory". You can use an absolute path instead:
    }

    //Screenshot for report
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private byte[]   saveScreenshot(String testName,WebDriver driver) {
        try {
            if (driver == null) {
                logger.warn("Driver is null, screenshot cannot be captured.");
                return null;
            }
            logger.info("Screenshot to capture for test: " + testName + " for report");

            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot " + testName + " for report :" + e.getMessage());
            return new byte[0];
        }
    }
    @Override
    @Step
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
        Allure.step("Test Passed: " + result.getName());
        Allure.addAttachment("Log", "text/plain", "Logs for test: " + result.getName());

    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
        Allure.step("Starting test: " + context.getName());
        Allure.addAttachment("Log", "text/plain", "Logs for test: " + context.getName());

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
        Allure.step("Test Suite Finished: " + context.getName());
        Allure.addAttachment("Log", "text/plain", "Logs for test: " + context.getName());
    }
}