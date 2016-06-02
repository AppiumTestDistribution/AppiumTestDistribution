package com.annotation.values;

import com.appium.manager.AvailabelPorts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class SampleIOS extends AvailabelPorts {

    public static Properties prop = new Properties();
    public InputStream input = null;
    public DesiredCapabilities capabilities = new DesiredCapabilities();
    public AppiumDriverLocalService appiumDriverLocalService;
    public AppiumDriver<MobileElement> driver;

    @BeforeClass public void setUp() throws Exception {
        String deviceID = "889e89b3367e426324c4ff4b7b1d49e4d765f99d";
        File classPathRoot = new File(System.getProperty("user.dir"));
        input = new FileInputStream("config.properties");
        prop.load(input);
        int port = getPort();
        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                System.getProperty("user.dir") + "/target/appiumlogs/" + deviceID + "__"
                    + "sampletest.txt"))
                .withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                    new File(String.valueOf(classPathRoot)).getAbsolutePath() + "/target/" + "tmp_"
                        + port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
    /* and so on */
        ;
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "karthik");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities
            .setCapability(MobileCapabilityType.APP, "/Users/saikrisv/Downloads/WordPress25-1.ipa");
        capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
        capabilities.setCapability("bundleId", "com.tesco.sample");
        capabilities.setCapability("autoAcceptAlerts", true);

        driver = new IOSDriver<MobileElement>(appiumDriverLocalService.getUrl(), capabilities);


    }


    @Test public void testApp() throws InterruptedException {
        Thread.sleep(5000);
    }


    @AfterClass public void tearDown() {
        appiumDriverLocalService.stop();
    }


}
