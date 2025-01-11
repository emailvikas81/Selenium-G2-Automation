package com.vertafore.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class PE_Common {
    public  static void login(WebDriver driver, String userName) throws IOException {
        String environment = System.getenv("ENVIRONMENT");
        String envURL;
        if (environment.equalsIgnoreCase("PREPROD")) {
            envURL =  Global_Common.getEnvironmentProperty("pe.environment.url") + "/individual/edge/login/login.jsp";
        } else {
            envURL = Global_Common.getEnvironmentProperty("cx.environment.url") + "/login.html";
        }
        driver.get(envURL);
        driver.findElement(By.name("username")).sendKeys(userName);
        if (environment.equalsIgnoreCase("PREPROD")) {
            driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty(userName + ".password"));
            driver.findElement(By.xpath("//*[@id='page-content']/div/form/div[3]/input")).click();
        } else {
            WebElement formElement = driver.findElement(By.name("producerLoginForm"));
            formElement.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty(userName + ".password"));
            if (environment.equalsIgnoreCase("TEST")) {
                driver.findElement(By.xpath("//*[@id='producerLoginForm']/div[5]/div[1]/input")).click();
            } else {
                driver.findElement(By.xpath("//*[@id='producerLoginForm']/div[3]/div[1]/input")).click();
            }
        }
    }

    public static String infoVersionBuildNumber(WebDriver driver, String url) throws Exception {
        String environment = System.getenv("ENVIRONMENT");
        driver.get(url);
        int locationFound = 0;
        for (int i = 0; i < 10; i++) {
            String labelFound = driver.findElement(By.xpath("/html/body/b[2]")).getText();
            if (labelFound.equals("")) {
                locationFound = i;
            }
        }
        locationFound += 2;
        String buildNumber;
        if (environment.equalsIgnoreCase("PROD")) {
            buildNumber = driver.findElement(By.xpath("/html/body/i[" + locationFound + "]")).getText().replace("r2.0.1_b", "");
        } else {
            buildNumber = driver.findElement(By.xpath("/html/body/i[" + locationFound + "]")).getText().replace("r2.1.0_b", "");
        }
        return buildNumber;
    }
}
