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

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {

		if (!data.get("runmode").equals("Y")) {

			throw new SkipException("Skipping the test case since run mode is No");
		}

		click("addCustBtn");
		type("firstNameTxt", data.get("FirstName"));
		type("lastNameTxt", data.get("LastName"));
		type("postCodeTxt", data.get("Postcode"));
		click("addBtn");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alertText")));
		alert.accept();
		Thread.sleep(2000);

	}

}
