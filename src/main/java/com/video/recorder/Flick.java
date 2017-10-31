package com.video.recorder;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.manager.AppiumDeviceManager;
import com.appium.utils.CommandPrompt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by saikrisv on 2016/09/23.
 */
public class Flick extends CommandPrompt {

    public static ConcurrentHashMap<Long, Integer> androidScreenRecordProcess =
        new ConcurrentHashMap<>();
    AndroidDeviceConfiguration androidDeviceConfiguration = new AndroidDeviceConfiguration();
    Process screenRecord;

    /**
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     * @throws IOException
     * @throws InterruptedException
     */
    public void stopVideoRecording(String className, String methodName,
                                   String videoFileName) throws IOException, InterruptedException {
        System.out.println("**************Stopping Video Recording**************");
        flickRecordingCommand("stop", className, methodName, videoFileName);
    }

    /**
     *
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     * @throws IOException
     * @throws InterruptedException
     */
    public void startVideoRecording(String className, String methodName,
                                    String videoFileName) throws IOException, InterruptedException {
        System.out.println("**************Starting Video Recording**************");
        flickRecordingCommand("start", className, methodName, videoFileName);
    }

    public void flickRecordingCommand(String command,String className,
        String methodName, String videoFileName) throws IOException, InterruptedException {
        String videoPath = System.getProperty("user.dir");
        String android;
        String ios;
        if (AppiumDeviceManager.getDeviceUDID().length() != 40) {
            String videoLocationAndroid =
                videoPath + "/target/screenshot/android/"
                        + AppiumDeviceManager.getDeviceUDID() + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationAndroid);
            if (command.equals("start")) {
                try {
                    if (!androidDeviceConfiguration.getDeviceManufacturer()
                            .equals("unknown") && androidDeviceConfiguration
                            .checkIfRecordable()) {
                        screenRecord = Runtime.getRuntime()
                            .exec(androidDeviceConfiguration.screenRecord(methodName));
                        System.out.println(
                            "ScreenRecording has started..." + Thread.currentThread().getId());
                        androidScreenRecordProcess
                            .put(Thread.currentThread().getId(), getPid(screenRecord));
                        System.out.println("Process ID's:" + getPid(screenRecord));
                        Thread.sleep(1000);
                    } else {
                        android = "flick video -a " + command + " -p android -u "
                                + AppiumDeviceManager.getDeviceUDID();
                        runCommandThruProcess(android);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (!androidDeviceConfiguration.getDeviceManufacturer()
                        .equals("unknown")
                    && androidDeviceConfiguration.checkIfRecordable()) {
                    stopRecording();
                    androidDeviceConfiguration
                        .pullVideoFromDevice(methodName, videoLocationAndroid)
                        .removeVideoFileFromDevice(methodName);
                } else {
                    android = "flick video -a " + command + " -p android -o "
                            + videoLocationAndroid + " -n " + videoFileName
                            + " -u " + AppiumDeviceManager.getDeviceUDID() + " --trace";
                    runCommandThruProcess(android);
                    System.out.println("Stopping Video recording on Emulator");
                    Thread.sleep(10000);
                }
            }
        } else {
            String videoLocationIOS =
                videoPath + "/target/screenshot/iOS/" + AppiumDeviceManager.getDeviceUDID()
                        + "/" + className + "/" + methodName;
            fileDirectoryCheck(videoLocationIOS);
            if (command.equals("start")) {
                ios = "flick video -a " + command + " -p ios -u "
                        + AppiumDeviceManager.getDeviceUDID();
            } else {
                ios = "flick video -a " + command + " -p ios -o " + videoLocationIOS + " -n "
                    + videoFileName + " -u " + AppiumDeviceManager.getDeviceUDID();
            }

            System.out.println(ios);
            try {
                runCommand(ios);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void fileDirectoryCheck(String folderPath) {
        File f = new File(folderPath);
        f.mkdirs();
    }

    public int getPid(Process process) {
        try {
            Class<?> cProcessImpl = process.getClass();
            Field fPid = cProcessImpl.getDeclaredField("pid");
            if (!fPid.isAccessible()) {
                fPid.setAccessible(true);
            }
            return fPid.getInt(process);
        } catch (Exception e) {
            return -1;
        }
    }

    public void stopRecording() throws IOException {
        Integer processId = androidScreenRecordProcess.get(Thread.currentThread().getId());
        if (processId != -1) {
            String process = "pgrep -P " + processId;
            System.out.println(process);
            Process p2 = Runtime.getRuntime().exec(process);
            BufferedReader r = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String command = "kill " + processId;
            System.out.println("Stopping Video Recording");
            System.out.println("******************" + command);
            try {
                runCommandThruProcess(command);
                Thread.sleep(10000);
                System.out.println("Killed video recording with exit code :" + command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
