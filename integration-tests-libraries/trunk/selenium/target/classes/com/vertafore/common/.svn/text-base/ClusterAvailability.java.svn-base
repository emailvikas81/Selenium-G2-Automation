package com.vertafore.common;

import com.google.common.base.Strings;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class ClusterAvailability {
    private static final long TIMEOUT_DEFAULT =  1000 * 60 * 10;
    private static final String INFO_DEFAULT = "info/version.jsp";
    private WebDriver driver;

    @Test
    public void clusterAvailability() throws Exception {
        String environment = System.getenv("ENVIRONMENT");
        String product = System.getenv("PRODUCT");
        if("UAT".equalsIgnoreCase(environment)){
            clusterAvailabilityLoadBalancer();
        } else if ("PROD".equalsIgnoreCase(environment) && "agy".equalsIgnoreCase(product)) {
            clusterAvailabilityLoadBalancer();
        } else if ("g2".equalsIgnoreCase(product)) {
            clusterAvailabilityLoadBalancer();
        } else {
            clusterAvailabilityDirect();
        }
    }

    public void clusterAvailabilityDirect() throws Exception {
        System.out.println("Starting cluster availability check (via internal IP)");
        ArrayList<String> serverList = Global_Common.getServerList();
        ArrayList<String> errorMessages = new ArrayList<String>();
        Boolean serverAvailable;
        String outputErrorMessage = "";

        long stopTime = System.currentTimeMillis() + getTimeout();
        for(int i = 0; i < serverList.size(); i++){
            do{
                System.out.println("Checking: " + serverList.get(i) + " (Server " + (i+1) + ")");
                serverAvailable = Global_Common.availabilityCheck("http://" + serverList.get(i) + "/" + getInfoUrl());
                if(serverAvailable){
                    break;
                }else{
                    System.out.println("Waiting on server...");
                    Thread.sleep(10000);
                }
                if(stopTime <= System.currentTimeMillis()){
                    errorMessages.add(serverList.get(i) + " (Server " + (i+1) + ")");
                }
            }while(System.currentTimeMillis() <= stopTime);
        }
        if(errorMessages.size() > 0){
            for(String error : errorMessages){
                if(outputErrorMessage.equals("")){
                    outputErrorMessage = "Server(s) NOT available: " + error;
                }else{
                    outputErrorMessage = outputErrorMessage + ", " + error;
                }
            }
            fail(outputErrorMessage);
        }
    }

    public void clusterAvailabilityLoadBalancer() throws Exception {
        System.out.println("Starting cluster availability check (via load balancer)");
        String product = System.getenv("PRODUCT");
        String environment = System.getenv("ENVIRONMENT");
        driver = Global_Common.loadWebDriver();
        ArrayList<String> serverList = Global_Common.getServerList();
        ArrayList<String> errorMessages = new ArrayList<String>();
        Boolean serverAvailable;
        String outputErrorMessage = "";
        String serverFound = "";
        long stopTime = System.currentTimeMillis() + getTimeout();
        for(int i = 0; i < serverList.size(); i++){
            System.out.println("Checking: " + serverList.get(i) + " (Server " + (i+1) + ")");
            do{
                if ("agy".equalsIgnoreCase(product)) {
                    if ("PROD".equalsIgnoreCase(environment)) {
                        serverAvailable = Global_Common.availabilityCheck(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/agency/" + getInfoUrl());
                    } else {
                        serverAvailable = Global_Common.availabilityCheck(Global_Common.getEnvironmentProperty(product + "uat.environment.url") + "/agency/" + getInfoUrl());
                    }
                } else if ("cx".equalsIgnoreCase(product)) {
                    serverAvailable = Global_Common.availabilityCheck(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/ComplianceExpress/" + getInfoUrl());
                } else if ("g2".equalsIgnoreCase(product)) {
                    serverAvailable = Global_Common.availabilityCheck(Global_Common.getEnvironmentProperty(product + ".root.url") + "/authenticate/" + getInfoUrl());
                } else {
                    serverAvailable = Global_Common.availabilityCheck(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/" + getInfoUrl());
                }
                if(serverAvailable) {
                    if ("agy".equalsIgnoreCase(product)) {
                        if ("PROD".equalsIgnoreCase(environment)) {
                            driver.get(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/agency/" + getInfoUrl());
                        } else {
                            driver.get(Global_Common.getEnvironmentProperty(product + "uat.environment.url") + "/agency/" + getInfoUrl());
                        }
                    } else if ("cx".equalsIgnoreCase(product)) {
                        driver.get(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/ComplianceExpress/" + getInfoUrl());
                    } else if ("g2".equalsIgnoreCase(product)) {
                        driver.get(Global_Common.getEnvironmentProperty(product + ".root.url") + "/authenticate/" + getInfoUrl());
                    }
                    else {
                        driver.get(Global_Common.getEnvironmentProperty(product + ".environment.url") + "/" + getInfoUrl());
                    }
                    if("pm".equalsIgnoreCase(product)){
                        serverFound = PM_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".environment.url") + "/" + getInfoUrl());
                    }else if("px".equalsIgnoreCase(product)){
                        serverFound = PX_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".environment.url") + "/" + getInfoUrl());
                    }
                    else if("agy".equalsIgnoreCase(product)) {
                        if ("PROD".equalsIgnoreCase(environment)) {
                            serverFound = Agency_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".environment.url") + "/agency/" + getInfoUrl());
                        } else {
                            serverFound = Agency_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + "uat.environment.url") + "/agency/" + getInfoUrl());
                        }
                    } else if ("cx".equalsIgnoreCase(product)) {
                        serverFound = CX_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".environment.url") + "/ComplianceExpress/" + getInfoUrl());
                    } else if ("sfs".equalsIgnoreCase(product)){
                        serverFound = SFS_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".environment.url") + "/" + getInfoUrl());
                    } else if("g2".equalsIgnoreCase(product)) {
                        serverFound = G2_Common.infoVersionServerNumber(driver, Global_Common.getEnvironmentProperty(product + ".root.url") + "/authenticate/" + getInfoUrl());
                    }
                    System.out.println("server found: " + serverFound);
                    if(serverFound.equals(serverList.get(i))){
                        break;
                    } else{
                        Global_Common.endTestCase(driver);
                        driver = Global_Common.loadWebDriver();
                    }
                }else{
                    System.out.println("Waiting on server...");
                    Thread.sleep(10000);
                }
                if(stopTime <= System.currentTimeMillis()){
                    errorMessages.add(serverList.get(i) + " (Server " + (i+1) + ")");
                }
            }while(System.currentTimeMillis() <= stopTime);
        }
        if(errorMessages.size() > 0){
            for(String error : errorMessages){
                if(outputErrorMessage.equals("")){
                    outputErrorMessage = "Server(s) NOT available: " + error;
                }else{
                    outputErrorMessage = outputErrorMessage + ", " + error;
                }
            }
            fail(outputErrorMessage);
        }
    }

    @After
    public void teardown() {
        Global_Common.endTestCase(driver);
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

    private String getInfoUrl() {
        if (Strings.isNullOrEmpty(Global_Common.getEnvironmentProperty("info.url"))) {
            return INFO_DEFAULT;
        } else {
            return Global_Common.getEnvironmentProperty("info.url");
        }
    }
}
