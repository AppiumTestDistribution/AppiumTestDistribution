package com.appium.manager;

import com.appium.cucumber.report.HtmlReporter;
import com.appium.executor.MyTestExecutor;
import com.appium.ios.IOSDeviceConfiguration;
import com.github.lalyos.jfiglet.FigletFont;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class picks the devices connected
 * and distributes across multiple thread.
 *
 * Thanks to @Thote_Gowda(thotegowda.gr@gmail.com)
 */


public class ParallelThread {
    private ConfigurationManager configurationManager;
    private DeviceManager deviceManager;
    protected int deviceCount = 0;
    Map<String, String> devices = new HashMap<String, String>();
    Map<String, String> iOSdevices = new HashMap<String, String>();
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    private MyTestExecutor myTestExecutor;
    List<Class> testcases;
    private HtmlReporter htmlReporter;

    public ParallelThread() throws IOException {
        deviceManager = DeviceManager.getInstance();
        configurationManager = ConfigurationManager.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
        myTestExecutor = new MyTestExecutor();
        htmlReporter = new HtmlReporter();
    }

    public ParallelThread(List<String> validDeviceIds) throws IOException {
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
        configurationManager = ConfigurationManager.getInstance();
        androidDevice.setValidDevices(validDeviceIds);
        iosDevice.setValidDevices(validDeviceIds);
        myTestExecutor = new MyTestExecutor();
        htmlReporter = new HtmlReporter();
    }

    public boolean runner(String pack, List<String> tests) throws Exception {
        figlet(configurationManager.getProperty("RUNNER"));
        return triggerTest(pack, tests);
    }

    public boolean runner(String pack) throws Exception {
        return runner(pack, new ArrayList<String>());
    }

    public boolean triggerTest(String pack, List<String> tests) throws Exception {
        return parallelExecution(pack, tests);
    }

    public boolean parallelExecution(String pack, List<String> tests) throws Exception {
        String operSys = System.getProperty("os.name").toLowerCase();
        File f = new File(System.getProperty("user.dir") + "/target/appiumlogs/");
        if (!f.exists()) {
            System.out.println("creating directory: " + "Logs");
            boolean result = false;
            try {
                f.mkdir();
                result = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        if (androidDevice.getDevices() != null) {
            devices = androidDevice.getDevices();
            deviceCount = devices.size() / 4;
            File adb_logs = new File(System.getProperty("user.dir") + "/target/adblogs/");
            if (!adb_logs.exists()) {
                System.out.println("creating directory: " + "ADBLogs");
                boolean result = false;
                try {
                    adb_logs.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }
            createSnapshotFolderAndroid(deviceCount, "android");
        }

        if (operSys.contains("mac")) {
            if (IOSDeviceConfiguration.deviceUDIDiOS.size() > 0) {
                iosDevice.checkExecutePermissionForIOSDebugProxyLauncher();
                iOSdevices = iosDevice.getIOSUDIDHash();
                deviceCount += iOSdevices.size();
                createSnapshotFolderiOS(deviceCount, "iPhone");
            }
        }
        if (deviceCount == 0) {
            figlet("No Devices Connected");
            System.exit(0);
        }
        System.out.println("***************************************************\n");
        System.out.println("Total Number of devices detected::" + deviceCount + "\n");
        System.out.println("***************************************************\n");
        System.out.println("starting running tests in threads");

        testcases = new ArrayList<Class>();

        boolean hasFailures = false;
        if (configurationManager.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            // final String pack = "com.paralle.tests"; // Or any other package
            PackageUtil.getClasses(pack).stream().forEach(s -> {
                if (s.toString().contains("Test")) {
                    testcases.add((Class) s);
                }
            });

            if (configurationManager.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
                hasFailures = myTestExecutor
                        .runMethodParallelAppium(tests, pack, deviceCount,
                                "distribute");

            }
            if (configurationManager.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
                hasFailures = myTestExecutor
                        .runMethodParallelAppium(tests, pack, deviceCount,
                                "parallel");
            }
        }

        if (configurationManager.getProperty("FRAMEWORK").equalsIgnoreCase("cucumber")) {
            //addPluginToCucumberRunner();
            if (configurationManager.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
                myTestExecutor
                        .constructXmlSuiteDistributeCucumber(deviceCount,
                                deviceManager.getDevices());
                hasFailures = myTestExecutor.runMethodParallel();
            } else if (configurationManager.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
                //addPluginToCucumberRunner();
                myTestExecutor
                        .constructXmlSuiteForParallelCucumber(deviceCount,
                                deviceManager.getDevices());
                hasFailures = myTestExecutor.runMethodParallel();
                htmlReporter.generateReports();
            }
        }
        return hasFailures;
    }

    public void createSnapshotFolderAndroid(int deviceCount, String platform) throws Exception {
        for (int i = 1; i <= (devices.size() / 4); i++) {
            String deviceSerial = devices.get("deviceID" + i);
            if (deviceSerial != null) {
                createPlatformDirectory(platform);
                File file = new File(
                        System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
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

    public void createSnapshotFolderiOS(int deviceCount, String platform) {
        for (int i = 0; i < iOSdevices.size(); i++) {
            String deviceSerial = iOSdevices.get("deviceID" + i);
            createPlatformDirectory(platform);
            File file = new File(
                    System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
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


    public void createPlatformDirectory(String platform) {
        File file2 = new File(System.getProperty("user.dir") + "/target/screenshot");
        if (!file2.exists()) {
            file2.mkdir();
        }

        File file3 = new File(System.getProperty("user.dir") + "/target/screenshot/" + platform);
        if (!file3.exists()) {
            file3.mkdir();
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

    public static void figlet(String text) {
        String asciiArt1 = null;
        try {
            asciiArt1 = FigletFont.convertOneLine(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(asciiArt1);
    }
}
