package com.icreon.res_allocqa.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import base.testBase;
import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.pages.HeaderMenu;
import com.icreon.res_allocqa.pages.ResourceAllocationPage;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.LogStatus;

public class ResourceAllocationTest extends testBase {
	static String methodName;
	public static String coeName;
	public static String coeNameUpdate;
	public static String coeTestDataFile = "CoeTestData.xls";
	public static String curDate;
	ResourceAllocationPage rap;
	WebDriver webDriver;
	HashMap<String, String> testData;
	HeaderMenu hm;
	
	public ResourceAllocationTest() throws IOException {
		super();
	}

	@BeforeClass
	public void beforeClass() {
		// Check run mode
		if(ExHelper.getRunMode(getClass().getSimpleName())) {
			throw new SkipException("Skipping Test Resource Allocation");
		}
	}
	
	@BeforeTest
	public void testInit() {
		this.webDriver = WebDriverFactory.getWebDriver();
	}
	/*
	@BeforeMethod
    public void handleTestMethodName(Method method) throws IOException
    {
		methodName = method.getName();
		rap = new ResourceAllocationPage(webDriver);
		rap.selectResourceManager();
		hm = new HeaderMenu(webDriver);
    }
	
	@Test(priority=1)
	public void testResourceAllocationToEndedProj() throws InterruptedException, IOException {
		setUpExtent("Test resource allocation to an ended project", "Resource allocation page");
		
		// Code for skipping exception
		if(ExHelper.getRunMode(methodName)) {
			test.log(LogStatus.SKIP, "testResourceAllocation skipped." );
			tearDownExtent();
			throw new SkipException("Skipping test testResourceAllocation");
		}

		test.log(LogStatus.INFO, "Testing 'Resource Allocation' functionality ");		
		
		// Hover Mouse on Setup Menu
		try {
			hm.moveMouseToSetup();
			test.log(LogStatus.PASS, "Pointer moved to setup menu." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Move to setup failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Resource Allocation Menu
		try {
			hm.clickResourceAllocMenu();
			test.log(LogStatus.PASS, "'Resource Allocation' menu is clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Resource Allocation' failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click on 'Allocate Resource' Link
		try {
			rap.clickAllocateResource();
			test.log(LogStatus.PASS, "'Allocate Resource' link clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Allocate Resource' link failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Selecting project which is already ended
		try {			
			rap.selectPastProject();
			test.log(LogStatus.PASS, "Previously ended project selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting previously ended project failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Select Resource
		try {
			rap.selectResource();
			test.log(LogStatus.PASS, "A resource is selected for allocation." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting resource for allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		//Allocation Start Date
		try {
			Thread.sleep(5000);
			rap.selectAllocationStartDate();
			test.log(LogStatus.PASS, "Allocation start date selected" );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selectig allocation start date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		//Allocation End Date
		try {
			rap.selectAllocationEndDate();
			test.log(LogStatus.PASS, "Allocation end date selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting allocation end date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Percentage allocation
		try {
			rap.enterPerAllocation();
			test.log(LogStatus.PASS, "Allocation percentage entered." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Entering percantage allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Add Button
		try {
			rap.clickAdd();
			Thread.sleep(5000);
			test.log(LogStatus.PASS, "Add button clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Clicking add button failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		
		//Check Success
		if(rap.checkResAllocationSuccess()) {
			test.log(LogStatus.INFO, rap.getMessage());
			test.log(LogStatus.PASS, "Result : PASS" );
		} else {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Resource allocated to an ended project.", image);
			test.log(LogStatus.ERROR, "Resource allocated to an ended project.");
		}	
		
		tearDownExtent();
	}
	
	
	@Test(priority=2)
	public void testConsultantAllocationWhoLeft() throws InterruptedException, IOException {
		setUpExtent("Test consultant(already left company) allocation to a project", "Resource allocation page");
		
		// Code for skipping exception
		if(ExHelper.getRunMode(methodName)) {
			test.log(LogStatus.SKIP, "testConsultantAllocationWhoLeft skipped." );
			tearDownExtent();
			throw new SkipException("Skipping test testConsultantAllocationWhoLeft");
		}

		test.log(LogStatus.INFO, "Testing 'Resource Allocation' functionality for a consultant who left company earlier.");		
		
		// Hover Mouse on Setup Menu
		try {
			hm.moveMouseToSetup();
			test.log(LogStatus.PASS, "Pointer moved to setup menu." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Move to setup failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Resource Allocation Menu
		try {
			hm.clickResourceAllocMenu();
			test.log(LogStatus.PASS, "'Resource Allocation' menu is clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Resource Allocation' failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click on 'Allocate Resource' Link
		try {
			rap.clickAllocateResource();
			test.log(LogStatus.PASS, "'Allocate Resource' link clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Allocate Resource' Link failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Selecting project
		try {			
			rap.selectProjectCommon();
			test.log(LogStatus.PASS, "Project selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting project failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Select Consultant
		try {
			rap.selectConsultant();
			test.log(LogStatus.PASS, "A consultant is selected for allocation." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting consultant for allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}

		//Consultant allocation Start Date
		try {
			Thread.sleep(5000);
			rap.selectAllocationStartDateConsultant();
			test.log(LogStatus.PASS, "Allocation start date selected" );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selectig allocation start date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		//Consultant allocation End Date
		try {
			rap.selectAllocationEndDateConsultant();
			test.log(LogStatus.PASS, "Allocation end date selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting allocation end date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Percentage allocation
		try {
			rap.enterPerAllocation();
			test.log(LogStatus.PASS, "Allocation percentage entered." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Entering percantage allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Add Button
		try {
			rap.clickAdd();
			Thread.sleep(5000);
			test.log(LogStatus.PASS, "Add button clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Clicking add button failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		
		//Check Success
		if(rap.checkResAllocationSuccess()) {
			test.log(LogStatus.INFO, rap.getMessage());
			test.log(LogStatus.PASS, "Result : PASS" );
		} else {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Consultant who has left company in earlier month  is assigned to a project.", image);
			test.log(LogStatus.ERROR, "Consultant who has left company in earlier month  is assigned to a project.");
		}	
		
		tearDownExtent();
	}
	
	
	/*
	 * Test allocating a resource(with 100% allocation) to a project
	 */
	/*
	@Test(priority=3)
	public void testResAllocWithAllocPer() throws InterruptedException, IOException {
		setUpExtent("Test resource(already has some allocation) allocation to a project", "Resource allocation page");
		
		// Code for skipping exception
		if(ExHelper.getRunMode(methodName)) {
			test.log(LogStatus.SKIP, "testResAllocWithAllocPer Skipped." );
			tearDownExtent();
			throw new SkipException("Skipping test testResAllocWithAllocPer");
		}

		test.log(LogStatus.INFO, "Testing 'Resource Allocation' functionality for a resource who is already has some allocations.");		
		
		// Hover Mouse on Setup Menu
		try {
			hm.moveMouseToSetup();
			test.log(LogStatus.PASS, "Pointer moved to setup menu." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Move to setup failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Resource Allocation Menu
		try {
			hm.clickResourceAllocMenu();
			test.log(LogStatus.PASS, "'Resource Allocation' menu is clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Resource Allocation' failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click on 'Allocate Resource' Link
		try {
			rap.clickAllocateResource();
			test.log(LogStatus.PASS, "'Allocate Resource' link clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Click on 'Allocate Resource' Link failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Selecting project
		try {			
			rap.selectProjectCommon();
			test.log(LogStatus.PASS, "Project selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting project failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Select resource who already has some allocations
		try {
			rap.selectResourceAlreadyAlloc();
			test.log(LogStatus.PASS, "A resource is selected for allocation." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting consultant for allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}

		//resource allocation Start Date
		try {
			Thread.sleep(5000);
			rap.selectAllocationStartDateAlloc();
			test.log(LogStatus.PASS, "Allocation start date selected" );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selectig allocation start date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		//Consultant allocation End Date
		try {
			rap.selectAllocationEndDate();
			test.log(LogStatus.PASS, "Allocation end date selected." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Selecting allocation end date failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Percentage allocation
		try {
			rap.enterPerAllocationHundred();
			test.log(LogStatus.PASS, "Allocation percentage entered." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Entering percantage allocation failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		// Click Add Button
		try {
			rap.clickAdd();
			Thread.sleep(5000);
			test.log(LogStatus.PASS, "Add button clicked." );
		} catch (Exception e) {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Clicking add button failed.", image);
			test.log(LogStatus.ERROR, PropertiesUtils.getSeleniumError());
		}
		
		
		//Check Success
		if(rap.checkResAllocationSuccess()) {
			test.log(LogStatus.INFO, rap.getMessage());
			test.log(LogStatus.PASS, "Result : PASS" );
		} else {
			String image = test.addScreenCapture(takeScreenShot(timeStamp()+".png"));
			test.log(LogStatus.FAIL, "Resource allocation became more than 100%.", image);
			test.log(LogStatus.ERROR, "Resource allocation became more than 100%.");
		}	
		
		tearDownExtent();
	}*/
	
}
