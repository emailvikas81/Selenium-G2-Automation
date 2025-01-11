package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class CT_Common {
    public static void login(WebDriver driver, String userName) throws IOException {
        String envURL = Global_Common.getEnvironmentProperty("ct.environment.url") + "/main/Login.action";
        driver.get(envURL);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty(userName + ".password"));
        driver.findElement(By.name("submit")).click();
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception {
        driver.get(url);
        int locationFound = 0;
        for (int i = 1; i < 10; i++) {
            String labelFound = driver.findElement(By.xpath("/html/body/table[1]/tbody/tr[" + i + "]/td[1]")).getText();
            if (labelFound.equals("BuildNumber")) {
                locationFound = i;
                break;
            }
        }
        String buildNumber = driver.findElement(By.xpath("/html/body/table[1]/tbody/tr[" + locationFound + "]/td[2]")).getText();
        return buildNumber;
    }

}
