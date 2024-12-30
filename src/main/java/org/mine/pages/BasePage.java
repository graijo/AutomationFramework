package org.mine.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger("executionLogger");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Set implicit wait
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); // Adjust timeout as needed
        PageFactory.initElements(driver, this);
    }

    // Common methods for all pages
    protected void waitForElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
            logger.error("Element not visible: " + element, e);
            throw e;
        }
    }

    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.debug("Clicked element: " + element);
        } catch (Exception e) {
            logger.error("Failed to click element: " + element, e);
            throw e;
        }
    }

    protected void sendKeys(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.clear();
            element.sendKeys(text);
            logger.debug("Entered text in element: " + element);
        } catch (Exception e) {
            logger.error("Failed to enter text in element: " + element, e);
            throw e;
        }
    }

    protected String getText(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            logger.debug("Got text from element: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + element, e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}