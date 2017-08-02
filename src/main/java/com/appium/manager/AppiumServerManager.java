package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;
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
public class AppiumServerManager {

    private AvailablePorts ap;
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private static ThreadLocal<AppiumDriverLocalService> appiumDriverLocalService
            = new ThreadLocal<>();

    private static AppiumDriverLocalService getServer() {
        return appiumDriverLocalService.get();
    }

    private static void setServer(AppiumDriverLocalService server) {
        appiumDriverLocalService.set(server);
    }


    public AppiumServerManager() throws IOException {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        ap = new AvailablePorts();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */

    private void startAppiumServerForAndroid(String methodName)
            throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server to handle Android Device::"
                + DeviceManager.getDeviceUDID() + "\n");
        System.out.println(
                "**************************************************************************\n");
        AppiumDriverLocalService appiumDriverLocalService;
        int port = ap.getPort();
        int chromePort = ap.getPort();
        int bootstrapPort = ap.getPort();
        int selendroidPort = ap.getPort();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder().withAppiumJS(new File(ConfigFileManager
                        .configFileMap.get("APPIUM_JS_PATH")))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                        System.getProperty("user.dir") + "/target/appiumlogs/"
                                + DeviceManager.getDeviceUDID()
                                + "__" + methodName + ".txt"))
                        .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT,
                                Integer.toString(chromePort))
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                                Integer.toString(bootstrapPort))
                        .withIPAddress("127.0.0.1")
                        .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                        .withArgument(AndroidServerFlag.SELENDROID_PORT,
                                Integer.toString(selendroidPort))
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
        @Override
        public String getArgument() {
            return "--webkit-debug-proxy-port";
        }
    };

    private void startAppiumServerForIOS(String methodName)
            throws Exception {
        String webKitPort = iosDeviceConfiguration.startIOSWebKit();
        System.out
                .println("***********************************************************\n");
        System.out.println("Starting Appium Server to handle IOS::"
                + DeviceManager.getDeviceUDID() + "\n");
        System.out
                .println("***********************************************************\n");
        File classPathRoot = new File(System.getProperty("user.dir"));
        int port = ap.getPort();
        AppiumDriverLocalService appiumDriverLocalService;
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder().withAppiumJS(new File(ConfigFileManager
                        .configFileMap.get("APPIUM_JS_PATH")))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(new File(
                        System.getProperty("user.dir") + "/target/appiumlogs/"
                                + DeviceManager.getDeviceUDID()
                                + "__" + methodName + ".txt"))
                        .withArgument(webKitProxy, webKitPort)
                        .withIPAddress("127.0.0.1")
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                        .withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                                new File(String.valueOf(classPathRoot))
                                        .getAbsolutePath() + "/target/" + "tmp_"
                                        + port)
                        .usingPort(port);
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        setServer(appiumDriverLocalService);
    }

    public URL getAppiumUrl() {
        return getServer().getUrl();
    }

    private void destroyAppiumNode() {
        getServer().stop();
        if (getServer().isRunning()) {
            System.out.println("AppiumServer didn't shut... Trying to quit again....");
            getServer().stop();
        }
    }

    public void startAppiumServer(String methodName) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                startAppiumServerForIOS(methodName);
            } else {
                startAppiumServerForAndroid(methodName);
            }
        } else {
            startAppiumServerForAndroid(methodName);
        }
    }

    public void stopAppiumServer() throws IOException, InterruptedException {
        destroyAppiumNode();
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            iosDeviceConfiguration.destroyIOSWebKitProxy();
        }
    }
}
