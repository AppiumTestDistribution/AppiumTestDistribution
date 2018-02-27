package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.cucumber.report.HtmlReporter;
import com.appium.executor.MyTestExecutor;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.HostMachineDeviceManager;
import com.report.factory.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.appium.manager.FigletHelper.figlet;

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

    private static final String ADB_LOGS_DIR = "/target/adblogs/";
    private static final String APPIUM_LOGS_DIR = "/target/appiumlogs/";
    private static final String SCREENSHOT_DIR = "/target/screenshot/";

    private ConfigFileManager configFileManager;
    private DeviceAllocationManager deviceAllocationManager;
    Map<String, String> devices = new HashMap<String, String>();
    ArrayList<String> iOSdevices = new ArrayList<>();
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    private MyTestExecutor myTestExecutor;
    private HtmlReporter htmlReporter;
    ExtentManager extentManager;
    private AppiumDriverManager appiumDriverManager;
    private ArtifactsUploader artifactsUploader;

    public ParallelThread() throws Exception {
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        configFileManager = ConfigFileManager.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
        myTestExecutor = new MyTestExecutor();
        htmlReporter = new HtmlReporter();
        extentManager = new ExtentManager();
        appiumDriverManager = new AppiumDriverManager();
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
        String os = System.getProperty("os.name").toLowerCase();
        String platform = System.getenv("Platform");
        HostMachineDeviceManager hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
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

        if (deviceAllocationManager.getDevices() != null && platform
                .equalsIgnoreCase(ANDROID)
                || platform.equalsIgnoreCase(BOTH)) {
            generateDirectoryForAdbLogs();
            createSnapshotFolderAndroid("android");
        }

        if (os.contains("mac") && platform.equalsIgnoreCase(IOS)
                || platform.equalsIgnoreCase(BOTH)) {
            //iosDevice.checkExecutePermissionForIOSDebugProxyLauncher();
            createSnapshotFolderiOS("iPhone");
        }

        List<Class> testcases = new ArrayList<>();

        boolean hasFailures = false;
        String runner = configFileManager.getProperty("RUNNER");
        String framework = configFileManager.getProperty("FRAMEWORK");


        if (framework.equalsIgnoreCase("testng")) {
            // final String pack = "com.paralle.tests"; // Or any other package
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
            //addPluginToCucumberRunner();
            if (runner.equalsIgnoreCase("distribute")) {
                myTestExecutor
                        .constructXmlSuiteDistributeCucumber(deviceCount);
                hasFailures = myTestExecutor.runMethodParallel();
            } else if (runner.equalsIgnoreCase("parallel")) {
                //addPluginToCucumberRunner();
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
        File adb_logs = new File(System.getProperty("user.dir") + ADB_LOGS_DIR);
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
        File f = new File(System.getProperty("user.dir") + APPIUM_LOGS_DIR);
        if (!f.exists()) {
            System.out.println("creating directory: " + "Logs");
            try {
                f.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    private void createSnapshotFolderAndroid(String platform) throws Exception {
        for (int i = 1; i <= (devices.size() / 4); i++) {
            String deviceSerial = devices.get("deviceID" + i);
            if (deviceSerial != null) {
                createPlatformDirectory(platform);
                File file = new File(
                        System.getProperty("user.dir") + SCREENSHOT_DIR + platform + "/"
                                + deviceSerial);
                if (!file.exists()) {
                    if (file.mkdir()) {
                        System.out.println("Android " + deviceSerial + " Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }
            }
        }
    }

    private void createSnapshotFolderiOS(String platform) {
        for (int i = 0; i < iOSdevices.size(); i++) {
            String deviceSerial = iOSdevices.get(i);
            createPlatformDirectory(platform);
            File file = new File(
                    System.getProperty("user.dir") + SCREENSHOT_DIR + platform + "/"
                            + deviceSerial);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("IOS " + deviceSerial + " Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
        }
    }


    private void createPlatformDirectory(String platform) {
        File platformDirectory = new File(System.getProperty("user.dir")
                + SCREENSHOT_DIR + platform);
        if (!platformDirectory.exists()) {
            platformDirectory.mkdirs();
        }
    }

    public void addPluginToCucumberRunner() throws IOException {
        File dir = new File(System.getProperty("user.dir") + "/src/test/java/output/");
        System.out.println("Getting all files in " + dir.getCanonicalPath()
                + " including those in subdirectories");
        List<File> files =
                (List<File>) FileUtils.listFiles(dir,
                        TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            BufferedReader read = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            ArrayList list = new ArrayList();

            String dataRow = read.readLine();
            while (dataRow != null) {
                list.add(dataRow);
                dataRow = read.readLine();
            }

            FileWriter writer = new FileWriter(
                    file.getAbsoluteFile());
            writer.append("package output;");

            for (int i = 0; i < list.size(); i++) {
                writer.append(System.getProperty("line.separator"));
                writer.append((CharSequence) list.get(i));
            }
            writer.flush();
            writer.close();

            Path path = Paths.get(file.getAbsoluteFile().toString());
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.add(12, "plugin = {\"com.cucumber.listener.ExtentCucumberFormatter:\"},");
            Files.write(path, lines, StandardCharsets.UTF_8);
        }
    }


}

