package tools.listeners.testng.helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import tools.engineconfigurations.Configurations;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount = Configurations.testNG.retryFailedTestAttempts();

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
