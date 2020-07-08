package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {

		test = report.startTest(result.getName().toUpperCase());
	}


		

	public void onTestSuccess(ITestResult result) {

		test.log(LogStatus.PASS, result.getName().toUpperCase()+ " PASS");
		report.endTest(test);
		report.flush();

	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed with execption : " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		Reporter.log("Click to see screenshot");
		Reporter.log("<a target=\"_blank\"href=" + TestUtil.screenshotName + ">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\"href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
				+ " height=100 width=100></img></a>");

		report.endTest(test);
		report.flush();

	}

	public void onTestSkipped(ITestResult result) {

		test.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped the test case as the Run mode is NO");
		report.endTest(test);
		report.flush();

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
