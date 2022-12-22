package com.appium.manager;

public interface IAppiumManager {

    void destroyAppiumNode(String host) throws Exception;

    String getRemoteWDHubIP(String host) throws Exception;

    void startAppiumServer(String host) throws Exception;

    int getAvailablePort(String hostMachine) throws Exception;
}
