package com.appium.filelocations;

public interface FileLocations {
    String OUTPUT_DIRECTORY = System.getenv("OUTPUT_DIRECTORY") != null
            ? "/" + System.getenv("OUTPUT_DIRECTORY") + "/" : "/target/";

    String PARALLEL_XML_LOCATION = OUTPUT_DIRECTORY + "parallel.xml";
    String REPORTS_DIRECTORY = OUTPUT_DIRECTORY +"reports/";

    String SCREENSHOTS_DIRECTORY = "screenshot/";
    String ANDROID_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "android/";
    String IOS_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "iOS/";

    String APPIUM_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "appiumlogs/";
    String ADB_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "adblogs/";
    String DEVICE_LOGS_DIRECTORY = "deviceLogs/";
    String DERIVED_DATA = OUTPUT_DIRECTORY + "derivedData/";
}
