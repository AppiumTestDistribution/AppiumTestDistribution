package com.test.unit;

import com.appium.manager.DeviceAllocationManager;
import com.appium.utils.CapabilityManager;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import org.testng.annotations.Test;

public class CapabilityManagerTest {
    CapabilityManager capabilityManager = CapabilityManager.getInstance();

    public CapabilityManagerTest() throws Exception {
    }

    @Test
    public void testApp() throws Exception {
        DevicesByHost instance = HostMachineDeviceManager.getInstance();
        System.out.println(instance);

    }
}

