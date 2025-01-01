package org.mine.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    //This variable is an instance variable (not marked as static) and is used to track how many times the current test has been retried.
    //It is not declared as final because its value needs to change during the execution of a test. Each time a test fails and is retried, retryCount increments by one.
    private int retryCount = 0;
    //constant that does not change and is shared across all instances of the RetryAnalyzer class.
    private static final int maxRetryCount = 3;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying for the "+retryCount+" time");
            return true; // Retry the test
        }
        return false; // Do not retry

    }
}
