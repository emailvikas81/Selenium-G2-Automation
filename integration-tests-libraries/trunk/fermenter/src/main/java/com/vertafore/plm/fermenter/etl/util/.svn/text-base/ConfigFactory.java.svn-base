package com.vertafore.plm.fermenter.etl.util;

import java.io.IOException;
import java.util.Properties;

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
                PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("etl.properties"));
                propertiesLoaded = true;
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
