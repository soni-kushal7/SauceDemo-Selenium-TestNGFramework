package listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;

public class ExtentReportListener implements ITestListener {

    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
        
        try {
            Object testClass = result.getInstance();
            if (testClass instanceof BaseTest) {
                WebDriver driver = ((BaseTest) testClass).getDriver();
                if (driver != null) {
                    String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
                    test.addScreenCaptureFromPath(screenshotPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
    
    public String takeScreenshot(WebDriver driver, String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/test-output/Screenshots/" + testName + "_" + timestamp + ".png";
        File finalDestination = new File(destination);
        
        // Ensure directory exists
        File directory = new File(System.getProperty("user.dir") + "/test-output/Screenshots/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Use Selenium's native FileHandler instead of Apache FileUtils
        FileHandler.copy(source, finalDestination);
        return destination;
    }
}
