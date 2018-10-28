package com.appium.executor;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.CommandPrompt;
import java.io.IOException;

/**
 * Created by saikrisv on 30/05/17.
 */
public class IOSDeviceTest {

    IOSDeviceConfiguration iosDeviceConfiguration;
    CommandPrompt cmd;

    public IOSDeviceTest() throws IOException {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        cmd = new CommandPrompt();
    }
}
