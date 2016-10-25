package com.icreon.res_allocqa.tests.admin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.icreon.res_allocqa.helpers.ExcelHelper;
import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.pages.HeaderMenu;
import com.icreon.res_allocqa.pages.SkillPage;
import com.icreon.res_allocqa.utilities.JavaUtils;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.LogStatus;
import base.testBase;

public class SkillTest extends testBase {

	static String methodName;
	SkillPage sp;
	HeaderMenu hm;
	WebDriver webDriver;
	JavaUtils jUtils;
	HashMap<String, String> testData;
	private static String skillName;
	private static String coeNameXls;
	public static String skillNameUpdate;
	public static String skillNameSearch;
	public static String coeNameSearch;
	public static String coeTestDataFile = "CoeAndSkillTestData.xls";
	
	public SkillTest() throws IOException {
		super();
		ExcelHelper eh = new ExcelHelper();
		jUtils = new JavaUtils();
		testData = eh.getTestData(PropertiesUtils.getPropVal("test.data")+coeTestDataFile);
		skillName = testData.get("SkillName");
		skillNameSearch = testData.get("SkillNameSearch");
		coeNameSearch = testData.get("CoeNameSearch");
		coeNameXls = testData.get("CoeName")+testData.get("CoeNameUpdate");
		
		// Logic for incrementing COE name by 1 to make it dynamic because input field is accepting only unique COE
		String[] skillArr = skillName.split("_");
		int incrementVal = Integer.parseInt(skillArr[1])+1;
		skillName = skillArr[0]+"_"+Integer.toString(incrementVal);
		
		// Updating data value in COE and Skil test data file
		eh.updateTestDataValue(PropertiesUtils.getPropVal("test.data")+coeTestDataFile, "SkillName", skillName);
		
		// Getting value for updating Skill name
		skillNameUpdate = testData.get("SkillNameUpdate");
		skillNameUpdate = skillName+skillNameUpdate;
		
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
		
	}
	
