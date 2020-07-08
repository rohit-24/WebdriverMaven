package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() throws IOException {

		if (driver == null) {

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\resources\\properties\\Config.properties");

			config.load(fis);
			log.debug("Config File loaded !!!");

			fis = new FileInputStream(System.getProperty("user.dir") + "\\resources\\properties\\OR.properties");

			OR.load(fis);
			log.debug("OR File loaded !!!");
		}

		if (config.getProperty("browser").equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome launched !!!");
		}

		else if (config.getProperty("browser").equals("firefox")) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to : " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implict.wait")),
				TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 5);

	}

	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		}

		catch (NoSuchElementException e) {

			return false;

		}
	}

	public void click(String locator) {

		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		test.log(LogStatus.INFO, "Clicking on : "+locator);
	}

	public void type(String locator, String value) {

		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "Typing in  : "+locator+ " entered value as "+value);
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		
		dropdown= driver.findElement(By.xpath(OR.getProperty(locator)));
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		test.log(LogStatus.INFO, "Selecting from dropdown : "+locator+ " value as "+value);
	}
	
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		
		try{
			
			Assert.assertEquals(actual, expected);
		
		}
		
		catch(Throwable t) {
			
			TestUtil.captureScreenshot();
			
			Reporter.log("<br>"+"Verification failure :"+ t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\"href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=100 width=100></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			
			test.log(LogStatus.FAIL, " Verification failed with execption : " +t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			
		}
	}
	

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

		log.debug("Execution Completed !!! ");

	}
}
