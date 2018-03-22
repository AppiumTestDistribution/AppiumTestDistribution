package com.appium.manager;

import com.appium.utils.Api;
import com.appium.utils.CapabilityManager;
import com.appium.utils.OSType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.device.Device;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoteAppiumManager implements IAppiumManager {

    @Override
    public void destroyAppiumNode(String host) throws IOException {
        new Api().getResponse("http://" + host + ":4567"
                + "/appium/stop").body().string();

    }

    @Override
    public String getRemoteWDHubIP(String host) throws IOException {
        String hostIP = "http://" + host;
        String appiumRunningPort = new JSONObject(new Api().getResponse(hostIP + ":4567"
                + "/appium/isRunning").body().string()).get("port").toString();
        return hostIP + ":" + appiumRunningPort + "/wd/hub";
    }

    @Override
    public void startAppiumServer(String host) throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server on host " + host);
        System.out.println(
                "**************************************************************************\n");
        if(CapabilityManager.getInstance().getAppiumServerPath(host) == null) {
            System.out.println("Picking Default Path for AppiumServiceBuilder");
            new Api().getResponse("http://" + host + ":4567"
                    + "/appium/start").body().string();
        } else {
            System.out.println("Picking UserSpecified Path for AppiumServiceBuilder");
            String appiumServerPath = CapabilityManager.getInstance().getAppiumServerPath(host);
            new Api().getResponse("http://" + host + ":4567"
                    + "/appium/start?URL=" + appiumServerPath).body().string();
        }

        boolean status = Boolean.getBoolean(new JSONObject(new Api().getResponse("http://" + host + ":4567"
                + "/appium/isRunning").body().string()).get("status").toString());
        if (status) {
            System.out.println(
                    "***************************************************************\n");
            System.out.println("Appium Server started successfully on  " + host);
            System.out.println(
                    "****************************************************************\n");
        }
    }

    @Override
    public List<Device> getDevices(String machineIP, String platform) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Device> devices = null;
        if (platform.equalsIgnoreCase(OSType.ANDROID.name())) {
            devices = Arrays.asList(mapper.readValue(new URL(
                            "http://" + machineIP + ":4567/devices/android"),
                    Device[].class));
        } else if (platform.equalsIgnoreCase(OSType.iOS.name())) {
            devices = Arrays.asList(mapper.readValue(new URL(
                            "http://" + machineIP + ":4567/devices/ios"),
                    Device[].class));
        } else if (platform.equalsIgnoreCase(OSType.BOTH.name())) {
             devices = Arrays.asList(mapper.readValue(new URL(
                            "http://" + machineIP + ":4567/devices"),
                    Device[].class));
        }
        assert devices != null;
        return new ArrayList<>(devices);
    }

    @Override
    public Device getSimulator(String machineIP, String deviceName, String os) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = String.format("http://%s:4567/device/ios/simulator"
                        + "?simulatorName=%s&simulatorOSVersion=%s",
                machineIP, URLEncoder.encode(deviceName, "UTF-8"),
                URLEncoder.encode(os, "UTF-8"));
        Device device = mapper.readValue(new URL(url),
                Device.class);
        return device;
    }

    @Override
    public int getAvailablePort(String hostMachine) throws IOException {
        String url = String.format("http://%s:4567/machine/availablePort", hostMachine);
        Response response = new Api().getResponse(url);
        return Integer.parseInt(response.body().string());
    }

    @Override
    public int startIOSWebKitProxy(String host) throws Exception {
        int port = getAvailablePort(host);
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = String.format("http://%s:4567/devices/ios/webkitproxy/start"
                        + "?udid=%s&port=%s", host,
                AppiumDeviceManager.getAppiumDevice().getDevice().getUdid(),
                port);
        Response response = new Api().getResponse(url);
        AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(response.body().string());
        return port;
    }

    @Override
    public void destoryIOSWebKitProxy(String host) throws Exception {
        if(AppiumDeviceManager.getAppiumDevice().getWebkitProcessID() != null) {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String url = String.format("http://%s:4567/devices/ios/webkitproxy/stop"
                    + "?processID=%s", host, AppiumDeviceManager.getAppiumDevice().getWebkitProcessID());
            new Api().getResponse(url);
            AppiumDeviceManager.getAppiumDevice().setWebkitProcessID(null);
        }
    }
}