	@BeforeMethod
    public void handleTestMethodName(Method method)
    {
		methodName = method.getName();
		this.webDriver = WebDriverFactory.getWebDriver();
    }
	
	
	@Test(priority=1)
	public void navigateToManageSkillCOE() throws IOException, InterruptedException {
		setUpExtent("Navigate to Manage Skills", "Testing that user is navigating to 'Manage Skills' page : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		
		// Checking success by comparing page heading
		String pageHeading = hm.getPageHeading(extentTest);
		if(pageHeading.equals("Manage Skills")) {
			extentTest.log(LogStatus.PASS, "User successfully navigated to 'Manage Skills page'." );
		} else {
			extentTest.log(LogStatus.FAIL, "User not navigated to 'Manage Skills page'.");
		}

		tearDownExtent();
	}
	
	@Test(priority=2)
	public void testAddSkill() throws InterruptedException, IOException {
		setUpExtent("Test Add Skill", "Testing add COE functionality : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickAddNewSkills(extentTest);
		sp.enterNewSkill(extentTest, skillName);
		if(coeName != null) {
			sp.selectSkillCOE(extentTest, coeName);
		} else {
			sp.selectSkillCOE(extentTest, coeNameXls);
		}
		sp.clickSaveSkills(extentTest);

		//Check Success		
		if(sp.checkAddSkillsSuccess(extentTest, skillName).equals(skillName)) {
			extentTest.log(LogStatus.PASS, "Skill '"+skillName+"' added Successful." );
		} else {
			extentTest.log(LogStatus.ERROR, "Adding Skill failed.");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=3)
	public void testEditSkill() throws InterruptedException, IOException {
		setUpExtent("Test Edit Skill", "Testing edit skill functionality : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
				
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickEditSkill(extentTest, skillName);
		sp.enterSkillUpdate(extentTest, skillNameUpdate);
		sp.clickSaveUpdateSkill(extentTest);
		
		//Check Success
		if(sp.checkEditSkillSuccess(extentTest, skillNameUpdate).equals(skillNameUpdate)) {
			extentTest.log(LogStatus.PASS, "Skill '"+skillNameUpdate+"' updated Successful." );
			skillName = skillNameUpdate;
		} else {
			extentTest.log(LogStatus.FAIL, "Edit skill failed.");
		}
		
		tearDownExtent();
	}
	
	
	
	@Test(priority=4)
	public void testDeactivateSkill() throws InterruptedException, IOException {
		setUpExtent("Test Deactivate Skill", "Testing skill 'deactivation' functionality : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickDeactivateSkill(extentTest, skillName);
		sp.clickConfirmActiveDeactive(extentTest);
		sp.selectInactive(extentTest);
		sp.clickSearch(extentTest);
		
		//Check Success
		if(sp.checkInactiveActiveSkillSuccess(extentTest, skillName)) {
			extentTest.log(LogStatus.PASS, "Skill '"+skillName+"' deactivation Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Deactivating skill failed.");
		}
		
		tearDownExtent();
	}
	
	@Test(priority=5)
	public void testActivateSkill() throws InterruptedException, IOException {
		setUpExtent("Test Activate Skill", "Testing skill 'Activation' functionality : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.selectInactive(extentTest);
		sp.clickSearch(extentTest);
		sp.clickActivateSkill(extentTest, skillName);
		sp.clickConfirmActiveDeactive(extentTest);
		sp.selectActive(extentTest);
		sp.clickSearch(extentTest);
				
		//Check Success
		if(sp.checkInactiveActiveSkillSuccess(extentTest, skillName)) {
			extentTest.log(LogStatus.PASS, "Skill '"+skillName+"' Activation Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Activating skill failed.");
		}
		
		tearDownExtent();
	}
	
	@Test(priority=6, groups= {"All",  "Search"})
	public void testSearchSkillByName() throws InterruptedException, IOException {
		setUpExtent("Test Skill Search by Name", "Testing search Skill by name : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		

		boolean isStrinfFound = true;
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.enterSkillInSearch(extentTest, skillNameSearch);
		sp.clickSearch(extentTest);
		ArrayList<WebElement> skillRecords = sp.getRecords(extentTest);
		for (int i = 0; i < skillRecords.size(); i++) {
			if(!skillRecords.get(i).getText().contains(skillNameSearch)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Searched skills found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Searched skills not found.", image);
			extentTest.log(LogStatus.FAIL, "Searched skills not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=7, groups= {"All",  "Search"})
	public void testSearchSkillByCOE() throws InterruptedException, IOException {
		setUpExtent("Test Skill Search by COE", "Testing search Skill by COE : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		

		boolean isStrinfFound = true;
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.selectSkillCOESearch(extentTest, coeNameSearch);
		sp.clickSearch(extentTest);
		ArrayList<WebElement> skillRecords = sp.getCOERecords(extentTest);
		for (int i = 0; i < skillRecords.size(); i++) {
			if(!skillRecords.get(i).getText().contains(coeNameSearch)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Searched skills found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Searched skills not found.", image);
			extentTest.log(LogStatus.FAIL, "Searched skills not found.");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=8, groups= {"All",  "Search"})
	public void testSearchByStatusActive() throws InterruptedException, IOException {
		setUpExtent("Test Skill Search by Status Active", "Testing search skill by status 'Active' : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.selectActive(extentTest);
		sp.clickSearch(extentTest);
		
		ArrayList<WebElement> COERecords = sp.getRecords(extentTest);
		ArrayList<WebElement> editButtons = sp.getEditButtons(extentTest);
				
		//Check Success
		if(COERecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Active' skills found" );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Active' skills not found", image);
			extentTest.log(LogStatus.FAIL, "'Active' skills not found");
		}	
		tearDownExtent();
	}
	
	@Test(priority=9, groups= {"All",  "Search"})
	public void testSearchByStatusAInative() throws InterruptedException, IOException {
		setUpExtent("Test Skill Search by Status Inactive", "Testing search by status 'Inactive' : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.selectInactive(extentTest);
		sp.clickSearch(extentTest);
		
		ArrayList<WebElement> COERecords = sp.getRecords(extentTest);
		ArrayList<WebElement> activateButtons = sp.getEditButtons(extentTest); // Edit and Activate buttons have same xpath
		
		//Check Success
		if(COERecords.size() == activateButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Inactive' skills found" );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Inactive' skills not found", image);
			extentTest.log(LogStatus.FAIL, "'Inactive' skills not found");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=10, groups= {"All",  "Sorting"})
	public void testSortingSkillByNameAscending() throws InterruptedException, IOException {
		setUpExtent("Test Skill Sorting by Name(Ascending)", "Testing skill sorting by name in ascending order : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickSkillHeader(extentTest);
		ArrayList<WebElement> skillRecords = sp.getRecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(skillRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in ascending order." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Records not sorted in ascending order.", image);
			extentTest.log(LogStatus.FAIL, "Records not sorted in ascending order.");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=11, groups= {"All",  "Sorting"})
	public void testSortingSkillByNameDescending() throws InterruptedException, IOException {
		setUpExtent("Test Skill Sorting by Name(Descending)", "Testing skill sorting by name in descending order : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickSkillHeader(extentTest);
		sp.clickSkillHeader(extentTest);
		ArrayList<WebElement> skillRecords = sp.getRecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(skillRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in descending order" );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Records not sorted in descending order.", image);
			extentTest.log(LogStatus.FAIL, "Records not sorted in descending order");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=12, groups= {"All",  "Sorting"})
	public void testSortingSkillByCOEAscending() throws InterruptedException, IOException {
		setUpExtent("Test Skill Sorting by COE(Ascending)", "Testing skill sorting by COE in ascending order : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickCOEHeader(extentTest);
		ArrayList<WebElement> COERecords = sp.getCOERecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(COERecords);

		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in ascending order." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Records not sorted in ascending order.", image);
			extentTest.log(LogStatus.FAIL, "Records not sorted in ascending order.");
		}
		tearDownExtent();
	}
	
	@Test(priority=13, groups= {"All",  "Sorting"})
	public void testSortingSkillByCOEDescending() throws InterruptedException, IOException {
		setUpExtent("Test Skill Sorting by COE(Descending)", "Testing skill sorting by COE in descending order : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.clickCOEHeader(extentTest);
		sp.clickCOEHeader(extentTest);
		ArrayList<WebElement> COERecords = sp.getCOERecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(COERecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in descending order" );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Records not sorted in descending order.", image);
			extentTest.log(LogStatus.FAIL, "Records not sorted in descending order");
		}	
		tearDownExtent();
	}
	
	@Test(priority=14, groups= {"All", "Pagination"})
	public void testCOEPagination() throws InterruptedException, IOException {
		setUpExtent("Test Pagination on Manage Skills Page", "Testing pagination on manage Skills page : : Admin > Manage Skills");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		sp = new SkillPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkills(extentTest);
		sp.selectPagiation(extentTest, Integer.toString(10));
		ArrayList<WebElement> records = sp.getRecords(extentTest);
		
		//Check Success
		if(records.size() == 10) {
			extentTest.log(LogStatus.PASS, "Pagination working on manage skills page");
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Pagination not working on manage skills page", image);
			extentTest.log(LogStatus.FAIL, "Pagination not working on manage skills page" );
		}	
		tearDownExtent();
	}
	
}
