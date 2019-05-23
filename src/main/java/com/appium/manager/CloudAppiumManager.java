package com.appium.manager;

import com.github.device.Device;

import java.util.List;

public class CloudAppiumManager implements IAppiumManager {
    @Override
    public void destroyAppiumNode(String host) {

    }

    @Override
    public String getRemoteWDHubIP(String host) {
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
    public int getAvailablePort(String hostMachine) throws Exception {
        return 0;
    }

    @Override
    public int startIOSWebKitProxy(String host) throws Exception {
        return 0;
    }

    @Override
    public void destoryIOSWebKitProxy(String host) throws Exception {

    }
}
