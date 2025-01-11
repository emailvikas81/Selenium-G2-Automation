package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

public class PM_Common {

    public static void login(WebDriver driver, String subscriberID, String userName) throws IOException {
        String envURL = Global_Common.getEnvironmentProperty("pm.environment.url") + "/login.do";
        driver.get(envURL);
        driver.findElement(By.name("subscriberId")).sendKeys(subscriberID);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty("subscriberID." + subscriberID + "." + userName + ".password"));
        driver.findElement(By.xpath("//*[@id='eSpace']/form/table/tbody/tr[4]/td[2]/input")).click();
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception{
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
