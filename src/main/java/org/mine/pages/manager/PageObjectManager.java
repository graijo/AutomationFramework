package org.mine.pages.manager;


import org.mine.pages.AddToCartPage;
import org.mine.pages.PlaceOrderPage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private WebDriver driver;
    private AddToCartPage addToCartPage;
    private PlaceOrderPage placeOrderPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public AddToCartPage getAddToCartPage() {
        if (addToCartPage == null) {
            addToCartPage = new AddToCartPage(driver);
        }
        return addToCartPage;
    }

    public PlaceOrderPage getplaceOrder() {
        if (placeOrderPage == null) {
            placeOrderPage = new PlaceOrderPage(driver);
        }
        return placeOrderPage;
    }
}
