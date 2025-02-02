package com.vertafore.common;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class G2_Common {
    public static void login(WebDriver driver, String accountID, String username) {
        String envUrl = Global_Common.getEnvironmentProperty("g2.environment.url") + accountID;
        driver.get(envUrl);
        driver.findElement(By.name("ssousername")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty("accountID." + accountID + "." + username + ".password"));
        driver.findElement(By.xpath("//*[@id='Table2']/tbody/tr/td/form/div/table[1]/tbody/tr[5]/td[2]/a")).click();
        waitForPageToLoad(driver);
    }

    public static void loginProducerPortal(WebDriver driver, String usernameProducerPortal, String environment) {
        String envUrl = Global_Common.getEnvironmentProperty("g2.producer.portal.url");
        driver.get(envUrl);
        driver.findElement(By.name("username")).sendKeys(usernameProducerPortal);
        driver.findElement(By.name("password")).sendKeys(Global_Common.getEnvironmentProperty(usernameProducerPortal + ".password"));

        if (environment.equalsIgnoreCase("PROD")) {
            driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[4]/td[2]/table/tbody/tr[5]/td/input")).click();
        } else {
            driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[4]/td[2]/table/tbody/tr[6]/td/input")).click();
        }
        waitForPageToLoad(driver);
    }

    public static WebDriver getPast408(WebDriver driver, String accountID, String userName) {
        int limit = 0;
        while (exists408(driver) && limit < 10) {
            Global_Common.endTestCase(driver);
            driver = Global_Common.loadWebDriver();
            G2_Common.login(driver, accountID, userName);
            limit++;
        }
        if (limit >= 10) {
            fail("408 error, please run test again.");
        }
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver getPast408ProducerPortal(WebDriver driver, String usernameProducerPortal, String environment) {
        int limit = 0;
        while (exists408(driver) && limit < 10) {
            Global_Common.endTestCase(driver);
            driver = Global_Common.loadWebDriver();
            G2_Common.loginProducerPortal(driver, usernameProducerPortal, environment);
            limit++;
        }
        if (limit >= 10) {
            fail("408 error, please run test again.");
        }
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        return driver;
    }

    private static boolean exists408(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            driver.findElement(By.xpath("//*[@id='sign']"));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void waitForPageToLoad(WebDriver driver) {
        ExpectedCondition< Boolean > pageLoad = new
                ExpectedCondition < Boolean > () {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };

        Wait< WebDriver > wait = new WebDriverWait(driver, 90);
        try {
            wait.until(pageLoad);
        } catch (Throwable pageLoadWaitError) {
            fail("Timeout during page load");
        }
    }

    public static void testEnvModulesTest(List<WebElement> moduleCells, WebDriver driver) {
        for (int i = 0; i < moduleCells.size(); i++) {
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + Global_Common.getEnvironmentProperty("module." + i));
            waitForPageToLoad(driver);

            switch (i) {
                case 0:
                    assertEquals("Employment Module", true, driver.findElement(By.xpath("//*[@id='tableHead']/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 1:
                    assertEquals("Database Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[2]/div[2]/table[1]/tbody/tr[1]/td[2]")).isDisplayed());
                    break;
                case 2:
                    break;
                case 3:
                    assertEquals("Toolkit Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table[1]/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 4:
                    assertEquals("Admin Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
            }
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + "/rpts/serviceRouter.do?action=process&targetModule=HOME&targetSubModule=HOMEPAGE&targetComponent=&targetService=");
            waitForPageToLoad(driver);
        }
    }

    public static void uatEnvModulesTest(List<WebElement> moduleCells, WebDriver driver) {
        for (int i = 0; i < moduleCells.size(); i++) {
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + Global_Common.getEnvironmentProperty("module." + i));
            waitForPageToLoad(driver);

            switch (i) {
                case 0:
                    assertEquals("Reports Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[3]/div[2]/table[1]/tbody/tr[1]/td[2]")).isDisplayed());
                    break;
                case 1:
                    assertEquals("Admin Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 2:
                    assertEquals("Configuration Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[2]/div[2]/table[1]/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
            }
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + "/rpts/serviceRouter.do?action=process&targetModule=HOME&targetSubModule=HOMEPAGE&targetComponent=&targetService=");
            waitForPageToLoad(driver);
        }
    }


    public static void preprodEnvModulesTest(List<WebElement> moduleCells, WebDriver driver) {
        for (int i = 0; i < moduleCells.size(); i++) {
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + Global_Common.getEnvironmentProperty("module." + i));
            waitForPageToLoad(driver);

            switch (i) {
                case 0:
                    assertEquals("Employment Module", true, driver.findElement(By.xpath("//*[@id='tableHead']/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 1:
                    assertEquals("Database Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[2]/div[2]/table[1]/tbody/tr[1]/td[2]")).isDisplayed());
                    break;
                case 2:
                    // Reports Module
                    break;
                case 3:
                    assertEquals("Toolkit Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table[1]/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 4:
                    assertEquals("Admin Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
            }
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + "/rpts/serviceRouter.do?action=process&targetModule=HOME&targetSubModule=HOMEPAGE&targetComponent=&targetService=");
            waitForPageToLoad(driver);
        }
    }

    public static void prodEnvModulesTest(List<WebElement> moduleCells, WebDriver driver) {
        for (int i = 0; i < moduleCells.size(); i++) {
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + Global_Common.getEnvironmentProperty("module." + i));
            waitForPageToLoad(driver);

            switch (i) {
                case 0:
                    assertEquals("Reports Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[3]/div[2]/table[1]/tbody/tr[1]/td[2]")).isDisplayed());
                    break;
                case 1:
                    assertEquals("Admin Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td/form/div[4]/div[2]/table/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
                case 2:
                    assertEquals("Configuration Module", true, driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/form/div[2]/div[2]/table[1]/tbody/tr[1]/td[1]")).isDisplayed());
                    break;
            }
            driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + "/rpts/serviceRouter.do?action=process&targetModule=HOME&targetSubModule=HOMEPAGE&targetComponent=&targetService=");
            waitForPageToLoad(driver);
        }
    }

    public static String infoVersionServerNumber(WebDriver driver, String url) throws Exception{
        driver.get(url);
        int locationFound = 0;
        for(int i=1; i<10; i++){
            String labelFound = driver.findElement(By.xpath("//*[@id='buildInfo']/p[2]/span[" + i + "]")).getText();
            if(labelFound.equals("Server:")) {
                locationFound = i + 1;
                break;
            }
        }
        String serverNumber = driver.findElement(By.xpath("//*[@id='buildInfo']/p[2]/span[" + locationFound + "]")).getText();
        return serverNumber;
    }
}
