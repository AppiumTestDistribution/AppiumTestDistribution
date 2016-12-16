package com.appium.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by saikrisv on 16/12/16.
 */
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Properties prop = new Properties();

    private ConfigurationManager(String configFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(configFile);
        prop.load(inputStream);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }


    public static ConfigurationManager getInstance() throws IOException {
        if (instance == null) {
            String configFile = "config.properties";
            if (System.getenv().containsKey("CONFIG_FILE")) {
                configFile = System.getenv().get("CONFIG_FILE");
                System.out.println("Using config file from " + configFile);
            }

            instance = new ConfigurationManager(configFile);
        }
        return instance;
    }

    public boolean containsKey(String key) {
        return prop.containsKey(key);
    }
}
