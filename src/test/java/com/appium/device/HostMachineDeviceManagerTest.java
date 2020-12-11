package com.appium.device;

import com.appium.capabilities.Capabilities;
import com.appium.manager.AppiumManagerFactory;
import com.appium.manager.IAppiumManager;
import com.appium.manager.LocalAppiumManager;
import com.github.device.Device;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class HostMachineDeviceManagerTest {

    private static final String CAPABILITIES_JSON = "{\n"
            + "  \"android\": {\n"
            + "    \"platformName\": \"android\",\n"
            + "    \"platformVersion\": \"7.0\",\n"
            + "    \"automationName\": \"UiAutomator2\",\n"
            + "    \"uiautomator2ServerInstallTimeout\": 50000,\n"
            + "    \"adbExecTimeout\": 60000,\n"
            + "    \"app\": {\n"
            + "      \"local\": \"https://github.com/shridharkalagi/AppiumSample/raw/master/VodQA.apk\"\n"
            + "    },\n"
            + "    \"deviceName\": \"Pixel_3a_API_30_x86\",\n"
            + "    \"noSign\": true\n"
            + "  },\n"
            + "  \"hostMachines\": [\n"
            + "    {\n"
            + "      \"machineIP\": \"127.0.0.1\"\n"
            + "    }\n"
            + "  ]\n"
            + "}\n";

    @Test(
            expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Provide hostMachine in Caps.json"
                    + " for execution"
    )
    public void shouldThrowErrorWhenHostMachineIsNotDefinedInCapabilities() {
        Capabilities capabilities = Mockito.mock(Capabilities.class);
        when(capabilities.hasHostMachines()).thenReturn(false);
        new HostMachineDeviceManager(null, capabilities, null);
    }

    @Test
    public void shouldIncludeAndroidDevices() throws IOException {
        Capabilities capabilities = new Capabilities(CAPABILITIES_JSON);
        AtdEnvironment atdEnvironment = Mockito.mock(AtdEnvironment.class);
        AppiumManagerFactory appiumManagerFactory = Mockito.mock(AppiumManagerFactory.class);
        IAppiumManager appiumManager = Mockito.mock(IAppiumManager.class);
        Device androidDevice = new Device();
        androidDevice.setName("Pixel_3a_API_30_x86");
        androidDevice.setAvailable(true);
        androidDevice.setOs("android");

        when(atdEnvironment.getEnv("Platform")).thenReturn("android");
        when(appiumManager.getDevices("127.0.0.1", "android")).thenReturn(Arrays.asList(androidDevice));
        when(appiumManagerFactory.getAppiumManagerFor("127.0.0.1")).thenReturn(appiumManager);

        HostMachineDeviceManager hostMachineDeviceManager = new HostMachineDeviceManager(
                appiumManagerFactory, capabilities, atdEnvironment);
        DevicesByHost devicesByHost = hostMachineDeviceManager.getDevicesByHost();

        Assert.assertEquals(devicesByHost.getAllDevices().size(), 1);
    }
}

