package com.kavya.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter{

	public ExtentTest extentTest;
	//Add customized system info
	public ExtentReports extentReports;
	//Configure look and feel of report
	ExtentSparkReporter extentReporter;

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		extentTest = extentReports.createTest(tr.getName());
		extentTest.pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		extentTest = extentReports.createTest(tr.getName());
		extentTest.fail("Test failed");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
	}

	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
		extentReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/extentreport.html");
		extentReporter.config().setTheme(Theme.DARK);
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentReporter);
		extentReports.setSystemInfo("Environment", "QA");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		extentReports.flush();
	}
}
