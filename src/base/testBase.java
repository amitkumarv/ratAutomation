package base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.icreon.res_allocqa.helpers.ExcelHelper;
import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.ExtentManager;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class testBase {
	protected WebDriver driver;
	protected ExcelHelper ExHelper;
	protected ExtentReports extent;
	protected ExtentTest extentTest;
	public File scrFile;
	public static String coeName;
	
	
	public testBase() throws IOException {
		ExHelper = new ExcelHelper();
		ExHelper.getTestCasestoRun(PropertiesUtils.getPropVal("test.run.settings"));
	}
	
	@BeforeSuite
	public void runBeforeSuit() throws IOException, InterruptedException {
		File file = new File(PropertiesUtils.getPropVal("extent.report.open"));
		file.delete();
	}
	
	public void init() {
		this.driver = WebDriverFactory.instantiateDriver();
		
	}
	
	public void setUpExtent(String testCaseName, String message){
		extent = ExtentManager.Instance();
		extentTest = extent.startTest(testCaseName, message);
	 }
	
	public String timeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000L);
	}
	
	public String takeScreenShot(WebDriver driver, String fileName) throws IOException {
		driver = WebDriverFactory.getWebDriver();
		scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshot/"+fileName));
        String filePath = PropertiesUtils.getPropVal("test.screenshots")+fileName;
        System.out.println(filePath);
        return filePath;
	}
	
	public void tearDownExtent() {
		extent.endTest(extentTest);
		extent.flush();
		extent.close();
	 }

	@AfterSuite
	public void quitBrowser() {
		 WebDriverFactory.getWebDriver().get("file:///"+PropertiesUtils.getPropVal("extent.report.open"));
	}
	
	
	
}
