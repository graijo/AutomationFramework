package org.mine.tests;

import org.mine.pages.AddToCartPage;
import org.mine.pages.PlaceOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest{
    AddToCartPage addToCartPage;
    PlaceOrderPage placeOrderPage;
    @Test
    public void verify_addToCartPage(){
//        appUrl=properties.getProperty("test.Url");
        driver.get(appUrl);
        driver.getTitle();
        Assert.assertEquals("GreenKart - veg and fruits kart",driver.getTitle(),"Title not matched");
        addToCartPage=pageObjectManager.getAddToCartPage();
        addToCartPage.searchAndAddProductToCart("Pumpkin - 1 Kg");
        addToCartPage.proceedToCheckOut();


    }


}
