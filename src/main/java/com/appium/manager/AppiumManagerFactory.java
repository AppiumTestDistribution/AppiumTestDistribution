package com.appium.manager;

import com.appium.entities.CloudHost;

public class AppiumManagerFactory {
    public static IAppiumManager getAppiumManager(String host) {
        if (CloudHost.isCloud(host)) {
            return new CloudAppiumManager();
        } else if ("127.0.0.1".equals(host)) {
            return new LocalAppiumManager();
        } else {
            return new RemoteAppiumManager();
        }
    }
}

