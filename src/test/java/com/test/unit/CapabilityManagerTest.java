package com.test.unit;

import com.appium.manager.ArtifactsUploader;
import com.appium.manager.HostArtifact;
import com.appium.utils.CapabilityManager;
import org.testng.annotations.Test;

import java.util.List;

public class CapabilityManagerTest {
    CapabilityManager capabilityManager = CapabilityManager.getInstance();

    public CapabilityManagerTest() throws Exception {
    }

    @Test
    public void testApp() throws Exception {
        List<AppiumDevice> allDevices = HostMachineDeviceManager.getInstance().getDevicesByHost().getAllDevices();
        System.out.println(allDevices);
    }
}

