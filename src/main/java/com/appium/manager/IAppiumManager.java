package com.appium.manager;

import java.io.IOException;

public interface IAppiumManager {

    void destroyAppiumNode(String host) throws IOException;

    String getRemoteWDHubIP(String host) throws IOException;

    void startAppiumServer(String host) throws Exception;
}
