package com.appium.manager;

import com.annotation.values.Author;
import com.appium.utils.GetDescriptionForChildNode;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * ReportManager - Handles all Reporting activities e.g communication with ExtentManager, etc
 */
public class ReportManager {

    private TestLogger testLogger;
    private AppiumDeviceManager appiumDeviceManager;
    private GetDescriptionForChildNode getDescriptionForChildNode;
    public String category = null;


    public ReportManager() throws IOException {
        testLogger = new TestLogger();
        appiumDeviceManager = new AppiumDeviceManager();
    }

    public void startLogResults(String methodName, String className) throws FileNotFoundException {
        testLogger.startLogging(methodName, className);
    }

    public HashMap<String, String> endLogTestResults(ITestResult result)
            throws Exception {
        return testLogger.endLog(result, appiumDeviceManager.getDeviceModel());
    }

    public void setAuthorName(ITestResult methodName) throws Exception {
        String authorName;
        String dataProvider = null;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String description = methodName.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(Test.class).description();
        Object dataParameter = methodName.getParameters();
        if (((Object[]) dataParameter).length > 0) {
            dataProvider = (String) ((Object[]) dataParameter)[0];
        }
        String descriptionMethodName;
        getDescriptionForChildNode = new GetDescriptionForChildNode(methodName, description)
                .invoke();
        methodNamePresent = getDescriptionForChildNode.isMethodNamePresent();
        descriptionMethodName = getDescriptionForChildNode.getDescriptionMethodName();
        if (System.getProperty("os.name").toLowerCase().contains("mac")
                && System.getenv("Platform").equalsIgnoreCase("iOS")
                || System.getenv("Platform")
                .equalsIgnoreCase("Both")) {
            category = appiumDeviceManager.getDeviceCategory();
        } else {
            category = appiumDeviceManager.getDeviceModel();
        }
        String testName = dataProvider == null ? descriptionMethodName
                : descriptionMethodName + "[" + dataProvider + "]";
        String udid = AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
        if (methodNamePresent) {
            authorName = methodName.getMethod()
                    .getConstructorOrMethod().getMethod()
                    .getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
        } else {
            // To Be Worked out
        }
    }
}
