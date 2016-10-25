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
import com.icreon.res_allocqa.pages.RoleMappingPage;
import com.icreon.res_allocqa.utilities.JavaUtils;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.LogStatus;
import base.testBase;

public class RoleMappingTest extends testBase {
	static String methodName;
	RoleMappingPage rm;
	HeaderMenu hm;
	WebDriver webDriver;
	JavaUtils jUtils;
	HashMap<String, String> testData;
	public static String empName;
	public static String empId;
	public static String roleMappingTestDataFile = "RoleMappingTestData.xls";
	
	public RoleMappingTest() throws IOException {
		super();
		ExcelHelper eh = new ExcelHelper();
		jUtils = new JavaUtils();
		testData = eh.getTestData(PropertiesUtils.getPropVal("test.data")+roleMappingTestDataFile);
		empName = testData.get("EmpName");
		empId = testData.get("EmpId");
		
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
	public void navigateToRoleMapping() throws IOException, InterruptedException {
		setUpExtent("Navigate to Role Mapping", "Testing that user is navigating to 'Role Mapping' page : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		
		// Checking success by comparing page heading
		String pageHeading = hm.getPageHeading(extentTest);
		if(pageHeading.equals("Role Mapping")) {
			extentTest.log(LogStatus.PASS, "User successfully navigated to 'Role Mapping page'." );
		} else {
			extentTest.log(LogStatus.FAIL, "User not navigated to 'Role Mapping' page.");
		}
		tearDownExtent();
	}
	
	@Test(priority=2, groups= {"All",  "Search"})
	public void testSearchRoleByName() throws InterruptedException, IOException {
		setUpExtent("Test Search Role by Employee Name", "Testing search role by employee name : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		boolean isStrinfFound = true;
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.enterEmpNameInSearch(extentTest, empName);
		rm.clickSearch(extentTest);
		ArrayList<WebElement> skillRecords = rm.getNamesFromTable(extentTest);
		for (int i = 0; i < skillRecords.size(); i++) {
			if(!skillRecords.get(i).getText().contains(empName)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Role searched by employee name found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role searched by employee name not found.", image);
			extentTest.log(LogStatus.FAIL, "Role searched by employee name not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=3, groups= {"All",  "Search"})
	public void testSearchRoleByEmpID() throws InterruptedException, IOException {
		setUpExtent("Test Search Role by Employee ID", "Testing search role by employee ID : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		boolean isStrinfFound = true;
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.enterEmpIdInSearch(extentTest, empId);
		rm.clickSearch(extentTest);
		ArrayList<WebElement> skillRecords = rm.getEmpIdsFromTable(extentTest);
		for (int i = 0; i < skillRecords.size(); i++) {
			if(!skillRecords.get(i).getText().contains(empId)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Role searched by employee id found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role searched by employee id not found.", image);
			extentTest.log(LogStatus.FAIL, "Role searched by employee id not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=4, groups= {"All",  "Search"})
	public void testSearchRoleByStatusActive() throws InterruptedException, IOException {
		setUpExtent("Test Search Role by Status Active", "Testing search role by status 'Active' : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.selectActive(extentTest);
		rm.clickSearch(extentTest);
		
		ArrayList<WebElement> roleRecords = rm.getNamesFromTable(extentTest);
		ArrayList<WebElement> editButtons = rm.getEditButtons(extentTest); // Edit and Activate buttons have same xpath
		
		//Check Success
		if(roleRecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Active' roles found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Active' roles not found.", image);
			extentTest.log(LogStatus.FAIL, "'Active' roles not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=5, groups= {"All",  "Search"})
	public void testSearchRoleByStatusInactive() throws InterruptedException, IOException {
		setUpExtent("Test Search Role by Status Inactive", "Testing search role by status 'Inactive' : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.selectInactive(extentTest);
		rm.clickSearch(extentTest);
		
		ArrayList<WebElement> roleRecords = rm.getNamesFromTable(extentTest);
		ArrayList<WebElement> editButtons = rm.getEditButtons(extentTest); // Edit and Activate buttons have same xpath
		
		//Check Success
		if(roleRecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Inactive' roles found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Inactive' roles not found.", image);
			extentTest.log(LogStatus.FAIL, "'Inactive' roles not found.");
		}		
		tearDownExtent();
	}
	
	@Test(priority=6, groups= {"All",  "Sorting"})
	public void testSortingRoleByEmpIdAscending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Employee ID(Ascending)", "Testing role sorting by employee id in ascending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickEmpIdHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getEmpIdsFromTable(extentTest);
		boolean isSorted = jUtils.checkIntSortingAscending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in ascending order of Employee ID." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in ascending order of Employee ID.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in ascending order of Employee ID.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=7, groups= {"All",  "Sorting"})
	public void testSortingRoleByEmpIdDescending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Employee ID(Descending)", "Testing role sorting by employee id in descending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickEmpIdHeader(extentTest);
		rm.clickEmpIdHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getEmpIdsFromTable(extentTest);
		boolean isSorted = jUtils.checkIntSortingDescending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in descending order of Employee ID." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in descending order of Employee ID.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in descending order of Employee ID.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=8, groups= {"All",  "Sorting"})
	public void testSortingRoleByNameAscending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Employee Name(Ascending)", "Testing role sorting by employee name in ascending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickNameHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getNamesFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in ascending order of Employee Name." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in ascending order of Employee Name.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in ascending order of Employee Name.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=9, groups= {"All",  "Sorting"})
	public void testSortingRoleByNameDescending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Employee Name(Descending)", "Testing role sorting by employee name in descending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickNameHeader(extentTest);
		rm.clickNameHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getNamesFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in descending order of Employee Name." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in descending order of Employee Name.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in descending order of Employee Name.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=10, groups= {"All",  "Sorting"})
	public void testSortingRoleByDesignationAscending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Designation(Ascending)", "Testing role sorting by designation in ascending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickDesignationHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getDesignationsFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in ascending order of Designation." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in ascending order of Designation.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in ascending order of Designation.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=11, groups= {"All",  "Sorting"})
	public void testSortingRoleByDesignationDescending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Designation(Descending)", "Testing role sorting by designation in descending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickDesignationHeader(extentTest);
		rm.clickDesignationHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getDesignationsFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in descending order of Designation." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in descending order of Designation.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in descending order of Designation.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=12, groups= {"All",  "Sorting"})
	public void testSortingRoleByEmailAscending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Email(Ascending)", "Testing role sorting by email in ascending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());			
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickEmailHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getEmailsFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in ascending order of Email." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in ascending order of Email.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in ascending order of Email.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=13, groups= {"All",  "Sorting"})
	public void testSortingRoleByEmailDescending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Email(Descending)", "Testing role sorting by email in descending order : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.clickEmailHeader(extentTest);
		rm.clickEmailHeader(extentTest);
		ArrayList<WebElement> empIdRecords = rm.getEmailsFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(empIdRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Role records sorted in descending order of Email." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Role records not sorted in descending order of Email.", image);
			extentTest.log(LogStatus.FAIL, "Role records not sorted in descending order of Email.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=14, groups= {"All", "Pagination"})
	public void testRolePagination() throws InterruptedException, IOException {
		setUpExtent("Test Pagination on 'Role Mapping' Page", "Testing pagination on 'Role Mapping' page : : Admin > Role Mapping");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		rm = new RoleMappingPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickRoleMapping(extentTest);
		rm.selectPagiation(extentTest, Integer.toString(10));
		ArrayList<WebElement> records = rm.getEmpIdsFromTable(extentTest);
		
		//Check Success
		if(records.size() == 10) {
			extentTest.log(LogStatus.PASS, "Pagination working on 'Role Mapping' page");
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Pagination not working on 'Role Mapping' page", image);
			extentTest.log(LogStatus.FAIL, "Pagination not working on 'Role Mapping' page" );
		}	
		tearDownExtent();
	}
}
