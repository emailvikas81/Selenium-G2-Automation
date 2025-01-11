package com.vertafore.functional;

import com.vertafore.common.CommonMethods;
import com.vertafore.common.Global_Common;
import com.vertafore.pageobjects.HomePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;

public class HomePageTest {
    private WebDriver driver;
    String username = Global_Common.getEnvironmentProperty("functionaltest.user");
    String accountID = Global_Common.getEnvironmentProperty("functionaltest.accountID");

    @Before
    public void setUp() throws Exception {
        driver = Global_Common.loadWebDriver();
        driver.get(CommonMethods.getLoginUrl(accountID));
        CommonMethods.login(driver, accountID, username);
    }

    @Test
    public void informationalMessagesTest(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> alertTitles = homePage.getAlertTitles();
        assertEquals(alertTitles.size(), 5);
        assertEquals(alertTitles.get(0).getText(), "G2 Notifications");
        assertEquals(alertTitles.get(1).getText(), "State News Updates");
        assertEquals(alertTitles.get(2).getText(), "Regulatory Changes");
        assertEquals(alertTitles.get(3).getText(), "Release Notes");
        assertEquals(alertTitles.get(4).getText(), "Support Aids");
    }
}
