package com.vertafore.pageobjects;

import com.vertafore.common.Global_Common;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginPage {
    private WebDriver driver;
//User Name
    @FindBy(how= How.NAME, using="ssousername")
    private WebElement usernameField;
//Password
    @FindBy(how=How.NAME, using="password")
    private WebElement passwordField;
//Login Button
    @FindBy(how=How.XPATH, using="//*[@id='Table2']/tbody/tr/td/form/div/table[1]/tbody/tr[5]/td[2]/a")
    private WebElement loginButton;
//Login Error
    @FindBy(how=How.ID, using="valerrors")
    private WebElement loginErrorMessage;
//Login Error Exists
    @FindBy(how=How.ID, using="valerrors")
    private List<WebElement> loginErrorMessageExists;
//Request Timeout Error
    @FindBy(how=How.ID, using="errorTitle")
    private WebElement requestTimeoutError;
//Request Timeout Error Exists
    @FindBy(how=How.ID, using="errorTitle")
    private List<WebElement> requestTimeoutErrorExists;
//Password Expiration Warning
    @FindBy(how=How.CLASS_NAME, using="PageTitle")
    private WebElement passwordExpirationWarning;
//Password Expiration Warning Exists
    @FindBy(how=How.CLASS_NAME, using="PageTitle")
    private List<WebElement> passwordExpirationWarningExists;
//Continue with Login Link
    @FindBy(how=How.LINK_TEXT, using="Continue with Login")
    private WebElement continueWithLoginLink;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public Boolean getRequestTimeoutErrorExists() {
        Global_Common.setDriverTimeout(driver, 1, TimeUnit.SECONDS);
        if(requestTimeoutErrorExists.size() > 0){
            Global_Common.setDriverTimeout(driver, 90, TimeUnit.SECONDS);
            return true;
        }else{
            Global_Common.setDriverTimeout(driver, 90, TimeUnit.SECONDS);
            return false;
        }
    }

    public void login(String accountID, String username) {
        String password = Global_Common.getEnvironmentProperty("accountID." + accountID + "." + username + ".password");
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.loginButton.click();
        Global_Common.setDriverTimeout(driver, 1, TimeUnit.SECONDS);
        if (passwordExpirationWarningExists.size() > 0){
            continueWithLoginLink.click();
        }
        Global_Common.setDriverTimeout(driver, 90, TimeUnit.SECONDS);
    }

    public String retrieveLoginFailureReason() {
        Global_Common.setDriverTimeout(driver, 1, TimeUnit.SECONDS);
        if (loginErrorMessageExists.size() > 0){
            return this.loginErrorMessage.getText();
        }else {
            return "";
        }
    }

}
