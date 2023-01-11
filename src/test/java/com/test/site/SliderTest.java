package com.test.site;

import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

@Test(groups = { "end-to-end-test" })
public class SliderTest extends UserBaseTest {
    @Test
    public void slideTest() {
        SessionId sessionId = getDriver().getSessionId();
        System.out.println("In slideTest" + getDriver().getCapabilities().getCapability("udid"));
        System.out.println("In slideTest" + Thread.currentThread().getId());
        assertNotNull(sessionId);
    }
}
