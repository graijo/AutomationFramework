package org.mine.tests;

import org.mine.pages.AddToCartPage;
import org.mine.pages.PlaceOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest{
    AddToCartPage addToCartPage;
    PlaceOrderPage placeOrderPage;
    @Test(retryAnalyzer = org.mine.listeners.RetryAnalyzer.class)
    public void verify_placeOrderPage(){
//        appUrl=properties.getProperty("test.Url");
        driver.get(appUrl);
        driver.getTitle();
        Assert.assertEquals("GreenKart - veg and fruits kart",driver.getTitle(),"Title not matched");
        addToCartPage=pageObjectManager.getAddToCartPage();
        addToCartPage.searchAndAddProductToCart("Pumpkin - 1 Kg");
        addToCartPage.proceedToCheckOut();
        placeOrderPage=pageObjectManager.getplaceOrder();
        placeOrderPage.verifyQuantityandClickOnPlaceOrderButton();


    }


}
