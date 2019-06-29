package com.appium.manager;

import com.appium.utils.Api;
import com.appium.capabilities.CapabilityManager;
import com.appium.utils.Helpers;
import com.appium.utils.OSType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.device.Device;
import okhttp3.Response;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RemoteAppiumManager extends Helpers implements IAppiumManager {

    private static final Logger LOGGER = Logger.getLogger(RemoteAppiumManager.class.getName());

    @Override
    public void destroyAppiumNode(String host) throws Exception {
        new Api().getResponse("http://" + host + ":" + getRemoteAppiumManagerPort(host)
                + "/appium/stop").body().string();

    }

    @Override
    public String getRemoteWDHubIP(String host) throws Exception {
        String hostIP = "http://" + host;
        String appiumRunningPort = new JSONObject(new Api().getResponse(hostIP
                + ":" + getRemoteAppiumManagerPort(host)
                + "/appium/isRunning").body().string()).get("port").toString();
        return hostIP + ":" + appiumRunningPort + "/wd/hub";
    }

    @Override
    public void startAppiumServer(String host) throws Exception {
        LOGGER.info(LOGGER.getName() + "Starting Appium Server on host " + host);
        String serverPath = CapabilityManager.getInstance().getAppiumServerPath(host);
        String serverPort = CapabilityManager.getInstance().getAppiumServerPort(host);
        if (serverPath == null
                && serverPort == null) {
            LOGGER.info(LOGGER.getName()
                + "Picking Default Path for AppiumServiceBuilder");
            new Api().getResponse("http://" + host + ":"
                    + getRemoteAppiumManagerPort(host)
                    + "/appium/start").body().string();
        } else if (serverPath != null && serverPort != null) {
            LOGGER.info(LOGGER.getName()
                + "Picking UserSpecified Path & Port for AppiumServiceBuilder");
            new Api().getResponse("http://" + host + ":"
                    + getRemoteAppiumManagerPort(host)
                    + "/appium/start?URL=" + serverPath
                    + "&PORT=" + serverPort).body().string();
        } else if (serverPath != null) {
            LOGGER.info(LOGGER.getName()
                + "Picking UserSpecified Path" + "& Using default Port for AppiumServiceBuilder");
            new Api().getResponse("http://" + host + ":"
                    + getRemoteAppiumManagerPort(host)
                    + "/appium/start?URL=" + serverPath).body().string();
        } else if (serverPort != null) {
            LOGGER.info(LOGGER.getName()
                + "Picking Default Path & User Port for AppiumServiceBuilder");
            new Api().getResponse("http://" + host + ":"
                    + getRemoteAppiumManagerPort(host)
                    + "/appium/start?PORT=" + serverPort).body().string();
        }

        boolean status = Boolean.getBoolean(new JSONObject(new Api()
                .getResponse("http://" + host + ":"
                        + getRemoteAppiumManagerPort(host)
                        + "/appium/isRunning").body().string()).get("status").toString());
        if (status) {
            LOGGER.info(LOGGER.getName()
                + "Appium Server started successfully on  " + host);
        }
    }

    @Override
    public List<Device> getDevices(String machineIP, String platform) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Device> devices = new ArrayList<>();

        if (platform.equalsIgnoreCase(OSType.ANDROID.name())
                || platform.equalsIgnoreCase(OSType.BOTH.name())) {
            List<Device> androidDevices = Arrays.asList(mapper.readValue(new URL(
                            "http://" + machineIP + ":"
                                    + getRemoteAppiumManagerPort(machineIP) + "/devices/android"),
                    Device[].class));
            Optional.ofNullable(androidDevices).ifPresent(devices::addAll);
        }
        if (platform.equalsIgnoreCase(OSType.iOS.name())
                || platform.equalsIgnoreCase(OSType.BOTH.name())) {
            if (CapabilityManager.getInstance().isApp()) {
                if (CapabilityManager.getInstance().isSimulatorAppPresentInCapsJson()) {
                    List<Device> bootedSims = Arrays.asList(mapper.readValue(new URL(
                                    "http://" + machineIP + ":"
                                            + getRemoteAppiumManagerPort(machineIP)
                                            + "/devices/ios/bootedSims"),
                            Device[].class));
                    Optional.ofNullable(bootedSims).ifPresent(devices::addAll);
                }
                if (CapabilityManager.getInstance().isRealDeviceAppPresentInCapsJson()) {
                    List<Device> iOSRealDevices = Arrays.asList(mapper.readValue(new URL(
                                    "http://" + machineIP + ":"
                                            + getRemoteAppiumManagerPort(machineIP)
                                            + "/devices/ios/realDevices"),
                            Device[].class));
                    Optional.ofNullable(iOSRealDevices).ifPresent(devices::addAll);
                }
            } else {
                List<Device> iOSDevices = Arrays.asList(mapper.readValue(new URL(
                                "http://" + machineIP + ":"
                                        + getRemoteAppiumManagerPort(machineIP)
                                        + "/devices/ios/realDevices"),
                        Device[].class));
                Optional.ofNullable(iOSDevices).ifPresent(devices::addAll);
            }
        }
        return devices;
    }

    @Override
    public Device getSimulator(String machineIP, String deviceName, String os) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = String.format("http://%s:"
                        + getRemoteAppiumManagerPort(machineIP)
                        + "/device/ios/simulator"
                        + "?simulatorName=%s&simulatorOSVersion=%s",
                machineIP, URLEncoder.encode(deviceName, "UTF-8"),
                URLEncoder.encode(os, "UTF-8"));
        Device device = mapper.readValue(new URL(url),
                Device.class);
        return device;
    }

    @Override
    public int getAvailablePort(String hostMachine) throws Exception {
        String url = String.format("http://%s:"
                + getRemoteAppiumManagerPort(hostMachine)
                + "/machine/availablePort", hostMachine);
        Response response = new Api().getResponse(url);
        return Integer.parseInt(response.body().string());
    }

    @Override
    public int startIOSWebKitProxy(String host) throws Exception {
        int port = getAvailablePort(host);
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = String.format("http://%s:" + getRemoteAppiumManagerPort(host)
                        + "/devices/ios/webkitproxy/start"
                        + "?udid=%s&port=%s", host,
                AppiumDeviceManager.getAppiumDevice().getDevice().getUdid(),
                port);
        Response response = new Api().getResponse(url);
        AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(response.body().string());
        return port;
    }

    @Override
    public void destoryIOSWebKitProxy(String host) throws Exception {
        if (AppiumDeviceManager.getAppiumDevice().getWebkitProcessID() != null) {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String url = String.format("http://%s:" + getRemoteAppiumManagerPort(host)
                            + "/devices/ios/webkitproxy/stop"
                            + "?processID=%s", host,
                    AppiumDeviceManager.getAppiumDevice().getWebkitProcessID());
            new Api().getResponse(url);
            AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(null);
        }
    }


}

