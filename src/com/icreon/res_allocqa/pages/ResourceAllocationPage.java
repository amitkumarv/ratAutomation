package com.icreon.res_allocqa.pages;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.icreon.res_allocqa.helpers.ExcelHelper;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.icreon.res_allocqa.utilities.SeleniumUtils;

public class ResourceAllocationPage {
	public static Date curDate;
	public static String ResourceAllovationTestDataFile = "ResourceAllocationTestData.xls";
	HashMap<String, String> testData;
	HashMap<Integer, HashMap<String, String>> hmProjects;
	HashMap<Integer, HashMap<String, String>> hmResources;
	HashMap<Integer, String> monthHM = new HashMap<Integer, String>(){{
		put(0, "Jan");
		put(1, "Feb");
		put(2, "Mar");
		put(3, "Apr");
		put(4, "May");
		put(5, "Jun");
		put(6, "Jul");
		put(7, "Aug");
		put(8, "Sep");
		put(9, "Oct");
		put(10, "Nov");
		put(11, "Dec");
	}};
	
	By LOGGED_IN_AS = By.name("rolesDrpDown");
	By ALLOCATE_RESOURCE_LINK = By.xpath("html/body/div[1]/div[2]/div[2]/div[1]/a");
	By PROJECT_LIST = By.id("project_id");
	By RESOURCE_LIST = By.id("resource");
	By OPTION = By.tagName("option");
	By PROJ_START_DATE = By.xpath(".//*[@id='add_resource']/div/div[1]/ul/li[6]/span");
	By PROJ_END_DATE = By.xpath(".//*[@id='add_resource']/div/div[1]/ul/li[7]/span");
	By ALLOC_START_DATE_INP = By.id("allocation_start_date");
	By ALLOC_END_DATE_INP = By.id("allocation_end_date");
	By CALENDER_MONTH = By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]");
	By CALENDER_YEAR = By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]");
	By PER_ALLOCATION = By.id("per_allocation");
	By ADD_BUTTON = By.id("addResource");
	By MESSAGE = By.xpath("html/body/div[1]/div[2]/h4");
	
	public ResourceAllocationPage(WebDriver webDriver) throws IOException {
		ExcelHelper eh = new ExcelHelper();
		testData = eh.getTestData(PropertiesUtils.getPropVal("test.data")+ResourceAllovationTestDataFile);
	}
	/*
	public void selectResourceManager() {
		Select drop = SeleniumUtils.handleSelect(webDriver, LOGGED_IN_AS, 5);
		drop.selectByValue("RM");
	}
	
	public void clickAllocateResource() {
		SeleniumUtils.findElement(webDriver, ALLOCATE_RESOURCE_LINK, 5).click();	
	}
	
	public void getAllProjects() {
		hmProjects = new HashMap<Integer, HashMap<String,String>>();
		Select drop = SeleniumUtils.handleSelect(webDriver, PROJECT_LIST, 5);
		List<WebElement> options = drop.getOptions();
		
		for(int i = 1; i < options.size(); i++) {
			hmProjects.put(i, new HashMap<String, String>());
			hmProjects.get(i).put("value", options.get(i).getAttribute("value"));
			hmProjects.get(i).put("data-contract-type", options.get(i).getAttribute("data-contract-type"));
			hmProjects.get(i).put("data-prj-code", options.get(i).getAttribute("data-prj-code"));
			hmProjects.get(i).put("data-prj-start-date", options.get(i).getAttribute("data-prj-start-date"));
			hmProjects.get(i).put("data-prj-end-date", options.get(i).getAttribute("data-prj-end-date"));
			hmProjects.get(i).put("data-prj-name", options.get(i).getText());
		}
	}
	
	public void selectPastProject() throws ParseException, InterruptedException {
		//Getting list of all projects
		getAllProjects();
		
		// Getting current date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String currentDateUnParsed = dateFormat.format(cal.getTime());
		curDate = dateFormat.parse(currentDateUnParsed);
		
		String value = null;
		for(int i = 1; i <= hmProjects.size(); i++) {
			String prjCode = hmProjects.get(i).get("data-prj-code");
			//Date prjEndDate =  dateFormat.parse(hmProjects.get(i).get("data-prj-end-date"));
			if(prjCode.equals(testData.get("project_code"))) {
				value = hmProjects.get(i).get("value");
				break;
			}
		}
		Select drop = SeleniumUtils.handleSelect(webDriver, PROJECT_LIST, 5);
		drop.selectByValue(value);
		
		
		
	}
	
	public void selectProjectCommon() throws ParseException, InterruptedException {
		//Getting list of all projects
		getAllProjects();
		
		// Getting current date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String currentDateUnParsed = dateFormat.format(cal.getTime());
		curDate = dateFormat.parse(currentDateUnParsed);
		
		String value = null;
		for(int i = 1; i <= hmProjects.size(); i++) {
			String prjCode = hmProjects.get(i).get("data-prj-code");
			//Date prjEndDate =  dateFormat.parse(hmProjects.get(i).get("data-prj-end-date"));
			if(prjCode.equals(testData.get("project_common"))) {
				value = hmProjects.get(i).get("value");
				break;
			}
		}
		Select drop = SeleniumUtils.handleSelect(webDriver, PROJECT_LIST, 5);
		drop.selectByValue(value);
	}
	
	public void getAllResource() {
		hmResources = new HashMap<Integer, HashMap<String,String>>();
		Select drop = SeleniumUtils.handleSelect(webDriver, RESOURCE_LIST, 5);
		List<WebElement> options = drop.getOptions();
		
		for(int i = 1; i < options.size(); i++) {
			hmResources.put(i, new HashMap<String, String>());
			hmResources.get(i).put("value", options.get(i).getAttribute("value"));
			hmResources.get(i).put("data-leaving-date", options.get(i).getAttribute("data-leaving-date"));
			hmResources.get(i).put("data-joining-date", options.get(i).getAttribute("data-joining-date"));
			hmResources.get(i).put("data-emp-id", options.get(i).getAttribute("data-emp-id"));
			hmResources.get(i).put("data-emp-name", options.get(i).getText());
		}		
	}
	
	public void selectResource() {
		// Getting list of resources
		getAllResource();
		
		String value = null;
		String resEmpIdWeb = null;
		String resIDTestDate = testData.get("resource_id");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestDate)) {
				value = hmResources.get(i).get("value");
				break;
			}
		}
		// Select resource from drop down  
		Select drop = SeleniumUtils.handleSelect(webDriver, RESOURCE_LIST, 5);
		drop.selectByValue(value);
	}
	
	public void selectConsultant() {
		// Getting list of resources
		getAllResource();
		
		String value = null;
		String resEmpIdWeb = null;
		String resIDTestDate = testData.get("consultant_id");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestDate)) {
				value = hmResources.get(i).get("value");
				break;
			}
		}
		// Select resource from drop down  
		Select drop = SeleniumUtils.handleSelect(webDriver, RESOURCE_LIST, 5);
		drop.selectByValue(value);
	}
	
	public void selectResourceAlreadyAlloc() {
		// Getting list of resources
		getAllResource();
		
		String value = null;
		String resEmpIdWeb = null;
		String resIDTestDate = testData.get("resource_id_hund");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestDate)) {
				value = hmResources.get(i).get("value");
				break;
			}
		}
		// Select resource from drop down  
		Select drop = SeleniumUtils.handleSelect(webDriver, RESOURCE_LIST, 5);
		drop.selectByValue(value);
	}
	
	public void selectAllocationStartDate() throws ParseException {
		// Getting project start date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String projStartDateStr = SeleniumUtils.findElement(webDriver, PROJ_START_DATE, 5).getText();
		Date projStartDate = dateFormat.parse(projStartDateStr);
		
		String resJoinDate = null;
		String resEmpIdWeb = null;
		String resIDTestData = testData.get("resource_id");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestData)) {
				resJoinDate = hmResources.get(i).get("data-joining-date");
				break;
			}
		}
		
		// Resource's joining date
		Date resJoiningDate = dateFormat.parse(resJoinDate);
		
		Date allocationStartDate = null;
		if(resJoiningDate.before(projStartDate)){
			allocationStartDate = projStartDate;
    	} else if (projStartDate.before(resJoiningDate)) {
    		allocationStartDate = resJoiningDate;
		} else {
			allocationStartDate = resJoiningDate;
		}
		
		// Click allocation start date input
		SeleniumUtils.findElement(webDriver, ALLOC_START_DATE_INP, 5).click();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(allocationStartDate);
		int allocStartMonth = cal.get(Calendar.MONTH);
		int allocStartDay = cal.get(Calendar.DAY_OF_MONTH);
		String allocEndYear = Integer.toString(cal.get(Calendar.YEAR));
		
		// Click month drop down of calendar
		Select drop = SeleniumUtils.handleSelect(webDriver, CALENDER_MONTH, 5);
		drop.selectByVisibleText(monthHM.get(allocStartMonth));
		
		// Click year drop down of calendar
		Select dropYear = SeleniumUtils.handleSelect(webDriver, CALENDER_YEAR, 5);
		dropYear.selectByVisibleText(allocEndYear);
		
		// Select Date
		SeleniumUtils.findElement(webDriver, By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr/td/a[text()='"+allocStartDay+"']"), 5).click();	
		
	}
	
	public void selectAllocationEndDate() throws ParseException, InterruptedException {
		//Getting project end date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String projEndDateStr = SeleniumUtils.findElement(webDriver, PROJ_END_DATE, 5).getText();
		Date projEndDate = dateFormat.parse(projEndDateStr);
		
		// Click allocation end date input
		SeleniumUtils.findElement(webDriver, ALLOC_END_DATE_INP, 5).click();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(projEndDate);
		int allocEndMonth = cal.get(Calendar.MONTH);
		int allocEndDay = cal.get(Calendar.DAY_OF_MONTH);
		String allocEndYear = Integer.toString(cal.get(Calendar.YEAR));
		
		// Click month drop down of calendar
		Select dropMonth = SeleniumUtils.handleSelect(webDriver, CALENDER_MONTH, 5);
		dropMonth.selectByVisibleText(monthHM.get(allocEndMonth));
		
		// Click year drop down of calendar
		Select dropYear = SeleniumUtils.handleSelect(webDriver, CALENDER_YEAR, 5);
		dropYear.selectByVisibleText(allocEndYear);
		
		// Select Date
		SeleniumUtils.findElement(webDriver, By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr/td/a[text()='"+allocEndDay+"']"), 5).click();	
		
	}
	
	// Consultant allocation start date
	public void selectAllocationStartDateConsultant() throws ParseException {
		// Getting project start date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String projStartDateStr = SeleniumUtils.findElement(webDriver, PROJ_START_DATE, 5).getText();
		Date projStartDate = dateFormat.parse(projStartDateStr);
		
		String resJoinDate = null;
		String resEmpIdWeb = null;
		String resIDTestData = testData.get("consultant_id");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestData)) {
				resJoinDate = hmResources.get(i).get("data-joining-date");
				break;
			}
		}
		
		// Resource's joining date
		Date resJoiningDate = dateFormat.parse(resJoinDate);
		
		Date allocationStartDate = null;
		if(resJoiningDate.before(projStartDate)){
			allocationStartDate = projStartDate;
    	} else if (projStartDate.before(resJoiningDate)) {
    		allocationStartDate = resJoiningDate;
		}
		
		// Click allocation start date input
		SeleniumUtils.findElement(webDriver, ALLOC_START_DATE_INP, 5).click();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(allocationStartDate);
		int allocStartMonth = cal.get(Calendar.MONTH);
		int allocStartDay = cal.get(Calendar.DAY_OF_MONTH);
		String allocStartYear = Integer.toString(cal.get(Calendar.YEAR));
		
		
		// Click month drop down of calendar
		Select dropMonth = SeleniumUtils.handleSelect(webDriver, CALENDER_MONTH, 5);
		dropMonth.selectByVisibleText(monthHM.get(allocStartMonth));
		
		// Click year drop down of calendar
		Select dropYear = SeleniumUtils.handleSelect(webDriver, CALENDER_YEAR, 5);
		dropYear.selectByVisibleText(allocStartYear);
		
		// Select Date
		SeleniumUtils.findElement(webDriver, By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr/td/a[text()='"+allocStartDay+"']"), 5).click();	
		
	}
	
	
	/*
	 * Resource allocation start date
	 * Resource already has some allocations
	 * 
	 * @return void
	 */
	/*
	public void selectAllocationStartDateAlloc() throws ParseException {
		// Getting project start date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String projStartDateStr = SeleniumUtils.findElement(webDriver, PROJ_START_DATE, 5).getText();
		Date projStartDate = dateFormat.parse(projStartDateStr);
		
		String resJoinDate = null;
		String resEmpIdWeb = null;
		String resIDTestData = testData.get("resource_id_hund");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestData)) {
				resJoinDate = hmResources.get(i).get("data-joining-date");
				break;
			}
		}
		
		// Resource's joining date
		Date resJoiningDate = dateFormat.parse(resJoinDate);
		
		Date allocationStartDate = null;
		if(resJoiningDate.before(projStartDate)){
			allocationStartDate = projStartDate;
    	} else if (projStartDate.before(resJoiningDate)) {
    		allocationStartDate = resJoiningDate;
		}
		
		// Click allocation start date input
		SeleniumUtils.findElement(webDriver, ALLOC_START_DATE_INP, 5).click();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(allocationStartDate);
		int allocStartMonth = cal.get(Calendar.MONTH);
		int allocStartDay = cal.get(Calendar.DAY_OF_MONTH);
		String allocStartYear = Integer.toString(cal.get(Calendar.YEAR));
		
		
		// Click month drop down of calendar
		Select dropMonth = SeleniumUtils.handleSelect(webDriver, CALENDER_MONTH, 5);
		dropMonth.selectByVisibleText(monthHM.get(allocStartMonth));
		
		// Click year drop down of calendar
		Select dropYear = SeleniumUtils.handleSelect(webDriver, CALENDER_YEAR, 5);
		dropYear.selectByVisibleText(allocStartYear);
		
		// Select Date
		SeleniumUtils.findElement(webDriver, By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr/td/a[text()='"+allocStartDay+"']"), 5).click();	
		
	}
	
	// Consultant allocation end date
	public void selectAllocationEndDateConsultant() throws ParseException {
		//Getting project end date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String projEndDateStr = SeleniumUtils.findElement(webDriver, PROJ_END_DATE, 5).getText();
		Date projEndDate = dateFormat.parse(projEndDateStr);
		
		String resLeaveDate = null;
		String resEmpIdWeb = null;
		String resIDTestData = testData.get("consultant_id");
		
		for(int i = 1; i <= hmResources.size(); i++) {
			resEmpIdWeb = hmResources.get(i).get("data-emp-id");
			if(resEmpIdWeb.equals(resIDTestData)) {
				resLeaveDate = hmResources.get(i).get("data-leaving-date");
				break;
			}
		}
		
		// Resource's joining date
		Date resLeavingDate = dateFormat.parse(resLeaveDate);
		
		Date allocationEndDate = null;
		if(resLeavingDate.before(projEndDate)){
			allocationEndDate = resLeavingDate;
    	} else if (projEndDate.before(resLeavingDate)) {
    		allocationEndDate = projEndDate;
		}
		
		// Click allocation end date input
		SeleniumUtils.findElement(webDriver, ALLOC_END_DATE_INP, 5).click();
		
		
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(allocationEndDate);
		int projEndMonth = cal.get(Calendar.MONTH);
		int projEndDay = cal.get(Calendar.DAY_OF_MONTH);
		String allocEndYear = Integer.toString(cal.get(Calendar.YEAR));
		
		// Click month drop down of calendar
		Select drop = SeleniumUtils.handleSelect(webDriver, CALENDER_MONTH, 5);
		drop.selectByVisibleText(monthHM.get(projEndMonth));
		
		// Click year drop down of calendar
		Select dropYear = SeleniumUtils.handleSelect(webDriver, CALENDER_YEAR, 5);
		dropYear.selectByVisibleText(allocEndYear);
				
		// Select Date
		SeleniumUtils.findElement(webDriver, By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr/td/a[text()='"+projEndDay+"']"), 5).click();	
		
	}
	
	
	public void enterPerAllocation() {
		SeleniumUtils.findElement(webDriver, PER_ALLOCATION, 5).clear();
		SeleniumUtils.findElement(webDriver, PER_ALLOCATION, 5).sendKeys(testData.get("alloc_percentage").toString().trim());
	}
	
	public void enterPerAllocationHundred() {
		SeleniumUtils.findElement(webDriver, PER_ALLOCATION, 5).clear();
		SeleniumUtils.findElement(webDriver, PER_ALLOCATION, 5).sendKeys("100");
	}
	
	public void clickAdd() {
		SeleniumUtils.findElement(webDriver, ADD_BUTTON, 5).click();
	}
	
	public boolean checkResAllocationSuccess() {
		return SeleniumUtils.checkElementExist(webDriver, PROJECT_LIST, 5);
	}
	
	public String getMessage() {
		return SeleniumUtils.findElement(webDriver, MESSAGE, 5).getText();
	}
	
	*/
	

}
