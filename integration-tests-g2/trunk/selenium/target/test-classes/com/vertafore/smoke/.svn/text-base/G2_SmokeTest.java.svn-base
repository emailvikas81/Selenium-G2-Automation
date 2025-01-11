package com.vertafore.smoke;

import com.google.common.base.Strings;
import com.vertafore.common.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class G2_SmokeTest {
    private WebDriver driver;
    private static final long TIMEOUT_DEFAULT =  1000 * 60 * 10;
    private static final String INFO_DEFAULT = "info/version.jsp";
    String accountID = Global_Common.getEnvironmentProperty("smoketest.accountID");
    String userName = Global_Common.getEnvironmentProperty("smoketest.user");
    String environment = System.getenv("ENVIRONMENT");
    String usernameProducerPortal = Global_Common.getEnvironmentProperty("smoketest.producer.portal.user");

    @Before
    public void setUp() throws Exception {
        driver = Global_Common.loadWebDriver();
    }

    @Test
    public void loginTest() throws Exception {
        G2_Common.login(driver, accountID, userName);
        driver = G2_Common.getPast408(driver, accountID, userName);
        if (environment.equalsIgnoreCase("TEST")) {
            driver.findElement(By.linkText("Continue with Login")).click();
        }
        String pageSource = driver.getPageSource();
        String[] firstLast = Global_Common.getEnvironmentProperty("accountID." + accountID + "." + userName + ".name").split(" ");
        String firstName = firstLast[0];
        String lastName = firstLast[1];
        assertEquals("Login", true, pageSource.contains(firstName + "  " + lastName));
    }

    @Test
    public void producerPortalTest() throws Exception {                     //TODO: add support for all UAT environments
        Assume.assumeFalse(environment.equalsIgnoreCase("WFTST"));
        Assume.assumeFalse(environment.equalsIgnoreCase("CSTST"));
        Assume.assumeFalse(environment.equalsIgnoreCase("IMPBAS"));

        G2_Common.loginProducerPortal(driver, usernameProducerPortal, environment);
        driver = G2_Common.getPast408ProducerPortal(driver, usernameProducerPortal, environment);
        driver.manage().window().maximize();
        WebElement userElement = driver.findElement(By.xpath("//*[@id='Table2']/tbody/tr[1]/td[1]/table/tbody/tr/th"));
        String nameOfUser = Global_Common.getEnvironmentProperty(usernameProducerPortal + ".name");
        assertEquals("Producer Portal", " Welcome " + nameOfUser, userElement.getText());
    }

    @Test
    public void modulesTest() throws Exception {
        G2_Common.login(driver, accountID, userName);
        driver = G2_Common.getPast408(driver, accountID, userName);
        if (environment.equalsIgnoreCase("TEST")) {
            driver.findElement(By.linkText("Continue with Login")).click();
        }
        driver.manage().window().maximize();

        WebElement moduleTable = driver.findElement(By.cssSelector("table[class='rootVoices']"));
        List<WebElement> moduleRow = moduleTable.findElements(By.tagName("tr"));
        List<WebElement> moduleCells = moduleRow.get(0).findElements(By.tagName("td"));

        String environment = System.getenv("ENVIRONMENT");
        if (environment.equalsIgnoreCase("TEST")) {
            G2_Common.testEnvModulesTest(moduleCells, driver);
        } else if(environment.equalsIgnoreCase("WFTST")) {
            G2_Common.uatEnvModulesTest(moduleCells, driver);            //TODO: add CSTST and IMPBAS environment support
        } else if(environment.equalsIgnoreCase("PREPROD")) {
            G2_Common.preprodEnvModulesTest(moduleCells, driver);
        } else {
            G2_Common.prodEnvModulesTest(moduleCells, driver);
        }

    }

    @Test
    public void verifyEARTest() throws Exception {
        ArrayList<String> serverList = Global_Common.getServerList();
        ArrayList<String> errorMessages = new ArrayList<String>();
        String serverFound = "";

        long stopTime = System.currentTimeMillis() + getTimeout();
        for(int i = 0; i < serverList.size(); i++){
            System.out.println("Checking: " + serverList.get(i) + " (Server " + (i+1) + ")");
            do {

                G2_Common.login(driver, accountID, userName);
                driver = G2_Common.getPast408(driver, accountID, userName);
                if (environment.equalsIgnoreCase("TEST")) {
                    driver.findElement(By.linkText("Continue with Login")).click();
                }
                driver.manage().window().maximize();
                driver.get(Global_Common.getEnvironmentProperty("g2.root.url") + "/TestWebApp/jsp/index.jsp");
                G2_Common.waitForPageToLoad(driver);
                driver.manage().window().maximize();

                serverFound = driver.findElement(By.xpath("//*[@id=\"mid\"]/div/span/span[2]")).getText();
                System.out.println("server found: " + serverFound);
                if(serverFound.equals(serverList.get(i))){
                    driver.findElement(By.id("getLabels")).click();
                    Thread.sleep(2000);
                    WebElement earTable = driver.findElement(By.xpath("//*[@id='labelBox']/table[1]"));
                    List<WebElement> earTableRows = earTable.findElements(By.tagName("tr"));
                    for (int j = 0; j < earTableRows.size(); j++) {
                        List<WebElement> earTableCells = earTableRows.get(j).findElements(By.tagName("td"));
                        for (int k = 0; k < earTableCells.size(); k++) {
                            if (earTableCells.get(k).getText().equals("")) {
                                errorMessages.add(serverList.get(i) + ": " + earTableCells.get(k-1).getText());
                            }
                        }
                    }
                    earTable = driver.findElement(By.xpath("//*[@id='labelBox']/table[2]"));
                    earTableRows = earTable.findElements(By.tagName("tr"));
                    for (int j = 0; j < earTableRows.size(); j++) {
                        List<WebElement> earTableCells = earTableRows.get(j).findElements(By.tagName("td"));
                        for (int k = 0; k < earTableCells.size(); k++) {
                            if (earTableCells.get(k).getText().equals("")) {
                                errorMessages.add(serverList.get(i) + ": " + earTableCells.get(k-1).getText());
                            }
                        }
                    }
                    break;
                } else{
                    Global_Common.endTestCase(driver);
                    driver = Global_Common.loadWebDriver();
                }
                if(stopTime <= System.currentTimeMillis()){
                    errorMessages.add(serverList.get(i) + " (Server " + (i+1) + ") NOT available");
                }
            }while(System.currentTimeMillis() <= stopTime);
        }
        if(errorMessages.size() > 0){
            for(String error : errorMessages){
                System.out.println(error);
            }
            fail("verifyEARTestFailed");
        }
    }

    private long getTimeout() {
        try {
            long timeout = Long.valueOf(Global_Common.getEnvironmentProperty("server.startup.timeout")) * 1000;
            System.out.println("Waiting custom time for servers to start: "+timeout);
            return timeout;
        } catch (Exception e) {
            System.out.println("Waiting default time for servers to start: "+TIMEOUT_DEFAULT);
            return TIMEOUT_DEFAULT;
        }
    }


    @After
    public void tearDown(){
        Global_Common.endTestCase(driver);
    }
}
