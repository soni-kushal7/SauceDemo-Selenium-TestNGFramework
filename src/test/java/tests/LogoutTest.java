package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.InventoryPage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    @Test
    public void successfulLogoutTest() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        loginPage.getLoginBtn().click();
        
        InventoryPage inventoryPage = new InventoryPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.visibilityOf(inventoryPage.getMenuBtn()));
        inventoryPage.clickMenu();
        
        wait.until(ExpectedConditions.elementToBeClickable(inventoryPage.getLogoutLink()));
        inventoryPage.clickLogout();
        
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Logout failed.");
    }
}
