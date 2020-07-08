package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass= TestUtil.class,dataProvider="dp")
	public void openAccountTest(Hashtable<String, String> data)
			throws InterruptedException {
		
		if (!TestUtil.isTestRunnable("openAccountTest", excel)) {

			throw new SkipException("Skipping the test " + "openAccountTest".toUpperCase() + "as the Run mode is No");
		}

	
		
		click("opnActBtn");
		select("custDrop",data.get("customer"));
		select("currencyDrop",data.get("currency"));
		click("processBtn");
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

	}

	

}
