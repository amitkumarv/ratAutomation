package com.icreon.res_allocqa.pages;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.ExtentManager;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.icreon.res_allocqa.utilities.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginPage {
	SeleniumUtils st;
	WebDriver driver;
	
	By LOGIN_BUTTON = By.xpath(".//*[@id='login_form']/div/ul/li[4]/input");
	By LOGOUT_LINK = By.className("logout");
	
	public LoginPage(WebDriver webDriver) {
		driver = WebDriverFactory.getWebDriver();
		st = new SeleniumUtils();
	}
	
	// Enter username in login form
	public void fillInUsername(ExtentTest extentTest) throws IOException, InterruptedException {
		st.enterText(extentTest, driver,By.id("user_name"), 5, "Usernsme", PropertiesUtils.getPropVal("user.username").trim());
	}
	
	// Enter password in login form
	public void fillInPassword(ExtentTest extentTest) throws IOException, InterruptedException {
		st.enterText(extentTest, driver,By.id("password"), 5, "Password", PropertiesUtils.getPropVal("user.password").trim());
	}
	
	// Click login button of login form
	public void clickLoginButton(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, LOGIN_BUTTON, 5, "Login button");
	}
	
	// Checking login success
	public boolean checkSuccess(ExtentTest extentTest) throws IOException {
		return st.checkElementExist(extentTest, driver, LOGOUT_LINK, 5, "Login button");
	}
	

}
