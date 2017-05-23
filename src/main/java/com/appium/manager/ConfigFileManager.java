package com.appium.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ConfigFileManager - Read config file statically into configFileMap
 */
public class ConfigFileManager {
    public static Map<String, String> configFileMap = new HashMap();
    private static Properties prop = new Properties();

    static {
        try {
            String configFile = "config.properties";
            if (System.getenv().containsKey("CONFIG_FILE")) {
                configFile = System.getenv().get("CONFIG_FILE");
                System.out.println("Using config file from " + configFile);
            }
            FileInputStream inputStream = new FileInputStream(configFile);
            prop.load(inputStream);
            Enumeration keys = prop.propertyNames();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                configFileMap.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
