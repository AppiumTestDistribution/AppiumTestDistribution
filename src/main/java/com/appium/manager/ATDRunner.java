package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.capabilities.CapabilityManager;
import com.appium.device.HostMachineDeviceManager;
import com.appium.executor.ATDExecutor;
import com.appium.filelocations.FileLocations;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.schema.CapabilitySchemaValidator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.appium.utils.ConfigFileManager.FRAMEWORK;
import static com.appium.utils.ConfigFileManager.RUNNER;
import static com.appium.utils.FigletHelper.figlet;

/*
 * This class picks the devices connected
 * and distributes across multiple thread.
 *
 * Thanks to @Thote_Gowda(thotegowda.gr@gmail.com)
 */
public class ATDRunner {
    private static final String ANDROID = "android";
    private static final String BOTH = "Both";
    private static final String IOS = "iOS";

    private DeviceAllocationManager deviceAllocationManager;
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    private ATDExecutor ATDExecutor;
    private CapabilityManager capabilityManager;
    private HostMachineDeviceManager hostMachineDeviceManager;
    private static final Logger LOGGER = Logger.getLogger(ATDRunner.class.getName());

    public ATDRunner() {
        capabilityManager = CapabilityManager.getInstance();
        new CapabilitySchemaValidator()
                .validateCapabilitySchema(capabilityManager.getCapabilities());
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
        ATDExecutor = new ATDExecutor(deviceAllocationManager);
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
        createOutputDirectoryIfNotExist();
    }

    private void createOutputDirectoryIfNotExist() {
        File file = new File(System.getProperty("user.dir"), FileLocations.OUTPUT_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Deprecated
    //Use system property udids to send list of valid device ids
    public ATDRunner(List<String> validDeviceIds) throws Exception {
        this();
        androidDevice.setValidDevices(validDeviceIds);
        iosDevice.setValidDevices(validDeviceIds);
    }

    public boolean runner(String pack, List<String> tests) throws Exception {
        figlet(RUNNER.get());
        return parallelExecution(pack, tests);
    }

    public boolean runner(String pack) throws Exception {
        return parallelExecution(pack, new ArrayList<>());
    }

    private boolean parallelExecution(String pack, List<String> tests) throws Exception {
        int deviceCount = hostMachineDeviceManager.getDevicesByHost().getAllDevices().size();

        if (deviceCount == 0) {
            figlet("No Devices Connected");
            System.exit(0);
        }

        LOGGER.info(LOGGER.getName()
            + "Total Number of devices detected::" + deviceCount + "\n");

        createAppiumLogsFolder();
        createSnapshotDirectoryFor();
        String platform = System.getenv("Platform");
        if (deviceAllocationManager.getDevices() != null && platform
                .equalsIgnoreCase(ANDROID)
                || platform.equalsIgnoreCase(BOTH)) {
            if (!capabilityManager.getCapabilityObjectFromKey("android")
                    .has("automationName")) {
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
        File adb_logs = new File(System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY);
        if (!adb_logs.exists()) {
            try {
                adb_logs.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    private void createAppiumLogsFolder() {
        File f = new File(System.getProperty("user.dir") + FileLocations.APPIUM_LOGS_DIRECTORY);
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
                    System.getProperty("user.dir")
                            + FileLocations.SCREENSHOTS_DIRECTORY + os + "/"
                            + deviceId);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    private void createPlatformDirectory(String platform) {
        File platformDirectory = new File(System.getProperty("user.dir")
                + FileLocations.SCREENSHOTS_DIRECTORY + platform);
        if (!platformDirectory.exists()) {
            platformDirectory.mkdirs();
        }
    }
}