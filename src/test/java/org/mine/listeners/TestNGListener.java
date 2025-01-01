package org.mine.listeners;
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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNGListener  implements ITestListener {
    WebDriver driver; // Pass driver instance from your Base Test
    protected static final Logger logger = LogManager.getLogger("executionLogger");

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Test Failed: " + result.getName());
        // Capture screenshot and attach to report
        Object testInstance = result.getInstance();
        this.driver = ((BaseTest) testInstance).getDriver();

        takeScreenshot(result.getName());

        // Log failure details
        System.out.println("Test Failed: " + result.getName());
        System.out.println("Error: " + result.getThrowable().getMessage());
    }

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

        //for report
//        @Attachment(value = "Screenshot", type = "image/png")
//        private byte[] saveScreenshot(WebDriver driver) {
//            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
    }
}