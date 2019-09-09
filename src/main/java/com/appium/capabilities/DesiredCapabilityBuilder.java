package com.appium.capabilities;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.utils.ArtifactsUploader;
import com.appium.utils.AvailablePorts;
import com.appium.utils.HostArtifact;
import com.appium.utils.JsonParser;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.JSONObject;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.ResourceUtils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by saikrisv on 20/05/17.
 */
public class DesiredCapabilityBuilder extends ArtifactsUploader {

    private AvailablePorts availablePorts;
    private List<AppiumDevice> appiumDevices;

    public static ThreadLocal<List<DesiredCapabilities>> desiredCapabilitiesThreadLocal
        = new ThreadLocal<>();

    public static ThreadLocal<DesiredCapabilities> desiredCapabilitieThreadLocal
        = new ThreadLocal<>();

    public DesiredCapabilityBuilder() {
        super();
        availablePorts = new AvailablePorts();
    }

    public static List<DesiredCapabilities> getDesiredCapabilities() {
        return desiredCapabilitiesThreadLocal.get();
    }

    public static DesiredCapabilities getDesiredCapability() {
        return desiredCapabilitieThreadLocal.get();
    }

    public void buildDesiredCapability(String jsonPath) throws Exception {
        appiumDevices = AppiumDeviceManager.getAppiumDevices();
        List<DesiredCapabilities> capabilities = new ArrayList<>();
        for (AppiumDevice appiumDevice : appiumDevices) {
            int port = appiumDevice.getPort();
            String platform = appiumDevice.getDevice().getOs();
            boolean isCloud = appiumDevice.getDevice().isCloud();
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            if (isCloud) {
                desiredCapabilities = desiredCapabilityForCloud(platform, jsonPath,
                    desiredCapabilities, appiumDevice);
            } else {
                desiredCapabilities = desiredCapabilityForLocalAndRemoteATD(platform, jsonPath,
                    port, desiredCapabilities, appiumDevice);
            }
            capabilities.add(desiredCapabilities);
        }
        desiredCapabilitiesThreadLocal.set(capabilities);
    }

