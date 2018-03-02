package com.appium.manager;

import com.thoughtworks.device.Device;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

public interface IAppiumManager {

    void destroyAppiumNode(String host) throws IOException;

    String getRemoteWDHubIP(String host) throws IOException;

    void startAppiumServer(String host) throws Exception;

    List<Device> getDevices(String machineIP,String platform) throws Exception;

    Device getSimulator(String machineIP, String deviceName, String os) throws IOException, InterruptedException;

    int getAvailablePort(String hostMachine) throws IOException;
}
