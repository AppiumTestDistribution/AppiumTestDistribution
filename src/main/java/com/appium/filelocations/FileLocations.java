package com.appium.filelocations;

public interface FileLocations {
    String OUTPUT_DIRECTORY = "/target/";

    String PARALLEL_XML_LOCATION = OUTPUT_DIRECTORY + "parallel.xml";

    String EXTENT_REPORTS_LOCATION = OUTPUT_DIRECTORY + "ExtentReports.html";
    String EXTENT_XML_LOCATION = OUTPUT_DIRECTORY + "extent.xml";

    String SCREENSHOTS_DIRECTORY = OUTPUT_DIRECTORY + "screenshot/";
    String ANDROID_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "android/";
    String IOS_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "iOS/";

    String APPIUM_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "appiumlogs/";
    String ADB_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "adblogs/";
}
