package com.appium.device;

import com.appium.capabilities.Capabilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class HostMachineDeviceManagerTest {
    Capabilities capabilities = Mockito.mock(Capabilities.class);

    @Test(expectedExceptionsMessageRegExp = "Provide hostMachine in Caps.json for execution")
    public void shouldThrowErrorWhenHostMachineIsNotDefinedInCapabilities() {
        when(capabilities.hasHostMachines()).thenReturn(false);
        new HostMachineDeviceManager(capabilities);
    }
}

