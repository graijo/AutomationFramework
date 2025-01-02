package org.mine.tests;

import io.qameta.allure.*;
import org.mine.pages.AddToCartPage;
import org.mine.pages.PlaceOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest{
    AddToCartPage addToCartPage;
    PlaceOrderPage placeOrderPage;
    @Test(retryAnalyzer = org.mine.listeners.RetryAnalyzer.class)
    @Step("addToCartPageTest main step")
    @Epic("addToCartPageTest")
    @Feature("verify_addToCartPage")
    @Story("Successful verification of addToCartPage")
    @Description("Validates Successfully verification of addToCartPage")
    public void verify_addToCartPage(){
//        appUrl=properties.getProperty("test.Url");
        Allure.step("open browser");
        driver.get(appUrl);
        Allure.step("get title");
        driver.getTitle();
        Allure.step("Assert"+" "+"\"GreenKart - veg and fruits kart\""+" "+driver.getTitle());
        Assert.assertEquals("GreenKart - veg and fruits kart",driver.getTitle(),"Title not matched");
        Allure.step("starting getAddToCartPage method");
        addToCartPage=pageObjectManager.getAddToCartPage();
        addToCartPage.searchAndAddProductToCart("Pumpkin - 1 Kg");
        addToCartPage.proceedToCheckOut();


    }


}
