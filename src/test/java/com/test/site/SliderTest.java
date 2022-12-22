package com.test.site;

import org.testng.annotations.Test;

@Test(groups = { "end-to-end-test" })
public class SliderTest extends UserBaseTest {
    @Test
    public void dragNDrop() {
        waitForElement("login").click();
        waitForElement("dragAndDrop").click();
        waitForElement("dragMe");
    }
}
