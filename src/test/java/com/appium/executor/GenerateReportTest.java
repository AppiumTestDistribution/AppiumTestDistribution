package com.appium.executor;

import com.appium.utils.ImageUtils;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by saikrisv on 13/05/17.
 */
public class GenerateReportTest {

    @Test
    public void generateReportJson() throws Exception {
        ImageUtils.creatResultsSet();
    }
}
