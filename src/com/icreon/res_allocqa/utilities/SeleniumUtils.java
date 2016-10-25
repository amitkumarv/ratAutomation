package com.icreon.res_allocqa.utilities;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SeleniumUtils {
	public File scrFile;
	WebDriver driver;
	
	
	public String timeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000L);
	}
	
	
	public String takeScreenShot(WebDriver driver, String fileName) throws IOException {
		driver = WebDriverFactory.getWebDriver();
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshot/"+fileName));
        String filePath = PropertiesUtils.getPropVal("test.screenshots")+fileName;
        return filePath;
	}
	
	/*
	 * Method for entering text in an input field
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param fieldName name of the field in which data to be entered
	 * @param fieldData data to be entered in fieldName
	 * return void
	 */
	public void enterText(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String fieldName, String fieldData) throws IOException, InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).sendKeys(fieldData);
			extentTest.log(LogStatus.PASS, fieldName+" entered." );
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, fieldData+" not entered in "+fieldName+".", image);
			extentTest.log(LogStatus.ERROR, fieldData+" not entered in "+fieldName+". ->"+e.getMessage());
		}
	}
	
	/*
	 * Method for click on an element
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element to be clicked
	 * return void
	 */
	public void doClick(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException, InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).click();
			Thread.sleep(1000);
			extentTest.log(LogStatus.PASS, elementName+" clicked." );
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not clicked.", image);
			extentTest.log(LogStatus.ERROR, elementName+" not clicked. ->"+e.getMessage());
		}
	}
	
	/*
	 * Method for click on an element
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element on which controlled to be moved
	 * return void
	 */
	public void doMoveControl(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException, InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(locator)).perform();
			extentTest.log(LogStatus.PASS, "Control moved to "+elementName+"." );
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, "Control not moved to "+elementName+"." , image);
			extentTest.log(LogStatus.ERROR, "Control not moved to "+elementName+". ->"+e.getMessage());
		}
	}
	
	/*
	 * Method for click on an element
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element from where text to be fetched
	 * return String
	 */
	public String getText(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException, InterruptedException {
		String textData = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			textData = driver.findElement(locator).getText();
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not found." , image);
			extentTest.log(LogStatus.ERROR, elementName+" not found. ->"+e.getMessage());
			textData = e.getMessage();
		}
		return textData;
	}
	
	/*
	 * Method for click on an element
	 * @param extentTest Object of ExtentManager
	 * @param anchorList List containing anchor tags
	 * return void
	 */
	public void getURLStatusCode(ExtentTest extentTest, List<WebElement> anchorList) throws IOException, InterruptedException {
		
		Boolean successFlag = true;
		for (int i = 0; i < anchorList.size(); i++) {
			String urlStr = anchorList.get(i).getAttribute("href");
			if(urlStr.contains("res-allocqa")) {
				URL url = new URL(anchorList.get(i).getAttribute("href"));
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				int statusCode = http.getResponseCode();
				//System.out.println(url+"->"+statusCode);
				if(statusCode != 200) {
					successFlag = false;
					extentTest.log(LogStatus.ERROR, urlStr+" has status code: "+statusCode);
				}
			}
		}
		if(successFlag.equals(true)) {
			extentTest.log(LogStatus.PASS, "All links on page are working." );
		}
		
	
	}
	
	/*
	 * Method to clear an input field
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element from where text to be cleared
	 * return String
	 */
	public String doClearInput(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException, InterruptedException {
		String textData = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).clear();
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not found." , image);
			extentTest.log(LogStatus.ERROR, elementName+" not found. ->"+e.getMessage());
			textData = e.getMessage();
		}
		return textData;
	}
	
	/*
	 * Method to handle a drop down
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element from where text to be cleared
	 * return void
	 */
	public Select handleSelect(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException {		
		Select drop = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			drop = new Select(driver.findElement(locator));
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not found." , image);
			extentTest.log(LogStatus.ERROR, elementName+" not found. ->"+e.getMessage());
		}
		
		return drop;
	}
	
	/*
	 * Method to handle a drop down
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element from where elements to be fetched
	 * return void
	 */
	public ArrayList<WebElement> getRecoprdsFromTable(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException {		
		ArrayList<WebElement> elements = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			elements = (ArrayList<WebElement>) driver.findElements(locator);
		} catch (Exception e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not found." , image);
			extentTest.log(LogStatus.ERROR, elementName+" not found. ->"+e.getMessage());
		}
		
		return elements;
	}
	
	/*
	 * Method to check that a given element exists or not
	 * @param extentTest Object of ExtentManager
	 * @param driver WebDriver
	 * @param locator Element locator
	 * @param timeoutInSeconds timeout
	 * @param elementName element whose existence is to be checked
	 * return boolean
	 */
	public boolean checkElementExist(ExtentTest extentTest, WebDriver driver, By locator, int timeoutInSeconds, String elementName) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if(driver.findElements(locator).size() == 1) {
				return true;
			}
		} catch (TimeoutException e) {
			String image = extentTest.addScreenCapture(takeScreenShot(driver, timeStamp()+".png"));
			extentTest.log(LogStatus.FAIL, elementName+" not found." , image);
			extentTest.log(LogStatus.ERROR, elementName+" not found. ->"+e.getMessage());
			return false;
		} 
	    return true;
	}
	
}
