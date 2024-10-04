package tools.listeners.testng.helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import tools.properties.Properties;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount = Properties.testNG.retryFailedTestAttempts();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            result.setStatus(ITestResult.FAILURE);
            return true;
        }
        return false;
    }
}
