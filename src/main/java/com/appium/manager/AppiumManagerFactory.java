package com.appium.manager;

import com.appium.capabilities.Capabilities;

public class AppiumManagerFactory {
    public IAppiumManager getAppiumManagerFor(String host) {
        return AppiumManagerFactory.getAppiumManager(host);
    }

    public static IAppiumManager getAppiumManager(String host) {
        if (Capabilities.getInstance().isCloud(host)) {
            return new CloudAppiumManager();
        } else if ("127.0.0.1".equals(host)) {
            return new LocalAppiumManager();
        } else {
            return new RemoteAppiumManager();
        }
    }
}

