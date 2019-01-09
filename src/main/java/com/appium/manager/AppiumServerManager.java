package com.appium.manager;

import com.appium.device.DevicesByHost;
import com.appium.device.HostMachineDeviceManager;
import io.appium.java_client.service.local.flags.ServerArgument;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumServerManager {

    private RemoteAppiumManager remoteAppiumManager;
    private DevicesByHost devicesByHost;

    public AppiumServerManager() {
        remoteAppiumManager = new RemoteAppiumManager();
        devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
    }

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
        for (String host : devicesByHost.getAllHosts()) {
            startAppiumServerSingleSession(host);
        }
    }

    public void stopAppiumServer() throws Exception {
        for ( String host: devicesByHost.getAllHosts()) {
            IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(host);
            appiumManager.destroyAppiumNode(host);
        }
    }
}
