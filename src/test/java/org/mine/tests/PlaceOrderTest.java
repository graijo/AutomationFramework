package org.mine.tests;


import io.qameta.allure.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;


import org.mine.pages.AddToCartPage;
import org.mine.pages.PlaceOrderPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

@Epic("PlaceOrderTest")
@Feature("Verify product count and click on place order")
@Story("Successful check product count and click on place order")
public class PlaceOrderTest extends BaseTest{
    AddToCartPage addToCartPage;
    PlaceOrderPage placeOrderPage;
    @Test(retryAnalyzer = org.mine.listeners.RetryAnalyzer.class)
//    @Step("PlaceOrderTest step")
     @Description("Validates Successfully checked product count and click on place order.Verify product count and click on place order.")
    public void verify_placeOrderPage(ITestContext iTestContext){
//        appUrl=properties.getProperty("test.Url");
        Allure.step("open browser");
        driver.get(appUrl);
        logger.info("app url opened is -"+appUrl);
        //the below is to attach logs-which is not suitable like showing step using Allure.step
        Allure.addAttachment("Log from a @test method", "text/plain", "1st Log for test: " +appUrl);
        Allure.step("get title");
        driver.getTitle();
        logger.info(driver.getTitle());
        Allure.step("Assert"+" "+"\"GreenKart - veg and fruits kart\""+" "+driver.getTitle());
        Assert.assertEquals("GreenKart - veg and fruits kart",driver.getTitle(),"Title not matched");
        Allure.step("starting getAddToCartPage method");
        addToCartPage=pageObjectManager.getAddToCartPage();
        Allure.step("starting searchAndAddProductToCart method with \"Pumpkin - 1 Kg\"");
        addToCartPage.searchAndAddProductToCart("Pumpkin - 1 Kg");
        Allure.step("starting proceedToCheckOut method ");
        addToCartPage.proceedToCheckOut();
        Allure.step("starting getplaceOrder method ");
        placeOrderPage=pageObjectManager.getplaceOrder();
        Allure.step("starting verifyQuantityandClickOnPlaceOrderButton method ");
        placeOrderPage.verifyQuantityandClickOnPlaceOrderButton();


    }


}
