package com.test.unit;

import com.appium.manager.DeviceAllocationManager;
import com.appium.utils.CapabilityManager;
import org.testng.annotations.Test;

public class CapabilityManagerTest {
    CapabilityManager capabilityManager = CapabilityManager.getInstance();

    public CapabilityManagerTest() throws Exception {
    }

    @Test
    public void testApp() throws Exception {
        DeviceAllocationManager.getInstance();

    }
}

