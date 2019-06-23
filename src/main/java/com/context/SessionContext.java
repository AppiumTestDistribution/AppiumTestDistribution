package com.context;

import org.testng.ITestResult;

import java.util.HashMap;

public class SessionContext {
    static final String TEST_RUNNER = "testrunner";
    private static final HashMap<String, TestExecutionContext> allTestsExecutionContext;

    static {
        System.out.println("SessionContext default constructor");
        new SessionContext();
        allTestsExecutionContext = new HashMap<>();
        System.out.println("Initialized SessionContext");
    }

    static synchronized void addContext(long threadId, TestExecutionContext testExecutionContext) {
        allTestsExecutionContext.put(String.valueOf(threadId), testExecutionContext);
        System.out.println(String.format("Adding context for thread - %s", threadId));
    }

    public static synchronized TestExecutionContext getTestExecutionContext(long threadId) {
        return allTestsExecutionContext.get(String.valueOf(threadId));
    }

    public static synchronized void remove(long threadId, ITestResult testResult) {
        System.out.println(String.format("Removing context for thread - %s", threadId));
        TestExecutionContext testExecutionContext = getTestExecutionContext(threadId);
        allTestsExecutionContext.remove(String.valueOf(threadId));
    }
}
