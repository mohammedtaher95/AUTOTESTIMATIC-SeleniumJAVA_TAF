package tools.listeners.junit;

import junit.runner.TestRunListener;
import utilities.EmailableReportGenerator;
import utilities.LoggingManager;

public class JunitListener implements TestRunListener {
    @Override
    public void testRunStarted(String testSuiteName, int testCount) {

    }

    @Override
    public void testRunEnded(long elapsedTime) {

    }

    @Override
    public void testRunStopped(long elapsedTime) {

    }

    @Override
    public void testStarted(String testName) {

    }

    @Override
    public void testEnded(String testName) {

        LoggingManager.info("Success of test cases and its details are : " + testName);
        LoggingManager.endTestCase(testName);
    }

    @Override
    public void testFailed(int status, String testName, String trace) {

    }
}
