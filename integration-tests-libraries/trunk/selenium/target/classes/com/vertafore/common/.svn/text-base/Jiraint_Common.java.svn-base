package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;


public class Jiraint_Common {

    public static void loginJira4(WebDriver driver, String jira4UserName) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 90);

        //login to Jira 4
        String envURL = Global_Common.getEnvironmentProperty("jira4.base.url");
        driver.get(envURL);
        String windowId = driver.getWindowHandle();
        driver.switchTo().frame("gadget-0");
        driver.findElement(By.id("usernameinput")).sendKeys(jira4UserName);
        driver.findElement(By.id("os_password")).sendKeys(Global_Common.getEnvironmentProperty("jira4." + jira4UserName + ".pw"));
        driver.findElement(By.id("login")).click();
        driver.switchTo().window(windowId);
    }

    public static void loginDJ(WebDriver driver, String jiraUserName) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 90);

        //login to DJ
        String envURL = Global_Common.getEnvironmentProperty("jira.base.url");
        driver.get(envURL);
        driver.switchTo().frame("gadget-0");
        driver.findElement(By.name("os_username")).sendKeys(jiraUserName);
        driver.findElement(By.name("os_password")).sendKeys(Global_Common.getEnvironmentProperty("jira." + jiraUserName + ".pw"));
        driver.findElement(By.id("login")).click();

        Thread.sleep(5000);

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='gadget-10002-title']")));
    }
}
