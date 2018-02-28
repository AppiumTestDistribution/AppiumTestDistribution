package com.appium.manager;

public class AppiumManagerFactory {
    public static IAppiumManager getAppiumManager(String host) {
        if ("127.0.0.1".equals(host)) {
            return new LocalAppiumManager();
        } else {
            return new RemoteAppiumManager();
        }
    }
}

