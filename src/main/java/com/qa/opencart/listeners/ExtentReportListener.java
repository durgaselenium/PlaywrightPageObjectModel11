package com.qa.opencart.listeners;

import static com.qa.opencart.factory.PlaywrightFactory.takescreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportListener implements ITestListener {

	private static final String OUTPUT_FOLDER = "./build/";
	private static final String FILE_NAME     = "TestExecutionReport.html";
	
	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest>test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
	
	private static ExtentReports init() {
	Path path = Paths.get(OUTPUT_FOLDER);
	 //if directory exists?
	if(!Files.exists(path)) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			//fail to create Directory
			e.printStackTrace();
		}
	}
	extentReports = new ExtentReports();
	ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
	reporter.config().setReportName("open cart Automation Test Results");
	extentReports.attachReporter(reporter);
	extentReports.setSystemInfo("System", "Window");
	extentReports.setSystemInfo("Author", "Durga Sridevi Veeravalli");
	extentReports.setSystemInfo("Build#", "1.1");
	extentReports.setSystemInfo("Team", "Oms");
	extentReports.setSystemInfo("Customer Name", "NAL");
	return extentReports;
	
	}
	
	
	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite Started!");
	}
	
	@Override
	public synchronized void onFinish(ITestContext context) {
	   System.out.println("Test Suite is Ending!");
		extent.flush();
		test.remove();
	}
	
	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName =  result.getMethod().getQualifiedName();
		
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid+1, last);
		System.out.println(methodName +"started!");
		
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		extentTest.assignCategory(className);
		
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
		
	}
	
	 @Override
	 public synchronized void onTestSuccess(ITestResult result) {
	 System.out.println(result.getMethod().getMethodName() + "Passed!");	 
	 test.get().pass("Test Passed");
//	 test.get().pass(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(takescreenshot()).build());
     test.get().getModel().setEndTime(getTime(result.getEndMillis()));	 
	 }
	 

	 @Override
	  public synchronized void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + "failed!");
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(takescreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		}
	 

		@Override
	   public synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + "skipped!");
		test.get().skip(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(takescreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		}

		@Override
		public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentagefor" + result.getMethod().getMethodName());	
		}

	  private java.util.Date getTime(long millis) {
		  Calendar calender = Calendar.getInstance();
		  calender.setTimeInMillis(millis);
		  return calender.getTime();
	  }
}
