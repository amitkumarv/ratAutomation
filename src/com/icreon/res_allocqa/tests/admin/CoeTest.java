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
import base.testBase;
import com.icreon.res_allocqa.helpers.ExcelHelper;
import com.icreon.res_allocqa.pages.CoePage;
import com.icreon.res_allocqa.pages.HeaderMenu;
import com.icreon.res_allocqa.utilities.JavaUtils;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.LogStatus;

public class CoeTest extends testBase {
	static String methodName;
	CoePage cop;
	HeaderMenu hm;
	JavaUtils jUtils;
	WebDriver webDriver;
	HashMap<String, String> testData;
	
	public static String coeNameUpdate;
	public static String coeNameSearch;
	public static String coeTestDataFile = "CoeAndSkillTestData.xls";
	
	public CoeTest() throws IOException {
		super();
		jUtils = new JavaUtils();
		
		ExcelHelper eh = new ExcelHelper();
		testData = eh.getTestData(PropertiesUtils.getPropVal("test.data")+coeTestDataFile);
		coeName = testData.get("CoeName");
		coeNameSearch = testData.get("CoeNameSearch");
				
		// Logic for incrementing COE name by 1 to make it dynamic because input field is accepting only unique COE
		String[] coeArr = coeName.split("_");
		int incrementVal = Integer.parseInt(coeArr[1])+1;
		coeName = coeArr[0]+"_"+Integer.toString(incrementVal);
		
		// Updating data value in COE test data file
		eh.updateTestDataValue(PropertiesUtils.getPropVal("test.data")+coeTestDataFile, "CoeName", coeName);
		
		// Getting value for updating COE name
		coeNameUpdate = testData.get("CoeNameUpdate");
		coeNameUpdate = coeName+coeNameUpdate;
		
	}

	@BeforeClass
	public void beforeClass() throws IOException {
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
		
    }
	
