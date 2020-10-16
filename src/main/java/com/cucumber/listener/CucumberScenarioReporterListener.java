package com.cucumber.listener;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.AppiumServerManager;
import com.appium.manager.DeviceAllocationManager;
import com.appium.manager.DeviceSingleton;
import com.appium.utils.ImageUtils;
import com.epam.reportportal.cucumber.ScenarioReporter;
import com.epam.reportportal.service.Launch;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.video.recorder.XpathXML;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.core.gherkin.Step;
import rp.com.google.common.base.Suppliers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CucumberScenarioReporterListener extends ScenarioReporter {

    private DeviceAllocationManager deviceAllocationManager;
    public AppiumServerManager appiumServerManager;
    public AppiumDriverManager appiumDriverManager;
    public DeviceSingleton deviceSingleton;
    public LinkedList<Step> testSteps;
    public AppiumDriver<MobileElement> appium_driver;
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    public String deviceModel;
    public ImageUtils imageUtils = new ImageUtils();
    public XpathXML xpathXML = new XpathXML();
    private String CI_BASE_URI = null;

    private static final Map<String, String> MIME_TYPES_EXTENSIONS =
        new HashMap() {
            {
                this.put("image/bmp", "bmp");
                this.put("image/gif", "gif");
                this.put("image/jpeg", "jpg");
                this.put("image/png", "png");
                this.put("image/svg+xml", "svg");
                this.put("video/ogg", "ogg");
            }
        };

    public CucumberScenarioReporterListener() throws Exception {
        System.out.println("CucumberScenarioReporterListener");
        appiumServerManager = new AppiumServerManager();
        appiumDriverManager = new AppiumDriverManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        deviceSingleton = DeviceSingleton.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        androidDevice = new AndroidDeviceConfiguration();
    }

    @Override
    protected void startRootItem() {
        this.rootSuiteId =
            Suppliers.memoize(
                () -> {
                    StartTestItemRQ rq = new StartTestItemRQ();
                    rq.setName("My Tests");
                    rq.setStartTime(Calendar.getInstance().getTime());
                    rq.setType("SUITE");
                    return ((Launch) this.launch.get()).startTestItem(rq);
                });
    }
}
