package com.video.recorder;

import com.appium.manager.AndroidDeviceConfiguration;
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
     * @param device_udid
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     * @throws IOException
     * @throws InterruptedException
     */
    public void stopVideoRecording(String device_udid, String className, String methodName,
        String videoFileName) throws IOException, InterruptedException {
        System.out.println("**************Stopping Video Recording**************");
        flickRecordingCommand("stop", device_udid, className, methodName, videoFileName);
    }

    /**
     * @param device_udid
     * @param className     - Current test class name
     * @param methodName    - Current test method name
     * @param videoFileName - filename should have TimeStamp,deviceID with TestMethod name
     * @throws IOException
     * @throws InterruptedException
     */
    public void startVideoRecording(String device_udid, String className, String methodName,
        String videoFileName) throws IOException, InterruptedException {
        System.out.println("**************Starting Video Recording**************");
        flickRecordingCommand("start", device_udid, className, methodName, videoFileName);
    }

    public void flickRecordingCommand(String command, String device_udid, String className,
        String methodName, String videoFileName) throws IOException, InterruptedException {
        String videoPath = System.getProperty("user.dir");
        String android = null;
        String ios;
        if (device_udid.length() != 40) {
            String videoLocationAndroid =
                videoPath + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationAndroid);
            if (command.equals("start")) {
                try {
                    if (androidDeviceConfiguration.checkIfRecordable(device_udid)
                        && !androidDeviceConfiguration.getDeviceManufacturer(device_udid)
                        .equals("Genymotion")) {
                        screenRecord = Runtime.getRuntime()
                            .exec(androidDeviceConfiguration.screenRecord(device_udid, methodName));
                        System.out.println(
                            "ScreenRecording has started..." + Thread.currentThread().getId());
                        androidScreenRecordProcess
                            .put(Thread.currentThread().getId(), getPid(screenRecord));
                        System.out.println("Process ID's:" + getPid(screenRecord));
                        Thread.sleep(1000);
                    } else {
                        android = "flick video -a " + command + " -p android -u " + device_udid;
                        runCommandThruProcess(android);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (androidDeviceConfiguration.checkIfRecordable(device_udid)
                    && !androidDeviceConfiguration.getDeviceManufacturer(device_udid)
                    .equals("Genymotion")) {
                    stopRecording();
                    System.out.println(
                        "Destroying the process::" + screenRecord.exitValue() + screenRecord
                            .isAlive());
                    androidDeviceConfiguration
                        .pullVideoFromDevice(device_udid, methodName, videoLocationAndroid);
                    Thread.sleep(5000);
                } else {
                    android = "flick video -a " + command + " -p android -o " + videoLocationAndroid
                        + " -n " + videoFileName + " -u " + device_udid + " --trace";
                    runCommandThruProcess(android);
                }
            }
        } else {
            String videoLocationIOS =
                videoPath + "/target/screenshot/iOS/" + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationIOS);
            if (command.equals("start")) {
                ios = "flick video -a " + command + " -p ios -u " + device_udid;
            } else {
                ios = "flick video -a " + command + " -p ios -o " + videoLocationIOS + " -n "
                    + videoFileName + " -u " + device_udid;
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
            String command = "kill -s SIGTSTP " + processId;
            System.out.println("Stopping Video Recording");
            System.out.println("******************" + command);
            Process killProcess = Runtime.getRuntime().exec(command);
            try {
                killProcess.waitFor();
                Thread.sleep(5000);
                System.out.println(
                    "Killed video recording with exit code :" + killProcess.exitValue()
                        + processId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
