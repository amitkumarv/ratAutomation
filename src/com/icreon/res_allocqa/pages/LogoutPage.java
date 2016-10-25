package com.icreon.res_allocqa.pages;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class LogoutPage {
	SeleniumUtils st;
	WebDriver driver;
	
	By LOGOUT_LINK = By.className("logout");
	By LOGIN_BUTTON = By.xpath(".//*[@id='login_form']/div/ul/li[4]/input");
	
	public LogoutPage(WebDriver webDriver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	public void clickLogout(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, LOGOUT_LINK, 5, "Logout link");
	}
	
	public boolean checkSuccess(ExtentTest extentTest) throws IOException {
		return st.checkElementExist(extentTest, driver, LOGIN_BUTTON, 5, "Login button");
	}
}
