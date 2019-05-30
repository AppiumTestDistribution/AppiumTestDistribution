package com.appium.manager;

import com.github.device.Device;

import java.util.List;

import static java.lang.System.getenv;
import static java.text.MessageFormat.format;

public class CloudAppiumManager implements IAppiumManager {
    @Override
    public void destroyAppiumNode(String host) {

    }

    @Override
    public String getRemoteWDHubIP(String host) {
        String url = "https://{0}:{1}@{2}/wd/hub";
        return format(url, getenv("CLOUD_USER"), getenv("CLOUD_KEY"), host);
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
