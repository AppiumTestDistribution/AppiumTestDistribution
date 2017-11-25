package com.appium.utils;

import com.annotation.values.Author;
import org.testng.ITestResult;

/**
 * Created by saikrisv on 20/05/17.
 */
public class GetDescriptionForChildNode {
    private ITestResult methodName;
    private String description;
    private boolean methodNamePresent;
    private String descriptionMethodName;

    public GetDescriptionForChildNode(ITestResult methodName, String description) {
        this.methodName = methodName;
        this.description = description;
    }

    public boolean isMethodNamePresent() {
        return methodNamePresent;
    }

    public String getDescriptionMethodName() {
        return descriptionMethodName;
    }

    public GetDescriptionForChildNode invoke() {
        if (description.isEmpty()) {
            descriptionMethodName = methodName.getMethod().getMethodName();
        } else {
            descriptionMethodName = description;
        }
        if (methodName.getMethod().getConstructorOrMethod()
                .getMethod().getAnnotation(Author.class) != null) {
            methodNamePresent = true;
        } else {
            methodNamePresent = false;
        }
        return this;
    }
}