	@Test(priority=1)
	public void navigateToManageSkillCOE() throws IOException, InterruptedException {
		cop = new CoePage(webDriver);
		hm = new HeaderMenu(webDriver);
		setUpExtent("Navigate to Manage Skill COE", "Testing that user is navigating to 'Manage Skill COE' page : : Admin >Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		
		// Checking success by comparing page heading
		String pageHeading = hm.getPageHeading(extentTest);
		if(pageHeading.equals("Manage Skill COE")) {
			extentTest.log(LogStatus.PASS, "User successfully navigated to 'Manage Skill COE page'." );
		} else {
			extentTest.log(LogStatus.FAIL, "User not navigated to 'Manage Skill COE page'.");
		}

		tearDownExtent();
	}
	
	
	@Test(priority=2)
	public void checkURLStatusCodes() throws IOException, InterruptedException {
		setUpExtent("Check URL Status Code", "Testing that all links on 'Manage Skill COE' page are working : : Admin > Manage Skill COE.");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		cop.getURLStatusCode(extentTest);
		tearDownExtent();
	}
	
	
	@Test(priority=3)
	public void testAddCOE() throws InterruptedException, IOException {
		setUpExtent("Test Add COE", "Testing add COE functionality : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
				
		cop = new CoePage(webDriver);
		hm = new HeaderMenu(webDriver);
			
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.clickAddSkillCOE(extentTest);
		cop.enterSkillCOE(extentTest, coeName);
		cop.clickSaveSkillCOE(extentTest);

		//Check Success
		if(cop.checkAddCOESuccess(extentTest, coeName).equals(coeName)) {
			extentTest.log(LogStatus.PASS, "COE '"+coeName+"' added Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Adding COE failed.");
		}	
		
		tearDownExtent();
	}
	
	@Test(priority=4)
	public void testEditCOE() throws InterruptedException, IOException {
		setUpExtent("Test Edit COE", "Testing edit COE functionality : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cop = new CoePage(webDriver);		
		cop.clickEditSkillCOE(extentTest, coeName);
		cop.enterSkillCOEUpdate(extentTest, coeNameUpdate);
		cop.clickSaveUpdateSkillCOE(extentTest);

		//Check Success
		if(cop.checkEditCOESuccess(extentTest, coeNameUpdate).equals(coeNameUpdate)) {
			extentTest.log(LogStatus.PASS, "COE '"+coeNameUpdate+"' edit Successful." );
			coeName = coeNameUpdate;
		} else {
			extentTest.log(LogStatus.FAIL, "Edit COE failed.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=5)
	public void testDeactivateCOE() throws InterruptedException, IOException {
		setUpExtent("Test deactivate COE", "Testing 'deactivation' functionality : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cop = new CoePage(webDriver);
		cop.clickDeactivateSkillCOE(extentTest, coeName);
		cop.clickConfirmDeactivateSkillCOE(extentTest);
		cop.selectInactive(extentTest);
		cop.clickSearch(extentTest);
	
		//Check Success
		if(cop.checkInactiveCOESuccess(extentTest, coeName)) {
			extentTest.log(LogStatus.PASS, "COE '"+coeName+"' deactivation Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Deactivating COE failed.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=6)
	public void testActivateCOE() throws InterruptedException, IOException {
		setUpExtent("Test Activate COE", "Testing COE 'Activation' functionality : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cop = new CoePage(webDriver);
		cop.clickActivateSkillCOE(extentTest, coeName);
		cop.clickConfirmDeactivateSkillCOE(extentTest);
		cop.selectActive(extentTest);
		cop.clickSearch(extentTest);

		//Check Success
		if(cop.checkActiveCOESuccess(extentTest, coeName)) {
			extentTest.log(LogStatus.PASS, "COE '"+coeName+"' Activation Successful." );
		} else {
			extentTest.log(LogStatus.FAIL, "Activating COE failed.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=7, groups= {"All",  "Search"})
	public void testSearchCOEByName() throws InterruptedException, IOException {
		setUpExtent("Test COE Search by Name", "Testing COE search by name : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		boolean isStrinfFound = true;
				
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);		
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.enterCOEinSearch(extentTest, coeNameSearch);
		cop.clickSearch(extentTest);
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		for (int i = 0; i < COERecords.size(); i++) {
			if(!COERecords.get(i).getText().contains(coeNameSearch)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Searched COEs found." );
		} else {
			extentTest.log(LogStatus.FAIL, "Searched COEs not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=8, groups= {"All",  "Search"})
	public void testSearchByStatusActive() throws InterruptedException, IOException {
		setUpExtent("Test COE Search Status Active", "Testing COE search by status 'Active' : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.clearSearchCOEName(extentTest);
		cop.selectActive(extentTest);
		cop.clickSearch(extentTest);
		
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		ArrayList<WebElement> editButtons = cop.getEditButtons(extentTest);
				
		//Check Success
		if(COERecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Active' COEs found" );
		} else {
			extentTest.log(LogStatus.FAIL, "'Active' COEs not found");
		}	
		tearDownExtent();
	}
	
	@Test(priority=9, groups= {"All",  "Search"})
	public void testSearchByStatusAInative() throws InterruptedException, IOException {
		setUpExtent("Test COE Search Status Inactive", "Testing COE search by status 'Inactive' : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.clearSearchCOEName(extentTest);
		cop.selectInactive(extentTest);
		cop.clickSearch(extentTest);
		
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		ArrayList<WebElement> activateButtons = cop.getActivateButtons(extentTest);
		
		//Check Success
		if(COERecords.size() == activateButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Inactive' COEs found" );
		} else {
			extentTest.log(LogStatus.FAIL, "'Inactive' COEs not found");
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=10, groups= {"All",  "Sorting"})
	public void testSortingByCOEAscending() throws InterruptedException, IOException {
		setUpExtent("Test Sorting by COE(Ascending)", "Testing sorting by COE name in ascending order : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);		
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.clickCOEColumnHeading(extentTest);
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(COERecords);
	
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in ascending order" );
		} else {
			extentTest.log(LogStatus.FAIL, "Records not sorted in ascending order");
		}	
		tearDownExtent();
	}
	
	@Test(priority=11, groups= {"All",  "Sorting"})
	public void testSortingByCOEDescending() throws InterruptedException, IOException {
		setUpExtent("Test Sorting by COE(Descending)", "Testing sorting by COE name in descending order : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.clickCOEColumnHeading(extentTest);
		cop.clickCOEColumnHeading(extentTest);
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(COERecords);

		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Records sorted in descending order" );
		} else {
			extentTest.log(LogStatus.FAIL, "Records not sorted in descending order");
		}	
		tearDownExtent();
	}
	
	@Test(priority=12, groups= {"All", "Pagination"})
	public void testCOEPagination() throws InterruptedException, IOException {
		setUpExtent("Test Pagination on Manage COE Page", "Testing pagination on manage COE page : : Admin > Manage Skill COE");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		hm = new HeaderMenu(webDriver);
		cop = new CoePage(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickSkillCOE(extentTest);
		cop.selectPagiation(extentTest, Integer.toString(10));
		ArrayList<WebElement> COERecords = cop.getRecords(extentTest);
		
		//Check Success
		if(COERecords.size() == 10) {
			extentTest.log(LogStatus.PASS, "Pagination working on manage COE page");
		} else {
			extentTest.log(LogStatus.FAIL, "Pagination not working on manage COE page" );
		}	
		tearDownExtent();
	}
}
