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

public class SkillPage {
	SeleniumUtils st;
	WebDriver driver;
	
	By ADD_SKILLS = By.xpath("html/body/div[1]/div[2]/div[2]/div[1]/a");
	By ADD_NEW_SKILL_INPUT = By.id("newSkillName_1");
	By COE_SELECT = By.id("newCoeName_1");
	By SAVE_SKILLS_BUTTON = By.xpath(".//*[@id='newSkilltr_1']/td[3]/div/a[1]");
	By NEW_SKILL = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr[1]/td[1]");
	By EDIT_SKILL_INPUT = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[1]/input");
	By SAVE_UPDATED_SKILL_BUT = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td/input/parent::td/following-sibling::td[2]/div/a[1]");
	By ACCEPT_DEACTIVATE_SKILL = By.xpath(".//*[@id='cboxLoadedContent']/div/div/div/a[1]");
	By STATUS = By.id("status");
	By SEARCH_BUTTON = By.xpath(".//*[@id='search_skills']/div[4]/input");
	By SEARCH_SKILL_INP = By.xpath(".//*[@id='search_skills']/div[1]/input");
	By SEARCH_COE_DROP = By.xpath(".//*[@id='search_skills']/div[2]/select");
	By SKILL_RECORDS = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[1]");
	By SKILL_COE_RECORDS = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[2]/span/select/option[@selected='selected']");
	By SKILL_EDIT_BUTTON = By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[3]/div/a[1]");
	By SKILL_NAME_HEADER = By.xpath(".//*[@id='skill-name-header']/a");
	By COE_NAME_HEADER = By.xpath(".//*[@id='manage-Skill-tbl']/thead/tr/th[2]/a");
	By PAGINATION_SELECT = By.xpath(".//*[@id='record-per-page-frm']/select");
	
	public SkillPage(WebDriver webDriver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	public void clickAddNewSkills(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ADD_SKILLS, 5, "Add skill link");
	}
	
	public void enterNewSkill(ExtentTest extentTest, String skillName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, ADD_NEW_SKILL_INPUT, 5, "Skill input", skillName);
	}
	
	public void selectSkillCOE(ExtentTest extentTest, String coeName) throws InterruptedException, IOException {
		Select drop = st.handleSelect(extentTest, driver, COE_SELECT, 5, "Select Skill COE");
		drop.selectByVisibleText(coeName);
	}
	
	public void clickSaveSkills(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SAVE_SKILLS_BUTTON, 5, "Skill sace button");
	}
	
	public String checkAddSkillsSuccess(ExtentTest extentTest, String skill) throws IOException, InterruptedException {
		return st.getText(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skill+"']"), 5,  "New added skill");
	}
	
	public void clickEditSkill(ExtentTest extentTest, String skillName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skillName+"']/following-sibling::td[2]/div/a[1]"), 5, "Skill edit button");
	}
	
	public void enterSkillUpdate(ExtentTest extentTest, String skillNameUpdated) throws IOException, InterruptedException {
		st.doClearInput(extentTest, driver, EDIT_SKILL_INPUT, 5, "Edit skill input");
		st.enterText(extentTest, driver, EDIT_SKILL_INPUT, 5, "Edit skill input", skillNameUpdated);
	}
	
	public void clickSaveUpdateSkill(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SAVE_UPDATED_SKILL_BUT, 5, "Save button");
	}
	
	public String checkEditSkillSuccess(ExtentTest extentTest, String skillNameUpdate) throws IOException, InterruptedException {
		return st.getText(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skillNameUpdate+"']"), 5, "Updated skill");
	}
	
	public void clickDeactivateSkill(ExtentTest extentTest, String skillName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skillName+"']/following-sibling::td[2]/div/a[2]"), 5, "Deactivate button");
	}
	
	public void clickConfirmActiveDeactive(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ACCEPT_DEACTIVATE_SKILL, 5, "Confirm deactivate button");
	}
	
	public void selectInactive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, STATUS, 5, "Status drop down in search form");
		drop.selectByValue("INACTIVE");
	}
	
	public void selectActive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, STATUS, 5, "Status drop down");
		drop.selectByValue("ACTIVE");
	}
	
	public void clickSearch(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SEARCH_BUTTON, 5, "Search Button");
	}
	
	public boolean checkInactiveActiveSkillSuccess(ExtentTest extentTest, String skillName) throws IOException {
		return st.checkElementExist(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skillName+"']"), 5, "Deactivated skill");
	}
	
	public void clickActivateSkill(ExtentTest extentTest, String skillName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='manage-Skill-tbl']/tbody/tr/td[text()='"+skillName+"']/following-sibling::td[2]/div/a"), 5, "Activate skill button");
	}
	
	public void enterSkillInSearch(ExtentTest extentTest, String skillName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, SEARCH_SKILL_INP, 5, "Skill input field in search form", skillName);
	}
	
	public void selectSkillCOESearch(ExtentTest extentTest, String coeName) throws InterruptedException, IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_COE_DROP, 5, "Skill COE drop down in search form");
		drop.selectByVisibleText(coeName);
	}
	
	public ArrayList<WebElement> getRecords(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> recList = st.getRecoprdsFromTable(extentTest, driver, SKILL_RECORDS, 5, "Skill names in table");
		return recList;
	}
	
	public ArrayList<WebElement> getCOERecords(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> recList = st.getRecoprdsFromTable(extentTest, driver, SKILL_COE_RECORDS, 5, "Skill names in table");
		return recList;
	}
	
	public ArrayList<WebElement> getEditButtons(ExtentTest extentTest) throws IOException {
		ArrayList<WebElement> editButtons = st.getRecoprdsFromTable(extentTest, driver, SKILL_EDIT_BUTTON, 5, "COE edit button");
		return editButtons;
	}
	
	public void clickSkillHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SKILL_NAME_HEADER, 5, "Skill header");
	}
	
	public void clickCOEHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, COE_NAME_HEADER, 5, "COE header");
	}
	
	public void selectPagiation(ExtentTest extentTest, String n) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, PAGINATION_SELECT, 5, "Pagination drop down");
		drop.selectByValue(n);
	}
	
}
