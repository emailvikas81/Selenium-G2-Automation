package com.vertafore.plm.fermenter.etl.util;

/**
 * Created with IntelliJ IDEA.
 * User: deaconda
 * Date: 9/8/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnvironmentUtilities {
    public String getSystemProperty(String propertyName){
        return System.getProperty(propertyName);
    }

    public String getEnvironmentVariable(String variableName){
        return System.getenv(variableName);
    }
}
