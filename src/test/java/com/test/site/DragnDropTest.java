package com.test.site;

import org.testng.annotations.Test;

public class DragnDropTest extends UserBaseTest {

    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();

    }

    @Test
    public void dragNDropNew() {
        login("login").click();
        waitForElement("dragAndDrop").click();

    }

}
