package com.appium.device;

import com.appium.capabilities.Capabilities;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class HostMachineDeviceManagerTest {
    Capabilities capabilities = Mockito.mock(Capabilities.class);

    @Test(
            expectedExceptions = RuntimeException.class,
            expectedExceptionsMessageRegExp = "Provide hostMachine in Caps.json for execution"
    )
    public void shouldThrowErrorWhenHostMachineIsNotDefinedInCapabilities() {
        when(capabilities.hasHostMachines()).thenReturn(false);
        new HostMachineDeviceManager(capabilities);
    }
}

