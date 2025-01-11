package com.vertafore.pageobjects;

import com.vertafore.common.Global_Common;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage {
    private WebDriver driver;
//Logged In User First and Last Name
    @FindBy(how= How.XPATH, using="id(\"fixedHeader\")/table[1]/tbody[1]/tr[3]/td[1]/strong[1]")
    private WebElement userFirstLastNameLoggedIn;
//Logged In User First and Last Name Exists
    @FindBy(how= How.XPATH, using="id(\"fixedHeader\")/table[1]/tbody[1]/tr[3]/td[1]/strong[1]")
    private List<WebElement> userFirstLastNameLoggedInExists;
//Alert Titles
    @FindBy(how= How.XPATH, using="//div[@class='alertTitle']")
    private List<WebElement> alertTitles;

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public List<WebElement> getAlertTitles() {
        return alertTitles;
    }

    public String getUserFirstLastNameLoggedIn() {
        Global_Common.setDriverTimeout(driver, 1, TimeUnit.SECONDS);
        if (userFirstLastNameLoggedInExists.size() > 0){
            Global_Common.setDriverTimeout(driver, 90, TimeUnit.SECONDS);
            return userFirstLastNameLoggedIn.getText();
        }else {
            Global_Common.setDriverTimeout(driver, 90, TimeUnit.SECONDS);
            return "";
        }
    };
}
