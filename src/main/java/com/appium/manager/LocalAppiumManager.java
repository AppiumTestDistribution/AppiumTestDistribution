package com.appium.manager;

import com.appium.filelocations.FileLocations;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.DeviceManager;
import com.thoughtworks.device.SimulatorManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class LocalAppiumManager implements IAppiumManager {

    private static AppiumDriverLocalService appiumDriverLocalService;

    private static AppiumDriverLocalService getAppiumDriverLocalService() {
        return appiumDriverLocalService;
    }

    private static final Logger LOGGER = Logger.getLogger(Class.class.getSimpleName());

    private static void setAppiumDriverLocalService(
            AppiumDriverLocalService appiumDriverLocalService) {
        LocalAppiumManager.appiumDriverLocalService = appiumDriverLocalService;
    }

    private URL getAppiumUrl() {
        return getAppiumDriverLocalService().getUrl();
    }


    @Override
    public void destroyAppiumNode(String host) throws IOException {
        getAppiumDriverLocalService().stop();
        if (getAppiumDriverLocalService().isRunning()) {
            LOGGER.info("AppiumServer didn't shut... Trying to quit again....");
            getAppiumDriverLocalService().stop();
        }
    }

    @Override
    public String getRemoteWDHubIP(String host) throws IOException {
        return getAppiumUrl().toString();
    }

    @Override
    public void startAppiumServer(String host) throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server on Localhost......");
        System.out.println(
                "**************************************************************************\n");
        AppiumDriverLocalService appiumDriverLocalService;
        AppiumServiceBuilder builder =
                getAppiumServerBuilder()
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withLogFile(new File(
                                System.getProperty("user.dir")
                                        + FileLocations.APPIUM_LOGS_DIRECTORY
                                        + "appium_logs.txt"))
                        .withIPAddress(host)
                        .usingAnyFreePort();
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

    @Override
    public List<Device> getDevices(String machineIP) throws Exception {
        return new DeviceManager().getDevices();
    }

    @Override
    public Device getSimulator(String machineIP, String deviceName, String os) throws IOException, InterruptedException {
        return new SimulatorManager().getDevice(deviceName, os, "iOS");
    }

    @Override
    public int getAvailablePort(String hostMachine) throws IOException {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    private AppiumServiceBuilder getAppiumServerBuilder() {
        if (ConfigFileManager.configFileMap.get("APPIUM_JS_PATH") == null) {
            System.out.println("Picking Default Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithDefaultPath();
        } else {
            System.out.println("Picking UserSpecified Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithUserAppiumPath();
        }
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithUserAppiumPath() {
        return new AppiumServiceBuilder().withAppiumJS(new File(ConfigFileManager
                .configFileMap.get("APPIUM_JS_PATH")));
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithDefaultPath() {
        return new AppiumServiceBuilder();
    }

}
