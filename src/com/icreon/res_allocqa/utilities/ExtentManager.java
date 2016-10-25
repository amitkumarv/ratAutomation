package com.icreon.res_allocqa.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentManager {
	
	
	@SuppressWarnings("deprecation")
	public static ExtentReports Instance() {
		 ExtentReports extent;
		 String Path = "./ExtentReport.html";
		 extent = new ExtentReports(Path, false);
		 
		 // Configuring report 
		 File configFile = new File(PropertiesUtils.getPropVal("extent.config"));
		 extent.loadConfig(configFile);
		 return extent;
	}
	public static String CaptureScreen(WebDriver driver, String ImagesPath) {
	    TakesScreenshot oScn = (TakesScreenshot) driver;
	    File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
	    File oDest = new File(ImagesPath+".jpg");
	    try {
	    	FileUtils.copyFile(oScnShot, oDest);
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
    	}
	    return ImagesPath+".jpg";
    }
	
	
}