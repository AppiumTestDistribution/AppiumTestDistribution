package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;
import com.github.yunusmete.stf.model.Device;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumServerManager {

    private AvailablePorts ap;
    private IOSDeviceConfiguration iosDeviceConfiguration;

    public static AppiumDriverLocalService getAppiumDriverLocalService() {
        return appiumDriverLocalService;
    }

    public static void setAppiumDriverLocalService(
            AppiumDriverLocalService appiumDriverLocalService) {
        AppiumServerManager.appiumDriverLocalService = appiumDriverLocalService;
    }

    static AppiumDriverLocalService appiumDriverLocalService;


    public AppiumServerManager() throws IOException {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        ap = new AvailablePorts();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */

    private void startAppiumServerSingleSession()
            throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server......");
        System.out.println(
                "**************************************************************************\n");
        AppiumDriverLocalService appiumDriverLocalService;
        int port = ap.getPort();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder().withAppiumJS(new File(ConfigFileManager
                        .configFileMap.get("APPIUM_JS_PATH")))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                        System.getProperty("user.dir")
                                + "/target/appiumlogs/appium_logs.txt"))
                        .withIPAddress("127.0.0.1")
                        .usingPort(port);
        /* and so on */
        ;
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Appium Server Started at......"
                + appiumDriverLocalService.getUrl());
        System.out.println(
                "**************************************************************************\n");
        setAppiumDriverLocalService(appiumDriverLocalService);
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

    public URL getAppiumUrl() {
        return getAppiumDriverLocalService().getUrl();
    }

    private void destroyAppiumNode() {
        getAppiumDriverLocalService().stop();
        if (getAppiumDriverLocalService().isRunning()) {
            System.out.println("AppiumServer didn't shut... Trying to quit again....");
            getAppiumDriverLocalService().stop();
        }
    }

    public void startAppiumServer() throws Exception {
        startAppiumServerSingleSession();
    }

    public void stopAppiumServer() throws IOException, InterruptedException {
        destroyAppiumNode();
        List<Device> devices = DeviceAllocationManager.service.getDevices().getDevices();
        for (Device device : devices) {
            if (device.isPresent()) {
                DeviceAllocationManager.service.deleteDeviceBySerial(device.getSerial());
            }
        }
    }
}
