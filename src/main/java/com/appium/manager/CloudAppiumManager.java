package com.appium.manager;

import org.apache.log4j.Logger;


import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;
import static java.text.MessageFormat.format;

public class CloudAppiumManager implements IAppiumManager {
    private final String url;
    private static final Logger LOGGER = Logger.getLogger(CloudAppiumManager.class.getName());

    public CloudAppiumManager(String cloudName) {
        url = "https://{2}/wd/hub";
    }

    public CloudAppiumManager() {
        url = "https://{0}:{1}@{2}/wd/hub";
    }

    @Override
    public void destroyAppiumNode(String host) {

    }

    @Override
    public String getRemoteWDHubIP(String host) {
        String format = format(url, getOverriddenStringValue("CLOUD_USER"),
                getOverriddenStringValue("CLOUD_KEY"), host);
        return format;
    }

    @Override
    public void startAppiumServer(String host) {

    }

    @Override
    public int getAvailablePort(String hostMachine) {
        return 0;
    }

}
