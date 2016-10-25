package com.icreon.res_allocqa.pages;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class RoleMappingPage {
	SeleniumUtils st;
	WebDriver driver;
	
	By SEARCH_EMPNAME_INP = By.xpath(".//*[@id='search_role']/div[1]/input");
	By SEARCH_EMPID_INP = By.xpath(".//*[@id='search_role']/div[2]/input");
	By SEARCH_STATUS = By.xpath(".//*[@id='status']");
	By SEARCH_BUTTON = By.xpath(".//*[@id='search_role']/div[4]/input");
	
	By ROLE_TABLE_EMPID = By.xpath(".//*[@id='role-tbl']/tbody/tr/td[1]");
	By ROLE_TABLE_EMPNAME = By.xpath(".//*[@id='role-tbl']/tbody/tr/td[2]");
	By ROLE_TABLE_DESIGNATION = By.xpath(".//*[@id='role-tbl']/tbody/tr/td[3]");
	By ROLE_TABLE_EMAIL = By.xpath(".//*[@id='role-tbl']/tbody/tr/td[4]");
	By ROLE_TABLE_EDIT_BUT = By.xpath(".//*[@id='role-tbl']/tbody/tr/td[last()]/div/a[1]");
	By ROLE_TABLE_HEAD_EMPID = By.xpath(".//*[@id='role-tbl']/thead/tr/th[1]/a");
	By ROLE_TABLE_HEAD_NAME = By.xpath(".//*[@id='role-tbl']/thead/tr/th[2]/a");
	By ROLE_TABLE_HEAD_DESIGNATION = By.xpath(".//*[@id='role-tbl']/thead/tr/th[3]/a");
	By ROLE_TABLE_HEAD_EMAIL = By.xpath(".//*[@id='role-tbl']/thead/tr/th[4]/a");
	
	By PAGINATION_SELECT = By.xpath(".//*[@id='record-per-page-frm']/select");
	
	
	public RoleMappingPage(WebDriver webDriver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	public void clickSearch(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SEARCH_BUTTON, 5, "Search Button");
	}
	
	public void enterEmpNameInSearch(ExtentTest extentTest, String empName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, SEARCH_EMPNAME_INP, 5, "Employee Name field in search form", empName);
	}
	
	public void enterEmpIdInSearch(ExtentTest extentTest, String empName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, SEARCH_EMPID_INP, 5, "Employee Name field in search form", empName);
	}
	
	public ArrayList<WebElement> getEmpIdsFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, ROLE_TABLE_EMPID, 5, "Employee name");
	}
	
	public ArrayList<WebElement> getNamesFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, ROLE_TABLE_EMPNAME, 5, "Employee name");
	}
	
	public ArrayList<WebElement> getDesignationsFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, ROLE_TABLE_DESIGNATION, 5, "Employee name");
	}
	
	public ArrayList<WebElement> getEmailsFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, ROLE_TABLE_EMAIL, 5, "Employee name");
	}
	
	public ArrayList<WebElement> getEditButtons(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, ROLE_TABLE_EDIT_BUT, 5, "Edit button");
	}
	
	public void selectActive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_STATUS, 5, "Status drop down in search form");
		drop.selectByValue("ACTIVE");
	}
	
	public void selectInactive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_STATUS, 5, "Status drop down in search form");
		drop.selectByValue("INACTIVE");
	}
	
	public void clickEmpIdHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ROLE_TABLE_HEAD_EMPID, 5, "EmpId column heading");
	}
	
	public void clickNameHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ROLE_TABLE_HEAD_NAME, 5, "EmpId column heading");
	}
	
	public void clickDesignationHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ROLE_TABLE_HEAD_DESIGNATION, 5, "EmpId column heading");
	}
	
	public void clickEmailHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ROLE_TABLE_HEAD_EMAIL, 5, "EmpId column heading");
	}
	
	public void selectPagiation(ExtentTest extentTest, String n) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, PAGINATION_SELECT, 5, "Pagination drop down");
		drop.selectByValue(n);
	}
}
