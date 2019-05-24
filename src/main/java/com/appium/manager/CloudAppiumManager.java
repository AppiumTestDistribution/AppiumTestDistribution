package com.appium.manager;

import com.github.device.Device;

import java.text.MessageFormat;
import java.util.List;

public class CloudAppiumManager implements IAppiumManager {
    @Override
    public void destroyAppiumNode(String host) {

    }

    @Override
    public String getRemoteWDHubIP(String host) {
        if (host.contains("sauce")) {
            return MessageFormat.format("https://{0}:{1}@ondemand.eu-central-1.saucelabs.com/wd/hub",
                System.getenv("sauceUserName"), System.getenv("sauceAccessKey"));
        }
        return "";
    }

    @Override
    public void startAppiumServer(String host) {

    }

    @Override
    public List<Device> getDevices(String machineIP, String platform) {
        return null;
    }

    @Override
    public Device getSimulator(String machineIP, String deviceName, String os) {
        return null;
    }

    @Override
    public int getAvailablePort(String hostMachine) {
        return 0;
    }

    @Override
    public int startIOSWebKitProxy(String host) {
        return 0;
    }

    @Override
    public void destoryIOSWebKitProxy(String host) {

    }
}
