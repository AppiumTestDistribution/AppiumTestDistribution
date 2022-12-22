package com.appium.manager;

import com.appium.filelocations.FileLocations;
import com.appium.capabilities.Capabilities;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;

public class LocalAppiumManager implements IAppiumManager {

    private static AppiumDriverLocalService appiumDriverLocalService;

    private static AppiumDriverLocalService getAppiumDriverLocalService() {
        return appiumDriverLocalService;
    }

    private static final Logger LOGGER = Logger.getLogger(LocalAppiumManager.class.getName());

    private static void setAppiumDriverLocalService(
            AppiumDriverLocalService appiumDriverLocalService) {
        LocalAppiumManager.appiumDriverLocalService = appiumDriverLocalService;
    }

    private URL getAppiumUrl() {
        return getAppiumDriverLocalService().getUrl();
    }


    @Override
    public void destroyAppiumNode(String host) {
        LOGGER.info("Shutting down Appium Server");
        getAppiumDriverLocalService().stop();
        if (getAppiumDriverLocalService().isRunning()) {
            LOGGER.info("AppiumServer didn't shut... Trying to quit again....");
            getAppiumDriverLocalService().stop();
        }
    }

    @Override
    public String getRemoteWDHubIP(String host) {
        return getAppiumUrl().toString();
    }

    @Override
    public void startAppiumServer(String host) throws Exception {
        LOGGER.info(LOGGER.getName() + "Starting Appium Server on Localhost");
        AppiumDriverLocalService appiumDriverLocalService;
        AppiumServiceBuilder builder =
                getAppiumServerBuilder(host)
                        .withLogFile(new File(
                                System.getProperty("user.dir")
                                        + FileLocations.APPIUM_LOGS_DIRECTORY
                                        + "appium_logs.txt"))
                        .withIPAddress(host)
                        .withTimeout(Duration.ofSeconds(60))
                        .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                        .withArgument(() -> "--config", System.getProperty("user.dir")
                                + FileLocations.SERVER_CONFIG)
                        .withArgument(GeneralServerFlag.USE_PLUGINS, "device-farm")
                        .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                        .usingAnyFreePort();
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        LOGGER.info(LOGGER.getName() + "Appium Server Started at......"
                + appiumDriverLocalService.getUrl());
        setAppiumDriverLocalService(appiumDriverLocalService);
    }

    /*private void getWindowsDevice(String platform, List<Device> devices) {
        if (platform.equalsIgnoreCase(OSType.WINDOWS.name())
                && Capabilities.getInstance().isWindowsApp()) {
            Device device = new Device();
            device.setName("windows");
            device.setOs("windows");
            device.setName("windows");
            device.setUdid("win-123");
            device.setDevice(true);
            List<Device> deviceList = new ArrayList<>();
            deviceList.add(device);
            devices.addAll(deviceList);
        }
    }*/

    @Override
    public int getAvailablePort(String hostMachine) throws IOException {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    private AppiumServiceBuilder getAppiumServerBuilder(String host) throws Exception {
        if (Capabilities.getInstance().getAppiumServerPath(host) == null) {
            LOGGER.info("Picking Default Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithDefaultPath();
        } else {
            LOGGER.info("Picking UserSpecified Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithUserAppiumPath(host);
        }
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithUserAppiumPath(String host)
            throws Exception {
        return new AppiumServiceBuilder().withAppiumJS(
                new File(Capabilities.getInstance().getAppiumServerPath(host)));
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithDefaultPath() {
        return new AppiumServiceBuilder();
    }

}
