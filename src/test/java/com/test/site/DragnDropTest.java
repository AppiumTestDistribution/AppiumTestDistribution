package com.test.site;

import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertNotNull;


@Test(groups = { "end-to-end-test" })
public class DragnDropTest extends UserBaseTest {
    @Test
    public void dragNDrop() {
        SessionId sessionId = getDriver().getSessionId();
        System.out.println("In DragAndDrop" + getDriver().getCapabilities().getCapability("udid"));
        System.out.println("In DragAndDrop" + Thread.currentThread().getId());
        assertNotNull(sessionId);
    }
}
