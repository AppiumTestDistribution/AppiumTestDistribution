package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumManager {

    private AvailablePorts ap;
    public AppiumDriverLocalService appiumDriverLocalService;
    private ConfigurationManager prop;
    private IOSDeviceConfiguration iosDeviceConfiguration;


    AppiumManager() throws IOException {
        prop = ConfigurationManager.getInstance();
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        ap = new AvailablePorts();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */

    public void appiumServerForAndroid(String deviceID, String methodName)
        throws Exception {
        System.out.println(
            "**************************************************************************\n");
        System.out.println("Starting Appium Server to handle Android Device::" + deviceID + "\n");
        System.out.println(
            "**************************************************************************\n");
        int port = ap.getPort();
        int chromePort = ap.getPort();
        int bootstrapPort = ap.getPort();
        int selendroidPort = ap.getPort();
        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                System.getProperty("user.dir") + "/target/appiumlogs/" + deviceID
                    .replaceAll("\\W", "_") + "__" + methodName + ".txt"))
                .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, Integer.toString(chromePort))
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                    Integer.toString(bootstrapPort))
                .withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                .withArgument(AndroidServerFlag.SELENDROID_PORT, Integer.toString(selendroidPort))
                .usingPort(port);
        /* and so on */
        ;
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */
    ServerArgument webKitProxy = new ServerArgument() {
        @Override public String getArgument() {
            return "--webkit-debug-proxy-port";
        }
    };

    public void appiumServerForIOS(String deviceID, String methodName)
            throws Exception {
        String webKitPort = iosDeviceConfiguration.startIOSWebKit(deviceID);
        System.out
            .println("**********************************************************************\n");
        System.out.println("Starting Appium Server to handle IOS::" + deviceID + "\n");
        System.out
            .println("**********************************************************************\n");
        File classPathRoot = new File(System.getProperty("user.dir"));
        int port = ap.getPort();
        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                System.getProperty("user.dir") + "/target/appiumlogs/" + deviceID
                        + "__" + methodName + ".txt"))
                .withArgument(webKitProxy, webKitPort)
                .withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                .withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                    new File(String.valueOf(classPathRoot)).getAbsolutePath() + "/target/" + "tmp_"
                        + port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
    }

    public URL getAppiumUrl() {
        return appiumDriverLocalService.getUrl();
    }

    public void destroyAppiumNode() {
        appiumDriverLocalService.stop();
        if (appiumDriverLocalService.isRunning()) {
            System.out.println("AppiumServer didn't shut... Trying to quit again....");
            appiumDriverLocalService.stop();
        }
    }
}
