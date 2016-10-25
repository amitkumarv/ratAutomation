package com.icreon.res_allocqa.tests;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.icreon.res_allocqa.pages.LoginPage;
import com.relevantcodes.extentreports.LogStatus;
import java.lang.reflect.Method;
import base.testBase;

public class LoginTest extends testBase {
	Method testMethod;
	LoginPage lp;
	WebDriver webDriver;
	public LoginTest() throws IOException {
		super();
	}


	@BeforeClass
	public void beforeClass() {
		System.out.println("login test");
		// Check run mode
		if(ExHelper.getRunMode(getClass().getSimpleName())) {
			throw new SkipException("Skipping Test One");
		}
	}
	
	@BeforeTest
	public void testInit() {
		init();
		this.webDriver = driver;
	}
	
	@Test(priority=1)
	public void testLogin() throws InterruptedException, IOException {
		setUpExtent("Login", "Testing login functunality : : Login page");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		LoginPage lp = new LoginPage(webDriver);
		lp.fillInUsername(extentTest);
		lp.fillInPassword(extentTest);
		lp.clickLoginButton(extentTest);
		
		if(lp.checkSuccess(extentTest)) {
			extentTest.log(LogStatus.PASS, "Login successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Login failed.");
		}
	
		tearDownExtent();
		
	}
	
}
