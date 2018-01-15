package com.test.unit;

import com.appium.utils.CapabilityManager;
import org.testng.annotations.Test;

import java.util.*;

public class CapabilityManagerTest {
    CapabilityManager capabilityManager = CapabilityManager.getInstance();

    public CapabilityManagerTest() throws Exception {
    }

    @Test
    public void verifyCapabilityForKeyHostMachine() {
        Object hostMachines = capabilityManager.getCapabilityFromKey("hostMachines");

        System.out.println(hostMachines);
    }


}
