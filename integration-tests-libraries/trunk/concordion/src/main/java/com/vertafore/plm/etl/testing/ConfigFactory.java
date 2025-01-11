package com.vertafore.plm.etl.testing;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 7/31/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigFactory {

    private static final Properties PROPERTIES = new Properties();
    private static boolean propertiesLoaded = false;

    public static Properties getConfig() {
        if (!propertiesLoaded) {
            loadProperties();
        }

        return PROPERTIES;
    }

    private synchronized static void loadProperties() {
        try {
            if (!propertiesLoaded) {
                PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
                propertiesLoaded = true;
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
