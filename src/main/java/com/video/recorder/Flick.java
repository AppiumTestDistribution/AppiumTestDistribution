package com.video.recorder;

import com.appium.filelocations.FileLocations;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.utils.CommandPrompt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * Created by saikrisv on 2016/09/23.
 */
public class Flick extends CommandPrompt implements IScreenRecord {
    private static final Logger LOGGER = Logger.getLogger(Flick.class.getSimpleName());

    /**
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     */
    public void stopVideoRecording(String className, String methodName,
                                   String videoFileName, AppiumDevice device) {
        LOGGER.info("**************Stopping Video Recording**************");
        flickRecordingCommand("stop", className, methodName, videoFileName, device);

    }

    /**
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     */
    public void startVideoRecording(String className, String methodName,
                                    String videoFileName, AppiumDevice device) {
        LOGGER.info("**************Starting Video Recording**************");
        flickRecordingCommand("start", className, methodName, videoFileName, device);
    }

    private void flickRecordingCommand(String command, String className,
                                       String methodName, String videoFileName,
                                       AppiumDevice appiumDevice) {
        String videoPath = System.getProperty("user.dir");
        AtomicReference<String> android = new AtomicReference<>();
        ;
        AtomicReference<String> ios = new AtomicReference<>();
        List<AppiumDevice> appiumDevices = AppiumDeviceManager.getAppiumDevices();
        if (appiumDevice.getDevice().getUdid().length() != 40) {
            String videoLocationAndroid =
                videoPath + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                    + appiumDevice.getDevice().getUdid() + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationAndroid);
            if (command.equals("start")) {
                try {
                    android.set("flick video -a " + command + " -p android -u "
                        + appiumDevice.getDevice().getUdid());
                    runCommandThruProcess(android.get());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                android.set("flick video -a " + command + " -p android -o "
                    + videoLocationAndroid + " -n " + videoFileName
                    + " -u " + appiumDevice
                    .getDevice().getUdid() + " --trace");
                try {
                    runCommandThruProcess(android.get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Stopping Video recording on Emulator");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } else {
            String videoLocationIOS =
                videoPath + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                    + appiumDevice.getDevice().getUdid()
                    + "/" + className + "/" + methodName;
            fileDirectoryCheck(videoLocationIOS);
            if (command.equals("start")) {
                ios.set("flick video -a " + command + " -p ios -u "
                    + appiumDevice.getDevice().getUdid());
            } else {
                ios.set("flick video -a " + command + " -p ios -o " + videoLocationIOS + " -n "
                    + videoFileName + " -u " + appiumDevice
                    .getDevice().getUdid());
            }

            System.out.println(ios.get());
            try {
                runCommand(ios.get());
                Thread.sleep(5000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void fileDirectoryCheck(String folderPath) {
        File f = new File(folderPath);
        f.mkdirs();
    }
}
