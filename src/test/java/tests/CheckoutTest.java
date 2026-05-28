package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutStepOnePage;
import pages.CheckoutStepTwoPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutTest extends BaseTest {

    @Test
    public void fullCheckoutTest() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        loginPage.getLoginBtn().click();
        
        InventoryPage inventoryPage = new InventoryPage(driver);
        Thread.sleep(1000);
        inventoryPage.clickAddToCartBackpack();
        Thread.sleep(500);
        inventoryPage.clickCartLink();
        
        CartPage cartPage = new CartPage(driver);
        Thread.sleep(1000);
        cartPage.clickCheckout();
        
        CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage(driver);
        Thread.sleep(1000);
        stepOnePage.fillDetailsAndContinue("Kushal", "Verma", "202028");
        
        CheckoutStepTwoPage stepTwoPage = new CheckoutStepTwoPage(driver);
        Thread.sleep(1000);
        stepTwoPage.clickFinish();
        
        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Thread.sleep(1000);
        Assert.assertEquals(completePage.getCompleteMessage(), "Thank you for your order!");
        
        completePage.clickBackHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
