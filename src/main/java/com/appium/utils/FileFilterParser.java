package com.appium.utils;


import com.appium.capabilities.Capabilities;
import com.appium.filelocations.FileLocations;
import com.epam.reportportal.service.ReportPortal;
import org.json.simple.JSONObject;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileFilterParser extends Helpers {

    private Map<String, Map<String, String>> getFilesFromDirectory(String directoryLocation,
                                                                   String[] fileTypes,
                                                                   String udid,
                                                                   ITestResult iTestResult)
            throws Exception {
        File file = new File(directoryLocation);
        if (!file.exists()) {
            throw new Exception(directoryLocation + " does not exist");
        }

        if (!file.isDirectory()) {
            throw new Exception(directoryLocation + " is not a directory");
        }


        FileFilter filter = inputFile -> {
            if (inputFile.isDirectory()) {
                return true;
            }
            for (String fileType : fileTypes) {
                if (inputFile.getAbsolutePath().toLowerCase().endsWith(fileType)) {
                    return true;
                }
            }
            return false;
        };

        HashMap<String, Map<String, String>> results = new HashMap<>();
        getFilesFromDirectory(file, filter, results, udid, iTestResult);
        return results;
    }

    private void getFilesFromDirectory(File inputDirectory, FileFilter filter, Map<String,
            Map<String, String>> results, String deviceUDID, ITestResult iTestResult) {
        File[] files = inputDirectory.listFiles(filter);
        for (File file : files) {
            if (file.isDirectory()) {
                getFilesFromDirectory(file, filter, results, deviceUDID, iTestResult);
            } else {
                if (file.getAbsolutePath().contains(deviceUDID)
                        && file.getAbsolutePath().contains(getCurrentTestMethodName())) {
                    if (!results.containsKey(inputDirectory.getName())) {
                        results.put(inputDirectory.getName(), new HashMap<>());
                    }
                    Map<String, String> values = results.get(inputDirectory.getName());
                    String screenName = file.getName().split("-")[1];
                    values.put(screenName.substring(0, screenName.indexOf("_")),
                            file.getAbsolutePath());
                    results.put(inputDirectory.getName(), values);
                }
            }
        }
    }

    public JSONObject getScreenShotPaths(String udid, ITestResult iTestResult) {
        String directoryLocation = System.getProperty("user.dir")
                                           + FileLocations.SCREENSHOTS_DIRECTORY;
        String[] fileTypes = {"png", "jpeg", "mov"};

        FileFilterParser fileFilterParser = new FileFilterParser();
        Map<String, Map<String, String>> filesFromDirectory = null;
        try {
            filesFromDirectory = fileFilterParser.getFilesFromDirectory(directoryLocation,
                    fileTypes, udid, iTestResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject list = new JSONObject();
        filesFromDirectory.forEach((key, values) -> {

            values.forEach((screenName, s) -> {
                if (s.contains("results") || s.contains("framed")) {
                    String path = s.split("target")[1];
                    Optional<String> atdHost;
                    Optional<String> atdPort;
                    try {
                        atdHost = Optional.ofNullable(Capabilities.getInstance()
                                .getMongoDbHostAndPort().get("atdHost"));
                        atdPort = Optional.ofNullable(Capabilities.getInstance()
                                .getMongoDbHostAndPort().get("atdPort"));
                        if (atdHost.isPresent() && atdPort.isPresent()) {
                            list.put(screenName, "http://" + getHostMachineIpAddress() + ":"
                                    + getRemoteAppiumManagerPort("127.0.0.1") + path);
                            ReportPortal.emitLog(screenName,
                                    "Info", new Date(), new File(System.getProperty("user.dir")
                                            + "/target/" + path));
                        } else {
                            ReportPortal.emitLog(screenName,
                                    "Info", new Date(), new File(System.getProperty("user.dir")
                                            + "/target/" + path));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.print(s + ", ");
                }
            });
        });
        return list;
    }
}
