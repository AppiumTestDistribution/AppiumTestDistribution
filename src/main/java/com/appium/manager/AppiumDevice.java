package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.github.device.Device;
import com.video.recorder.AppiumScreenRecordFactory;
import com.video.recorder.IScreenRecord;
import org.openqa.selenium.logging.LogEntries;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.stream.StreamSupport;

public class AppiumDevice {

    private static final String AVAILABLE = "AVAILABLE";
    private static final String BUSY = "BUSY";
    private final Device device;
    private int port;
    private int chromeDriverPort;
    private String webkitProcessID;
    private String deviceState;
    private final String ipAddress;

    public AppiumDevice(Device device, String ipAddress) {
        this.device = device;
        this.ipAddress = ipAddress;
        deviceState = AVAILABLE;
        chromeDriverPort = 0; //Setting as Zero initially
    }

    public int getChromeDriverPort() {
        return chromeDriverPort;
    }

    public void setChromeDriverPort(int chromeDriverPort) {
        this.chromeDriverPort = chromeDriverPort;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Device getDevice() {
        return device;
    }

    public String getHostName() {
        return ipAddress;
    }

    public boolean isLocalDevice() {
        return ipAddress.equals("127.0.0.1");
    }

    public boolean isAvailable() {
        return deviceState.equalsIgnoreCase(AVAILABLE);
    }

    public void blockDevice() {
        deviceState = BUSY;
    }

    public void freeDevice() {
        deviceState = AVAILABLE;
    }

    public String getWebkitProcessID() {
        return webkitProcessID;
    }

    public void setWebkitProcessID(String webkitProcessID) {
        this.webkitProcessID = webkitProcessID;
    }

    private boolean isNativeAndroid() {
        return AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                && AppiumDriverManager.getDriver().getCapabilities()
                .getCapability("browserName") == null;
    }

    public void startDataCapture(String specName, Integer scenarioRunCount)
            throws IOException, InterruptedException {
        String scenarioName = specName.replaceAll(" ", "_");
        if (isNativeAndroid()) {
            String udid = this.getDevice().getUdid();
            String fileName = String.format("/%s-run-%s", udid, scenarioRunCount);
            File logFile = createFile( FileLocations.REPORTS_DIRECTORY
                    + scenarioName
                    + File.separator
                    + FileLocations.DEVICE_LOGS_DIRECTORY,
                    fileName);
            PrintStream logFileStream = new PrintStream(logFile);
            LogEntries logcatOutput = AppiumDriverManager.getDriver().manage().logs().get("logcat");
            StreamSupport.stream(logcatOutput.spliterator(), false).forEach(logFileStream::println);
        }
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            IScreenRecord videoRecording = AppiumScreenRecordFactory.recordScreen();
            videoRecording.startVideoRecording(scenarioName, scenarioName, scenarioName);
        }
    }

    private File createFile(String dirName, String fileName) {
        File logFile = new File(System.getProperty("user.dir")
                 + dirName
                + fileName + ".txt");
        if (logFile.exists()) {
            return logFile;
        }
        try {
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logFile;
    }
}
