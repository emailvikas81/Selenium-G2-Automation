package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.junit.Assert.fail;

public class SFS_Common {

    public static void login(WebDriver driver, String stateID, String userName) throws IOException {
        String envURL = Global_Common.getEnvironmentProperty("sfs.environment.url");
        driver.get(envURL);
        driver.findElement(By.name("custId")).sendKeys(stateID);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty("stateID." + stateID + "." + userName + ".password"));
        driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
        Boolean changePassword = driver.findElements(By.id("changePasswordHeader")).size() > 0;
        if(changePassword){
            fail("Password expired!  Must change password.");
        }
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception{
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("/html/body/div[2]/b[" + i + "]")).getText();
            if(labelFound.equals("Build Number:")) {
                locationFound = i;
                break;
            }
        }
        String buildNumber = driver.findElement(By.xpath("/html/body/div[2]/i[" + locationFound + "]")).getText();
        return buildNumber;
    }

    public static String infoVersionServerNumber(WebDriver driver, String url) throws Exception{
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("/html/body/div[2]/b[" + i + "]")).getText();
            if(labelFound.equals("Machine Name:")) {
                locationFound = i;
                break;
            }
        }
        String serverNumber = driver.findElement(By.xpath("/html/body/div[2]/i[" + locationFound + "]")).getText();
        return serverNumber;
    }
}
