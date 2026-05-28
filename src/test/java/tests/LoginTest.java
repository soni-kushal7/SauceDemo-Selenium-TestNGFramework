package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("standard_user", "secret_sauce");
        loginPage.getLoginBtn().click();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Redirection failed.");
    }

    @Test
    public void lockedOutUserTest() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("locked_out_user", "secret_sauce");
        loginPage.getLoginBtn().click();
        
        Assert.assertTrue(driver.getPageSource().contains("Epic sadface"), "Error message not found");
    }
}
