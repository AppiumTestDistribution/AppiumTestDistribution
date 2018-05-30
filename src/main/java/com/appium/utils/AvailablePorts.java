package com.appium.utils;

import com.appium.manager.AppiumManagerFactory;
import com.appium.manager.IAppiumManager;

import java.io.IOException;

public class AvailablePorts {

    public int getAvailablePort(String hostMachine) throws Exception {
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostMachine);
        return appiumManager.getAvailablePort(hostMachine);
    }
}
