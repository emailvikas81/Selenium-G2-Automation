package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class Agency_Common {

    public static void login(WebDriver driver, String accountID, String userName) throws IOException {
        String envURL = Global_Common.getEnvironmentProperty("cx.environment.url") + "/login.html";
        driver.get(envURL);
        driver.findElement(By.name("accountId")).sendKeys(accountID);
        driver.findElement(By.name("loginName")).sendKeys(userName);
        WebElement formElement = driver.findElement(By.name("loginForm"));
        formElement.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty("accountID." + accountID + "." + userName + ".password"));
        String environment = System.getenv("ENVIRONMENT");
        driver.findElement(By.xpath("//*[@id='loginForm']/div[2]/div[1]/input")).click();
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception {
        String environment = System.getenv("ENVIRONMENT");
        driver.get(url);
        int locationFound = 0;
        for (int i = 1; i < 10; i++) {
            String labelFound = driver.findElement(By.xpath("/html/body/b[" + i + "]")).getText();
            if (labelFound.equals("Release:")) {
                locationFound = i;
                break;
            }
        }
        String buildNumberField = driver.findElement(By.xpath("/html/body/i[" + locationFound + "]")).getText();
        String [] buildNumberArray = buildNumberField.split("_");
        String buildNumber =  buildNumberArray[1].replace("b", "");
        return buildNumber;
    }

    public static String infoVersionServerNumber(WebDriver driver, String url) throws Exception{
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("/html/body/b[" + i + "]")).getText();
            if(labelFound.equals("Server:")) {
                locationFound = i;
                break;
            }
        }
        String serverNumber = driver.findElement(By.xpath("/html/body/i[" + locationFound + "]")).getText();
        return serverNumber;
    }
}
