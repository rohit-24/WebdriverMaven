package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
	//	verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		click("bmLoginBtn");
		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn"))), "Login not successfull");
		Thread.sleep(3000);
		//log.debug("Login Test successfully executed");
		
	}
	
	
	/*
	 * @Test public void loginAsCustomer() throws InterruptedException {
	 * 
	 * driver.findElement(By.xpath(OR.getProperty("cstLoginBtn"))).click();
	 * Thread.sleep(3000);
	 * 
	 * }
	 */
	
	

}
