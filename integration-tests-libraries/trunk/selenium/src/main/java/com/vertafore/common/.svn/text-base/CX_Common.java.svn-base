package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class CX_Common {

    public static void login(WebDriver driver, String accountID, String userName) throws IOException {
        Agency_Common.login(driver, accountID, userName);
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception {
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("/html/body/div[1]/b[" + i + "]")).getText();
            if(labelFound.equals("Build Number:")) {
                locationFound = i;
                break;
            }
        }
        String buildNumber = driver.findElement(By.xpath("/html/body/div[1]/i[" + locationFound + "]")).getText();
        return buildNumber;
    }

    public static String infoVersionServerNumber(WebDriver driver, String url) throws Exception{
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("/html/body/div[1]/b[" + i + "]")).getText();
            if(labelFound.equals("Server:")) {
                locationFound = i;
                break;
            }
        }
        String serverNumber = driver.findElement(By.xpath("/html/body/div[1]/i[" + locationFound + "]")).getText();
        return serverNumber;
    }
}
