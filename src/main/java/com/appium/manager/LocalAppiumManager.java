package com.appium.manager;

import com.appium.filelocations.FileLocations;
import com.appium.utils.CapabilityManager;
import com.appium.utils.OSType;
import com.thoughtworks.android.AndroidManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.SimulatorManager;
import com.thoughtworks.iOS.IOSManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class LocalAppiumManager implements IAppiumManager {

    private static AppiumDriverLocalService appiumDriverLocalService;

    private static AppiumDriverManager appiumDriverManager;

    private static AppiumDriverLocalService getAppiumDriverLocalService () {
        return appiumDriverLocalService;
    }

    private static final Logger LOGGER = Logger.getLogger(Class.class.getSimpleName());

    private static void setAppiumDriverLocalService (
            AppiumDriverLocalService appiumDriverLocalService ) {
        LocalAppiumManager.appiumDriverLocalService = appiumDriverLocalService;
    }

    private URL getAppiumUrl () {
        return getAppiumDriverLocalService().getUrl();
    }


    @Override
    public void destroyAppiumNode ( String host ) throws IOException {
        getAppiumDriverLocalService().stop();
        if (getAppiumDriverLocalService().isRunning()) {
            LOGGER.info("AppiumServer didn't shut... Trying to quit again....");
            getAppiumDriverLocalService().stop();
        }
    }

    @Override
    public String getRemoteWDHubIP ( String host ) throws IOException {
        return getAppiumUrl().toString();
    }

    @Override
    public void startAppiumServer ( String host ) throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server on Localhost......");
        System.out.println(
                "**************************************************************************\n");
        AppiumDriverLocalService appiumDriverLocalService;
        AppiumServiceBuilder builder =
                getAppiumServerBuilder(host)
                        .withLogFile(new File(
                                System.getProperty("user.dir")
                                        + FileLocations.APPIUM_LOGS_DIRECTORY
                                        + "appium_logs.txt"))
                        .withIPAddress(host)
                        .usingAnyFreePort();

        addUserSpecifiedServerCapabilities(builder);

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
    public List<Device> getDevices ( String machineIP, String platform ) throws Exception {
        List<Device> devices = new ArrayList<>();
        if (platform.equalsIgnoreCase(OSType.ANDROID.name())
                || platform.equalsIgnoreCase(OSType.BOTH.name())) {
            devices.addAll(new AndroidManager().getDevices());
        }
        if (platform.equalsIgnoreCase(OSType.iOS.name())
                || platform.equalsIgnoreCase(OSType.BOTH.name())) {
            if (CapabilityManager.getInstance().isApp()) {
                if (CapabilityManager.getInstance().isSimulatorAppPresentInCapsJson()) {
                    devices.addAll(new SimulatorManager()
                            .getAllBootedSimulators(OSType.iOS.name()));
                }
                if (CapabilityManager.getInstance().isRealDeviceAppPresentInCapsJson()) {
                    devices.addAll(new IOSManager().getDevices());
                }
            } else {
                devices.addAll(new IOSManager().getDevices());
            }
        }
        return devices;
    }

    @Override
    public Device getSimulator ( String machineIP, String deviceName, String os )
            throws IOException, InterruptedException {
        return new SimulatorManager().getDevice(deviceName, os, OSType.iOS.name());
    }

    @Override
    public int getAvailablePort ( String hostMachine ) throws IOException {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    @Override
    public int startIOSWebKitProxy ( String host ) throws Exception {
        int port = getAvailablePort(host);
        String webkitRunner = "ios_webkit_debug_proxy -c "
                + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                + ":" + port;
        String process = Runtime.getRuntime().exec(webkitRunner).toString();
        AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(process);
        return port;
    }

    @Override
    public void destoryIOSWebKitProxy ( String host ) throws Exception {
        if (AppiumDeviceManager.getAppiumDevice().getWebkitProcessID() != null) {
            String command = "kill -9 " + AppiumDeviceManager
                    .getAppiumDevice().getWebkitProcessID();
            LOGGER.info("Kills webkit proxy" + "******************" + command);
            Runtime.getRuntime().exec(command);
            AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(null);
        }
    }

    private AppiumServiceBuilder getAppiumServerBuilder ( String host ) throws Exception {
        if (CapabilityManager.getInstance().getAppiumServerPath(host) == null) {
            System.out.println("Picking Default Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithDefaultPath();
        } else {
            System.out.println("Picking UserSpecified Path for AppiumServiceBuilder");
            return getAppiumServiceBuilderWithUserAppiumPath(host);
        }
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithUserAppiumPath ( String host )
            throws Exception {
        return new AppiumServiceBuilder().withAppiumJS(
                new File(CapabilityManager.getInstance().getAppiumServerPath(host)));
    }

    private AppiumServiceBuilder getAppiumServiceBuilderWithDefaultPath () {
        return new AppiumServiceBuilder();
    }

    private void addUserSpecifiedServerCapabilities ( AppiumServiceBuilder builder ) throws Exception {

        appiumDriverManager = new AppiumDriverManager();

        JSONObject serverCaps = appiumDriverManager.getDesiredServerCapabilities();

        Iterator<?> keys = serverCaps.keys();

        if (serverCaps.length() != 0) {

            do {
                String key = (String) keys.next();

                GeneralServerFlag serverArgument = appiumDriverManager.getServerArgument(key);

                if (serverArgument != null && !serverCaps.get(key).toString().equals("null")) {
                    builder.withArgument(serverArgument, serverCaps.getString(key));
                } else if (serverArgument != null && serverCaps.get(key).toString().equals("null")) {
                    builder.withArgument(serverArgument);
                }
            } while (keys.hasNext());
        }
    }
}
