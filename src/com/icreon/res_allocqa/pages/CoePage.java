package com.icreon.res_allocqa.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class CoePage {
	SeleniumUtils st;
	WebDriver driver;
	
	By ADD_SKILL_COE = By.xpath("html/body/div[1]/div[2]/div[2]/div[1]/a");
	By ADD_SKILL_COE_INPUT = By.xpath(".//*[@id='newCOE_1']");
	By SAVE_SKILL_COE_BUTTON = By.xpath(".//*[@id='newCOEtr_1']/td[2]/div/a[1]");
	By Update_COE_INPUT = By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td/input");
	By SAVE_UPDATE_COE = By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td/input/parent::td/following-sibling::td/div/a[1]");
	By CONFIRM_DEACTIVATE = By.xpath(".//*[@id='cboxLoadedContent']/div/div/div/a[1]");
	By STATUS = By.id("status");
	By SEARCH_BUTTON = By.xpath(".//*[@id='search_coe']/div[3]/input");
	By COE_COL_HEAD = By.xpath(".//*[@id='manage-coe-tbl']/thead/tr/th[1]/a");
	By COE_NAME_INPUT = By.xpath(".//*[@id='coeSearch1']");
	By COE_NAMEs_IN_TABLE = By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[1]");
	By COE_EDIT_BUTTON = By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[2]/div/a[1]");
	By ACTIVATE_COE_BUTTONS = By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[2]/div/a[@title='Activate']");
	By PAGINATION_SELECT = By.xpath(".//*[@id='record-per-page-frm']/select");
	
	public CoePage(WebDriver driver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	public void getURLStatusCode(ExtentTest extentTest) throws IOException, InterruptedException {
		List<WebElement> anchorList = driver.findElements(By.tagName("a"));
		st.getURLStatusCode(extentTest, anchorList);
	}

	public void clickAddSkillCOE(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ADD_SKILL_COE, 5, "Add Skill COE link");
	}
	
	public void enterSkillCOE(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, ADD_SKILL_COE_INPUT, 5, "Skill COE input", coeName);
	}
	
	public void clickSaveSkillCOE(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SAVE_SKILL_COE_BUTTON, 5, "Save button");
	}
	
	public String checkAddCOESuccess(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		return st.getText(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']"), 5, "New added COE");
	}
	
	public void clickEditSkillCOE(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']/following-sibling::td/div/a[1]"), 5, "Edit button");
	}
	
	public void enterSkillCOEUpdate(ExtentTest extentTest, String updatedCoeName) throws IOException, InterruptedException {
		st.doClearInput(extentTest, driver, Update_COE_INPUT, 5, "Skill COE input");
		st.enterText(extentTest, driver, Update_COE_INPUT, 5, "Skill COE input", updatedCoeName);
	}
	
	public void clickSaveUpdateSkillCOE(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SAVE_UPDATE_COE, 5, "Save button");
	}
	
	public String checkEditCOESuccess(ExtentTest extentTest, String coeNameUpdate) throws IOException, InterruptedException {
		return st.getText(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeNameUpdate+"']"), 5, "Updated COE");
	}
	
	public void clickDeactivateSkillCOE(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']/following-sibling::td/div/a[2]"), 5, "Deactivate button");
	}
	
	public void clickConfirmDeactivateSkillCOE(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, CONFIRM_DEACTIVATE, 5, "Confirm(Deactivate) button");
	}
	
	public void selectInactive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, STATUS, 5, "Select status");
		drop.selectByValue("INACTIVE");
	}
	public void clickSearch(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SEARCH_BUTTON, 5, "Search Button");
	}
	
	public boolean checkInactiveCOESuccess(ExtentTest extentTest, String coeName) throws IOException {
		return st.checkElementExist(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']"), 5, "Deactivated COE");
	}
	
	public void clickActivateSkillCOE(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']/following-sibling::td/div/a"), 5, "Activate button");
	}
	
	public void selectActive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, STATUS, 5, "Select status");
		drop.selectByValue("ACTIVE");
	}
	
	public boolean checkActiveCOESuccess(ExtentTest extentTest, String coeName) throws IOException {
		return st.checkElementExist(extentTest, driver, By.xpath(".//*[@id='manage-coe-tbl']/tbody/tr/td[text()='"+coeName+"']"), 5, "Deactivated COE");
	}
	
	public void enterCOEinSearch(ExtentTest extentTest, String coeName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, COE_NAME_INPUT, 5, "Skill COE input", coeName);
	}
	
	public ArrayList<WebElement> getRecords(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> COEList = st.getRecoprdsFromTable(extentTest, driver, COE_NAMEs_IN_TABLE, 5, "COE names in table");
		return COEList;
	}
	
	public void clearSearchCOEName(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClearInput(extentTest, driver, COE_NAME_INPUT, 5, "COE Name input");
	}
	
	public ArrayList<WebElement> getEditButtons(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> editButtons = st.getRecoprdsFromTable(extentTest, driver, COE_EDIT_BUTTON, 5, "COE edit button");
		return editButtons;
	}
	
	public ArrayList<WebElement> getActivateButtons(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> activateButtons = st.getRecoprdsFromTable(extentTest, driver, ACTIVATE_COE_BUTTONS, 5, "COE activate button");
		return activateButtons;
	}
	
	public void selectPagiation(ExtentTest extentTest, String n) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, PAGINATION_SELECT, 5, "Pagination drop down");
		drop.selectByValue(n);
	}
	
	public void clickCOEColumnHeading(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, COE_COL_HEAD, 5, "COE column heading");
	}
	
	
}
