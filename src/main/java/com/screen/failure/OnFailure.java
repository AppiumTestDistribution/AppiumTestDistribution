package com.screen.failure;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.09.14
 */
public class OnFailure extends TestListenerAdapter {


    @Step("Hi, I'm step in your testng listener")
    @Override
    public void onTestFailure(ITestResult tr) {
    	//bs.makeScreenshot();
    }

    @Attachment("Hi, I'm attachment in your testng listener")
    public String createAttachment() {
        return "My own attachment body!";
    }
    
    
}