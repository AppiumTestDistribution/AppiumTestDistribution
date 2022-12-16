package com.appium.manager;

import com.appium.capabilities.Capabilities;
import com.appium.device.Device;
import com.appium.device.Devices;
import com.appium.device.HostMachineDeviceManager;
import com.appium.executor.ATDExecutor;
import com.appium.filelocations.FileLocations;
import com.appium.plugin.PluginClI;
import com.appium.plugin.PluginCliRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.appium.filelocations.FileLocations.SERVER_CONFIG;
import static com.appium.utils.ConfigFileManager.FRAMEWORK;
import static com.appium.utils.ConfigFileManager.RUNNER;
import static com.appium.utils.FigletHelper.figlet;
import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;
import static java.lang.System.getProperty;

public class ATDRunner {
    private static final String ANDROID = "android";
    private static final String BOTH = "both";
    private static final String IOS = "iOS";
    public static final String USER_DIR = "user.dir";

    private DeviceAllocationManager deviceAllocationManager;
    private ATDExecutor ATDExecutor;
    private Capabilities capabilities;
    private HostMachineDeviceManager hostMachineDeviceManager;
    private static final Logger LOGGER = Logger.getLogger(ATDRunner.class.getName());
    private FileWriter file;

    List<Device> deviceList;
    public ATDRunner() throws Exception {
        capabilities = Capabilities.getInstance();
        writeServiceConfig();
        LocalAppiumManager localAppiumManager = new LocalAppiumManager();
        localAppiumManager.startAppiumServer("127.0.0.1"); //Needs to be removed
        Devices devices = new Devices();
        Response connectedDevices = devices.getDevices();
        ObjectMapper mapper = new ObjectMapper();
        deviceList = mapper.readValue(connectedDevices.body().string(),
                new TypeReference<List<Device>>() {
                });
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        ATDExecutor = new ATDExecutor(deviceList);
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
        createOutputDirectoryIfNotExist();
    }

    private void writeServiceConfig() {
        JSONObject serverConfig = Capabilities.getInstance().getCapabilityObjectFromKey("serverConfig");
        try (FileWriter writer = new FileWriter(new File(
                getProperty("user.dir") + SERVER_CONFIG))) {
            writer.write(serverConfig.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOutputDirectoryIfNotExist() {
        File file = new File(System.getProperty(USER_DIR), FileLocations.OUTPUT_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public boolean runner(String pack, List<String> tests) throws Exception {
        figlet(RUNNER.get());
        return parallelExecution(pack, tests);
    }

    public boolean runner(String pack) throws Exception {
        return parallelExecution(pack, new ArrayList<>());
    }

    private boolean parallelExecution(String pack, List<String> tests) throws Exception {
        int deviceCount = deviceList.size();

        if (deviceCount == 0) {
            figlet("No Devices Connected");
            System.exit(0);
        }

        LOGGER.info(LOGGER.getName()
                + "Total Number of devices detected::" + deviceCount + "\n");

        createAppiumLogsFolder();
        createSnapshotDirectoryFor();
        String platform = getOverriddenStringValue("Platform");
        if (deviceAllocationManager.getDevices() != null && platform.equalsIgnoreCase(ANDROID)
                || platform.equalsIgnoreCase(BOTH)) {
            if (!capabilities.getCapabilityObjectFromKey("android").has("automationName")) {
                throw new IllegalArgumentException("Please set automationName "
                        + "as UIAutomator2 or Espresso to create Android driver");
            }
            generateDirectoryForAdbLogs();
        }

        boolean result = false;
        String runner = RUNNER.get();
        String framework = FRAMEWORK.get();

        if (framework.equalsIgnoreCase("testng")) {
            String executionType = runner.equalsIgnoreCase("distribute")
                    ? "distribute" : "parallel";
            result = ATDExecutor.constructXMLAndTriggerParallelRunner(tests, pack, deviceCount,
                    executionType);
        }
        return result;
    }

    private void generateDirectoryForAdbLogs() {
        File adb_logs = new File(System.getProperty(USER_DIR) + FileLocations.ADB_LOGS_DIRECTORY);
        if (!adb_logs.exists()) {
            try {
                adb_logs.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    private void createAppiumLogsFolder() {
        File f = new File(System.getProperty(USER_DIR) + FileLocations.APPIUM_LOGS_DIRECTORY);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    private void createSnapshotDirectoryFor() {
        List<AppiumDevice> udids = hostMachineDeviceManager.getDevicesByHost().getAllDevices();
        for (AppiumDevice udid : udids) {
            String os = udid.getDevice()
                    .getOs().equalsIgnoreCase(IOS) ? "iOS" : "Android";
            createPlatformDirectory(os);
            String deviceId = udid.getDevice().getUdid();
            File file = new File(
                    System.getProperty(USER_DIR)
                            + FileLocations.SCREENSHOTS_DIRECTORY
                            + os
                            + File.separator
                            + deviceId);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    private void createPlatformDirectory(String platform) {
        File platformDirectory = new File(System.getProperty(USER_DIR)
                + FileLocations.SCREENSHOTS_DIRECTORY + platform);
        if (!platformDirectory.exists()) {
            platformDirectory.mkdirs();
        }
    }

}