package com.appium.utils.ios;

import com.appium.utils.CommandPrompt;
import com.dd.plist.*;
import net.dongliu.apk.parser.ApkParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ansonliao on 13/5/2016.
 */
public class IOSUtils {
    private static CommandPrompt commandPrompt = new CommandPrompt();

    public static final String IPA_BUNDLE_ID = "CFBundleIdentifier";
    public static final String IPA_BUNDLE_NAME = "CFBundleName";
    public static final String IPA_BUNDLE_VER = "CFBundleVersion";
    public static final String IPA_BUNDLE_DISPLAY_NAME = "CFBundleDisplayName";
    public static final String IPA_BUNDLE_MIN_VER = "MinimumOSVersion";

    public static synchronized String getDeviceName(String devicdUDID){
        String deviceName = null;

        try {
            deviceName = commandPrompt.runCommand(
                    "idevicename --udid " + devicdUDID).replace("\\W", "_");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceName;
    }

    public static synchronized String getIOSVersion(String devicdUDID){
        String iOSVerison = null;

        try {
            iOSVerison = commandPrompt.runCommandThruProcessBuilder(
                    "ideviceinfo --udid " + devicdUDID + " | grep ProductVersion | awk '{print $2}'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iOSVerison;
    }

    public static synchronized String getDeviceClass(String deviceUDID) {
        String deviceClass = "";

        try {
            deviceClass = commandPrompt.runCommandThruProcessBuilder(
                    "ideviceinfo --udid " + deviceUDID + " | grep DeviceClass | awk '{print $2}'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceClass;
    }

//    public static synchronized Map<String, String> getIPAInfo(String ipaFilePath) {
//        Map<String, String> ipaInfo = new HashMap<>();
//        UnzipUtility unzipper = new UnzipUtility();
//
//        File ipaFile = new File(ipaFilePath);
//        String ipaAbsPath = ipaFile.getAbsolutePath();
//        String ipaDirectory = new File(ipaAbsPath).getParent();
////        String ipaFileNameWithoutExt = FilenameUtils.removeExtension(ipaFile.getName());
//
//        // remove old Payload folder if existed
//        if (new File(ipaDirectory + "/Payload").exists()) {
//            try {
//                commandPrompt.runCommandThruProcessBuilder("rm -rf " + new File(ipaDirectory + "/Payload"));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // unzip ipa zip file
//        try {
//            unzipper.unzip(ipaAbsPath, ipaDirectory);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // fetch app file name, after unzip ipa
//        String appFileName = "";
//        outter: for (File file : new File(ipaDirectory + "/Payload").listFiles()) {
//            if (file.toString().endsWith(".app")) {
//                appFileName = new File(file.toString()).getAbsolutePath();
//                break outter;
//            }
//        }
//
//        String plistFilePath = appFileName + "/info.plist";
//
//        // parse info.plist
//        File plistFile = new File(plistFilePath);
//        NSDictionary rootDict = null;
//        try {
//            rootDict = (NSDictionary) PropertyListParser.parse(plistFile);
//
//            // get bundle id
//            NSString parameter = (NSString) rootDict.objectForKey(IPA_BUNDLE_ID);
//            ipaInfo.put(IPA_BUNDLE_ID, parameter.toString());
//
//            // get application name
//            parameter = (NSString) rootDict.objectForKey(IPA_BUNDLE_NAME);
//            ipaInfo.put(IPA_BUNDLE_NAME, parameter.toString());
//
//            // get version
//            parameter = (NSString) rootDict.objectForKey(IPA_BUNDLE_VER);
//            ipaInfo.put(IPA_BUNDLE_VER, parameter.toString());
//
//            // get bundle display name
//            parameter = (NSString) rootDict.objectForKey(IPA_BUNDLE_DISPLAY_NAME);
//            ipaInfo.put(IPA_BUNDLE_DISPLAY_NAME, parameter.toString());
//
//            // get ios mini. version
//            parameter = (NSString) rootDict.objectForKey(IPA_BUNDLE_MIN_VER);
//            ipaInfo.put(IPA_BUNDLE_MIN_VER, parameter.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (PropertyListFormatException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//
//        // remove unzip folder
//        try {
//            commandPrompt.runCommandThruProcessBuilder("rm -rf " + new File(ipaDirectory + "/Payload"));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return ipaInfo;
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ApkParser apkParser = new ApkParser(new File("/Users/AnsonLiao/Desktop/Maaii.apk"));
        System.out.println(apkParser.getManifestXml());
    }
}