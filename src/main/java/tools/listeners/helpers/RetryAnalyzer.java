package tools.listeners.helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import static tools.properties.PropertiesHandler.getTestNG;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount = getTestNG().retryFailedTestAttempts();
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
