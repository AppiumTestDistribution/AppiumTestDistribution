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
    private static ConfigFileManager instance;

    private ConfigFileManager(String configFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(configFile);
        prop.load(inputStream);
    }

    public static ConfigFileManager getInstance() throws IOException {
        if (instance == null) {
            String configFile = "config.properties";
            try {
                if (System.getenv().containsKey("CONFIG_FILE")) {
                    configFile = System.getenv().get("CONFIG_FILE");
                    System.out.println("Using config file from " + configFile);
                }
                instance = new ConfigFileManager(configFile);
                Enumeration keys = prop.propertyNames();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    configFileMap.put(key, prop.getProperty(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String getProperty(String object) {
        return configFileMap.get(object);
    }

    public String getProperty(String key,String value) {
        return configFileMap.getOrDefault(key,value);
    }
}
