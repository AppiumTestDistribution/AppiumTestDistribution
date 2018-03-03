package com.test.unit;

import com.appium.utils.AppiumDevice;
import com.appium.utils.CapabilityManager;
import com.appium.utils.HostMachineDeviceManager;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

public class CapabilityManagerTest {

    public CapabilityManagerTest() throws Exception {
    }

    @Test
    public void testApp() throws Exception {
        List<AppiumDevice> allDevices = HostMachineDeviceManager.getInstance().getDevicesByHost().getAllDevices();
        System.out.println(allDevices);
    }
}

