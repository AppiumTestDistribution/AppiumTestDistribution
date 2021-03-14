package com.appium.utils;

import static com.appium.utils.Variable.getOverriddenStringValue;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;
import static java.text.MessageFormat.format;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * ConfigFileManager - Read config file statically into configFileMap
 */
public enum ConfigFileManager {
    CATEGORY("ATDTest"),
    SUITE_NAME("ATDSuiteName"),
    LISTENERS(""),
    INCLUDE_GROUPS(""),
    EXCLUDE_GROUPS(""),
    FRAMEWORK("testng"),
    MAX_RETRY_COUNT("0"),
    CAPS("./caps/capabilities.json"),
    RUNNER_LEVEL("methods"),
    RUNNER("distribute");

    private static final Properties PROPERTIES;
    private static final Logger LOGGER = Logger.getLogger(ConfigFileManager.class.getSimpleName());

    static {
        PROPERTIES = new Properties();
        String configFile = getOverriddenStringValue("CONFIG_FILE", "./configs/config.properties");
        LOGGER.info(format("Using config file from [{0}]", configFile));
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            LOGGER.severe(format("Error while loading config file: {0}", e.getMessage()));
        }
    }

    private final String defaultValue;

    ConfigFileManager(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String get() {
        return getOverriddenStringValue(name(), defaultValue);
    }

    public boolean isTrue() {
        return parseBoolean(get());
    }

    public int getInt() {
        return parseInt(get());
    }
}