package com.context;


import java.util.HashMap;
import java.util.logging.Logger;
import org.testng.ITestResult;

public class SessionContext {
    static final String TEST_RUNNER = "testrunner";
    private static final HashMap<String, TestExecutionContext> allTestsExecutionContext;
    private static final Logger LOGGER = Logger.getLogger(SessionContext.class.getSimpleName());

    static {
        LOGGER.info("SessionContext default constructor");
        new SessionContext();
        allTestsExecutionContext = new HashMap<>();
        LOGGER.info("Initialized SessionContext");
    }

    static synchronized void addContext(long threadId, TestExecutionContext testExecutionContext) {
        allTestsExecutionContext.put(String.valueOf(threadId), testExecutionContext);
        LOGGER.info(String.format("Adding context for thread - %s", threadId));
    }

    public static synchronized TestExecutionContext getTestExecutionContext(long threadId) {
        return allTestsExecutionContext.get(String.valueOf(threadId));
    }

    public static synchronized void remove(long threadId, ITestResult testResult) {
        LOGGER.info(String.format("Removing context for thread - %s", threadId));
        TestExecutionContext testExecutionContext = getTestExecutionContext(threadId);
        allTestsExecutionContext.remove(String.valueOf(threadId));
    }
}
