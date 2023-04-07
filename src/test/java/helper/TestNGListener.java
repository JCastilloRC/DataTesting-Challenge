package helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
public class TestNGListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
    protected Logger LOGGER = LogManager.getLogger("TestRunner");

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("- - - - - - - > START TEST CASE: " + iTestResult.getName());
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("Test successfully executed.");
        LOGGER.info("- - - - - - - > TEST CASE END \n");
    }
    @Override
    public void onTestFailure(ITestResult iTestResult){
        LOGGER.error("Test Failed with message: " + iTestResult.getThrowable().getMessage());
        LOGGER.info("- - - - - - - > TEST CASE END \n");
    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info("Test run: " + iTestContext.getAllTestMethods().length +
                    ", Passed: " + iTestContext.getPassedTests().size() +
                    ", Failures: " + iTestContext.getFailedTests().size() +
                    ", Skipped: " + iTestContext.getSkippedTests().size());
    }
}