package com.icreon.res_allocqa.helpers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.icreon.res_allocqa.utilities.PropertiesUtils;

public class WebDriverFactory {
	/* Browsers constants */
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String OPERA = "opera";
	public static final String INTERNET_EXPLORER = "ie";
	public static WebDriver driver;
	
	
	public static WebDriver instantiateDriver() {
		// Getting browser name from property file
		String browserName = PropertiesUtils.getPropVal("browser.name");
		
		// Instantiating web driver
		if(CHROME.equals(browserName)) {
			System.setProperty("webdriver.chrome.driver", PropertiesUtils.getPropVal("driver.path"));
			driver = new ChromeDriver();
		} else if(FIREFOX.equals("firefox")) {
			driver = new FirefoxDriver();
		} 
		
		// Implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// Maximize window
		driver.manage().window().maximize();
		
		// Getting and launching url
		driver.get(PropertiesUtils.getPropVal("login.url"));
		return driver;
	}
	
	public static WebDriver getWebDriver() {
		//System.out.println(driver);
		return driver;
	}
	
	
	
}
