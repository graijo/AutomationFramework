package org.mine.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PlaceOrderPage extends BasePage{

    @FindBy(xpath = "///p[@class='quantity']")
    WebElement homeLocatorQuantityText;

    @FindBy(xpath = "//button[text()='Place Order']")
    WebElement homeLocatorPlaceOrderButton;

    public PlaceOrderPage(WebDriver driver) {
        super(driver);
    }
    public void verifyQuantityandClickOnPlaceOrderButton(){
        waitForElementVisible(homeLocatorQuantityText);
        Assert.assertEquals(getText(homeLocatorQuantityText),"3","Quantity is not correct");
        click(homeLocatorPlaceOrderButton);

    }

}
