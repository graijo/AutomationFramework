package org.mine.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AddToCartPage extends BasePage {

    //CamelCase Naming Convention is Common in Java.
    // Using underscores can lead to longer names, which might feel verbose.
    //Syntax: locatorPageCategoryNameForLocatorTypeOfElement
    //Example: homeLocatorSearchBoxInput

    @FindBy(xpath = "//input[contains(@class,\"search\")]")
    WebElement homeLocatorSearchBoxInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    WebElement homeLocatorSearchButton;

    @FindBy(xpath = "//h4[@class=\"product-name\"]")
    WebElement homeLocatorProductName;

    @FindBy(xpath = "//a[text()=\"+\"]")
    WebElement homeLocatorProductCountIncrementInput;

    @FindBy(xpath = "//button[text()=\"ADD TO CART\"]")
    WebElement homeLocatorAddtoCartButton;

    @FindBy(xpath = "//img[@alt=\"Cart\"]")
    WebElement homeLocatorCartButton;

//    @FindBy(xpath = "//button[text()='PROCEED TO CHECKOUT']")
//    WebElement homeLocatorProceedToCheckOutButton;

    @FindBy(xpath = "//button[text()='PROCEED TO CHECKOUT']/..")
    WebElement homeLocatorProceedToCheckOutButton;


    public AddToCartPage(WebDriver driver) {
        super(driver);
    }


    public void searchAndAddProductToCart(String search)  {
        click(homeLocatorSearchBoxInput);
        sendKeys(homeLocatorSearchBoxInput,search);
        click(homeLocatorSearchButton);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(getText(homeLocatorProductName),search,"Product name is not correct");
        click(homeLocatorProductCountIncrementInput);
        click(homeLocatorAddtoCartButton);
    }

    public void proceedToCheckOut(){
        waitForElementVisible(homeLocatorCartButton);
        click(homeLocatorCartButton);
        waitForElementVisible(homeLocatorProceedToCheckOutButton);
        click(homeLocatorProceedToCheckOutButton);
        click(homeLocatorProceedToCheckOutButton);

    }

//    public void login(String username, String password) {
//        sendKeys(usernameInput, username);
//        sendKeys(passwordInput, password);
//        click(loginButton);
//    }
}
