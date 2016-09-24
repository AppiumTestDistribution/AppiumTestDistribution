package com.video.recorder;

import com.appium.utils.CommandPrompt;

import java.io.File;
import java.io.IOException;

/**
 * Created by saikrisv on 2016/09/23.
 */
public class Flick extends CommandPrompt {

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
        String methodName, String videoFileName) {
        String videoPath = System.getProperty("user.dir");
        String android;
        String ios;
        if (device_udid.length() != 40) {
            String videoLocationAndroid =
                videoPath + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationAndroid);
            if (command.equals("start")) {
                android = "flick video -a " + command + " -p android -u " + device_udid;
            } else {
                android =
                    "flick video -a " + command + " -p android -o " + videoLocationAndroid + " -n "
                        + videoFileName + " -u " + device_udid + " --trace";
            }

            System.out.println(android);
            try {
                runCommand(android);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String videoLocationIOS =
                videoPath + "/screenshot/iOS/" + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + methodName;
            fileDirectoryCheck(videoLocationIOS);
            if (command.equals("start")) {
                ios = "flick video -a " + command + " -p ios -u " + device_udid;
            } else {
                ios = "flick video -a " + command + " -p ios -o " + videoLocationIOS + "/target/"
                    + " -n " + videoFileName + " -u " + device_udid;
            }

            System.out.println(ios);
            try {
                runCommand(ios);
                Thread.sleep(10000);
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
}
