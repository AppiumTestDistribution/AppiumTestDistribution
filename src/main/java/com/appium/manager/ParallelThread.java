package com.appium.manager;

import static com.appium.manager.FigletHelper.figlet;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.cucumber.report.HtmlReporter;
import com.appium.executor.MyTestExecutor;
import com.appium.filelocations.FileLocations;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.schema.CapabilitySchemaValidator;
import com.appium.utils.AppiumDevice;
import com.appium.utils.CapabilityManager;
import com.appium.utils.HostMachineDeviceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * This class picks the devices connected
 * and distributes across multiple thread.
 *
 * Thanks to @Thote_Gowda(thotegowda.gr@gmail.com)
 */


public class ParallelThread {
    private static final String ANDROID = "android";
    private static final String BOTH = "Both";
    private static final String IOS = "iOS";

    private ConfigFileManager configFileManager;
    private DeviceAllocationManager deviceAllocationManager;
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    private MyTestExecutor myTestExecutor;
    private HtmlReporter htmlReporter;
    private CapabilityManager capabilityManager;
    private HostMachineDeviceManager hostMachineDeviceManager;

    public ParallelThread() throws Exception {
        capabilityManager = CapabilityManager.getInstance();
        new CapabilitySchemaValidator()
                .validateCapabilitySchema(capabilityManager.getCapabilities());
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        configFileManager = ConfigFileManager.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
        myTestExecutor = new MyTestExecutor();
        htmlReporter = new HtmlReporter();
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
        createOutputDirectoryIfNotExist();
    }

    private void createOutputDirectoryIfNotExist() {
        File file = new File(System.getProperty("user.dir"), FileLocations.OUTPUT_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println("OUTPUT DIRECTORY :" + file.getAbsolutePath());
    }

    @Deprecated
    //Use system property udids to send list of valid device ids
    public ParallelThread(List<String> validDeviceIds) throws Exception {
        this();
        androidDevice.setValidDevices(validDeviceIds);
        iosDevice.setValidDevices(validDeviceIds);
    }

    public boolean runner(String pack, List<String> tests) throws Exception {
        figlet(configFileManager.getProperty("RUNNER"));
        return triggerTest(pack, tests);
    }

    public boolean runner(String pack) throws Exception {
        return runner(pack, new ArrayList<>());
    }

    private boolean triggerTest(String pack, List<String> tests) throws Exception {
        return parallelExecution(pack, tests);
    }

    private boolean parallelExecution(String pack, List<String> tests) throws Exception {
        int deviceCount = hostMachineDeviceManager.getDevicesByHost().getAllDevices().size();

        if (deviceCount == 0) {
            figlet("No Devices Connected");
            System.exit(0);
        }

        System.out.println("***************************************************\n");
        System.out.println("Total Number of devices detected::" + deviceCount + "\n");
        System.out.println("***************************************************\n");
        System.out.println("starting running tests in threads");

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
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac") && platform.equalsIgnoreCase(IOS)
                || platform.equalsIgnoreCase(BOTH)) {
            //iosDevice.checkExecutePermissionForIOSDebugProxyLauncher();
        }

        List<Class> testcases = new ArrayList<>();

        boolean hasFailures = false;
        String runner = configFileManager.getProperty("RUNNER");
        String framework = configFileManager.getProperty("FRAMEWORK");


        if (framework.equalsIgnoreCase("testng")) {
            PackageUtil.getClasses(pack).stream().forEach(s -> {
                if (s.toString().contains("Test")) {
                    testcases.add((Class) s);
                }
            });
            String executionType = runner.equalsIgnoreCase("distribute")
                    ? "distribute" : "parallel";
            hasFailures = myTestExecutor
                    .runMethodParallelAppium(tests, pack, deviceCount,
                            executionType);
        }

        if (framework.equalsIgnoreCase("cucumber")) {
            if (runner.equalsIgnoreCase("distribute")) {
                myTestExecutor
                        .constructXmlSuiteDistributeCucumber(deviceCount);
                hasFailures = myTestExecutor.runMethodParallel();
            } else if (runner.equalsIgnoreCase("parallel")) {
                myTestExecutor
                        .constructXmlSuiteForParallelCucumber(deviceCount,
                                hostMachineDeviceManager.getDevicesByHost().getAllDevices());
                hasFailures = myTestExecutor.runMethodParallel();
                htmlReporter.generateReports();
            }
        }
        return hasFailures;
    }

    private void generateDirectoryForAdbLogs() {
        File adb_logs = new File(System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY);
        if (!adb_logs.exists()) {
            System.out.println("creating directory: " + "ADBLogs");
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
            System.out.println("creating directory: " + "Logs");
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
                if (file.mkdir()) {
                    System.out.println("Directory is created for device" + deviceId);
                } else {
                    System.out.println("Failed to create directory!");
                }
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

