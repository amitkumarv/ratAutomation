package com.icreon.res_allocqa.tests;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.pages.LogoutPage;
import com.relevantcodes.extentreports.LogStatus;
import java.lang.reflect.Method;
import base.testBase;

public class LogoutTest extends testBase {
	Method testMethod;
	LogoutPage lp;
	WebDriver webDriver;
	public LogoutTest() throws IOException {
		super();
	}

	@BeforeClass
	public void beforeClass() {
		// Check run mode
		if(ExHelper.getRunMode(getClass().getSimpleName())) {
			throw new SkipException("Skipping Test Logout");
		}
	}
	
	@BeforeTest
	public void testInit() {
		//init();
		this.webDriver = WebDriverFactory.getWebDriver();
	}
	
	@BeforeMethod
    public void handleTestMethodName(Method method)
    {
		
    }
	
	@Test(priority=100)
	public void testLogout() throws InterruptedException, IOException {
		LogoutPage lp = new LogoutPage(webDriver);
		setUpExtent("Test Logout", "Testing logout functunality : : Logging out");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		lp.clickLogout(extentTest);

		//Check Success
		if(lp.checkSuccess(extentTest)) {
			extentTest.log(LogStatus.PASS, "Logout Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Logout failed.");
		}	
		tearDownExtent();
		
	}
	
}
