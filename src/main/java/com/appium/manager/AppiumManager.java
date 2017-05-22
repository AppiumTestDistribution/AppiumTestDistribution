package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import io.appium.java_client.AppiumDriver;
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
    private ConfigurationManager prop;
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private static ThreadLocal<AppiumDriverLocalService> appiumDriverThreadLocal
            = new ThreadLocal<>();

    public static AppiumDriverLocalService getServer() {
        return appiumDriverThreadLocal.get();
    }

    static void setServer(AppiumDriverLocalService server) {
        appiumDriverThreadLocal.set(server);
    }


    AppiumManager() throws IOException {
        prop = ConfigurationManager.getInstance();
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        ap = new AvailablePorts();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */

    public void appiumServerForAndroid(String methodName)
        throws Exception {
        AppiumDriverLocalService appiumDriverLocalService;
        System.out.println(
            "**************************************************************************\n");
        System.out.println("Starting Appium Server to handle Android Device::" + DeviceUDIDManager.getDeviceUDID() + "\n");
        System.out.println(
            "**************************************************************************\n");
        int port = ap.getPort();
        int chromePort = ap.getPort();
        int bootstrapPort = ap.getPort();
        int selendroidPort = ap.getPort();
        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                System.getProperty("user.dir") + "/target/appiumlogs/" + DeviceUDIDManager.getDeviceUDID()
                    .replaceAll("\\W", "_") + "__" + methodName + ".txt"))
                .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, Integer.toString(chromePort))
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                    Integer.toString(bootstrapPort))
                .withIPAddress("127.0.0.1")
                .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                .withArgument(AndroidServerFlag.SELENDROID_PORT, Integer.toString(selendroidPort))
                .usingPort(port);
        /* and so on */
        ;
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        setServer(appiumDriverLocalService);
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

    public void appiumServerForIOS(String methodName)
            throws Exception {
        AppiumDriverLocalService appiumDriverLocalService;
        String webKitPort = iosDeviceConfiguration.startIOSWebKit();
        System.out
            .println("**********************************************************************\n");
        System.out.println("Starting Appium Server to handle IOS::" + DeviceUDIDManager.getDeviceUDID() + "\n");
        System.out
            .println("**********************************************************************\n");
        File classPathRoot = new File(System.getProperty("user.dir"));
        int port = ap.getPort();
        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                System.getProperty("user.dir") + "/target/appiumlogs/" + DeviceUDIDManager.getDeviceUDID()
                        + "__" + methodName + ".txt"))
                .withArgument(webKitProxy, webKitPort)
                .withIPAddress("127.0.0.1")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                .withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                    new File(String.valueOf(classPathRoot)).getAbsolutePath() + "/target/" + "tmp_"
                        + port).withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        setServer(appiumDriverLocalService);
    }

    public URL getAppiumUrl() {
        return getServer().getUrl();
    }

    public void destroyAppiumNode() {
        getServer().stop();
        if (getServer().isRunning()) {
            System.out.println("AppiumServer didn't shut... Trying to quit again....");
            getServer().stop();
        }
    }
}
