package com.appium.manager;

import io.appium.java_client.service.local.flags.ServerArgument;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumServerManager {


    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     * @param host
     */

    private void startAppiumServerSingleSession(String host)
            throws Exception {
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(host);
        appiumManager.startAppiumServer(host);
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */
    ServerArgument webKitProxy = new ServerArgument() {
        @Override
        public String getArgument() {
            return "--webkit-debug-proxy-port";
        }
    };



    public void startAppiumServer() throws Exception {
        startAppiumServerSingleSession("127.0.0.1");
    }

    public void stopAppiumServer() throws Exception {
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager("127.0.0.1");
        appiumManager.destroyAppiumNode("127.0.0.1");
    }
}
