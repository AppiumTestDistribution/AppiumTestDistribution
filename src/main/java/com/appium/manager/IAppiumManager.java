package com.appium.manager;


import com.github.device.Device;

import java.util.List;

public interface IAppiumManager {

    void destroyAppiumNode(String host) throws Exception;

    String getRemoteWDHubIP(String host) throws Exception;

    void startAppiumServer(String host) throws Exception;

    List<Device> getDevices(String machineIP, String platform);

    Device getSimulator(String machineIP, String deviceName, String os);

    int getAvailablePort(String hostMachine) throws Exception;



}