    private DesiredCapabilities desiredCapabilityForCloud(String platform, String jsonPath,
                                                          DesiredCapabilities desiredCapabilities,
                                                          AppiumDevice appiumDevice) {
        JSONObject platFormCapabilities = new JsonParser(jsonPath).getObjectFromJSON()
            .getJSONObject(platform);
        platFormCapabilities.keySet().forEach(key -> {
            capabilityObject(desiredCapabilities, platFormCapabilities, key, appiumDevice);
        });
        desiredCapabilities.setCapability("device",
            appiumDevice.getDevice().getName());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
            appiumDevice.getDevice().getName());
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        desiredCapabilities.setCapability(CapabilityType.VERSION,
            appiumDevice.getDevice().getOsVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
            appiumDevice.getDevice().getOsVersion());
        return desiredCapabilities;
    }

    private void capabilityObject(DesiredCapabilities desiredCapabilities,
                                  JSONObject platFormCapabilities, String key,
                                  AppiumDevice device) {
        String appCapability = "app";
        if (appCapability.equals(key)) {
            Object values = platFormCapabilities.get(appCapability);
            List<HostArtifact> hostArtifacts = ArtifactsUploader.getInstance()
                .getHostArtifacts();
            String hostAppPath = hostAppPath(values, hostArtifacts, device);
            Path path = FileSystems.getDefault().getPath(hostAppPath);
            if (device.getDevice().isCloud()
                || ResourceUtils.isUrl(hostAppPath)) {
                desiredCapabilities.setCapability(appCapability, hostAppPath);
            } else {
                desiredCapabilities.setCapability(appCapability,
                    path.normalize().toAbsolutePath().toString());
            }
        } else {
            desiredCapabilities.setCapability(key, platFormCapabilities.get(key));
        }
    }

    private DesiredCapabilities desiredCapabilityForLocalAndRemoteATD(String platform,
                                                                      String jsonPath,
                                                                      int port,
                                                                      DesiredCapabilities desiredCapabilities,
                                                                      AppiumDevice device)
        throws Exception {
        JSONObject platFormCapabilities = new JsonParser(jsonPath).getObjectFromJSON()
            .getJSONObject(platform);

        platFormCapabilities.keySet().forEach(key -> {
            if ("browserName".equals(key) && "chrome".equals(platFormCapabilities.getString(key))) {
                try {
                    device.setChromeDriverPort(
                        availablePorts.getAvailablePort(device.getHostName()));
                    desiredCapabilities.setCapability("chromeDriverPort",
                        device.getChromeDriverPort());
                } catch (Exception e) {
                    throw new RuntimeException("Unable to allocate chromedriver with unique port");
                }
            }
            capabilityObject(desiredCapabilities, platFormCapabilities, key, device);
        });

        if (AppiumDeviceManager.getMobilePlatform(device.getDevice().getUdid()).equals(MobilePlatform.ANDROID)) {
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                port);
            appPackage(desiredCapabilities);
        } else if (AppiumDeviceManager.getMobilePlatform(device.getDevice().getUdid()).equals(MobilePlatform.IOS)) {
            appPackageBundle(desiredCapabilities);
            //Check if simulator.json exists and add the deviceName and OS
            if (device.getDevice().getUdid().length()
                == IOSDeviceConfiguration.SIM_UDID_LENGTH) {

                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                    device.getDevice().getName());
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                    device.getDevice().getOsVersion());
                desiredCapabilities.setCapability("webkitDebugProxyPort",
                    new AvailablePorts().getAvailablePort(device.getHostName()));
            } else {
                desiredCapabilities.setCapability("webkitDebugProxyPort",
                    new AvailablePorts().getAvailablePort(device.getHostName()));
            }

            desiredCapabilities.setCapability(IOSMobileCapabilityType
                .WDA_LOCAL_PORT, port);
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.IOS_XCUI_TEST);
            desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                device.getDevice().getUdid());
        }
        desiredCapabilities.setCapability(MobileCapabilityType.UDID,
            device.getDevice().getUdid());
        return desiredCapabilities;
    }

    private String hostAppPath(Object values, List<HostArtifact> hostArtifacts, AppiumDevice device) {
        HostArtifact hostArtifact;
        hostArtifact = hostArtifacts.stream().filter(s ->
            s.getHost()
                .equalsIgnoreCase(device
                    .getHostName()))
            .collect(toList()).parallelStream()
            .findFirst().get();
        String appPath = hostArtifact.getArtifactPath("APK");
        if (values instanceof JSONObject) {
            if (!device
                .getDevice().isCloud()) {
                int length = device
                    .getDevice()
                    .getUdid()
                    .length();
                if (length == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                    appPath = hostArtifact.getArtifactPath("APP");
                } else if (length == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                    appPath = hostArtifact.getArtifactPath("IPA");
                }
            } else {
                if (isEmpty(appPath) || (((JSONObject) values).has("simulator")
                    || ((JSONObject) values).has("device"))) {
                    appPath = hostArtifact.getArtifactPath(((JSONObject) values)
                        .has("simulator")
                        ? "APP" : "IPA");
                }
            }
        }
        return appPath;
    }

    private void appPackage(DesiredCapabilities desiredCapabilities) {
        if (System.getenv("APP_PACKAGE") != null) {
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                System.getenv("APP_PACKAGE"));
        }
    }

    private void appPackageBundle(DesiredCapabilities iOSCapabilities) {
        if (System.getenv("APP_PACKAGE") != null) {
            iOSCapabilities
                .setCapability(IOSMobileCapabilityType.BUNDLE_ID,
                    System.getenv("APP_PACKAGE"));
        }
    }
}
