package com.test.site;

import com.appium.utils.ScreenShotManager;
import org.testng.annotations.Test;

import java.io.IOException;

public class DragnDropTest extends  UserBaseTest {

    @Test
    public void dragNDrop() throws IOException, InterruptedException {
        login("login").click();
        new ScreenShotManager().captureScreenShot("DrangNDropScreen");
    }

}
