package com.icreon.res_allocqa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.icreon.res_allocqa.helpers.WebDriverFactory;
import com.icreon.res_allocqa.utilities.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class HeaderMenu {
	SeleniumUtils st;
	WebDriver driver;
	
	// Menus to be hover
	By SETUP_MENU = By.xpath("html/body/div[1]/ul/li[1]/a");
	By REPORTS = By.xpath("html/body/div[1]/ul/li[2]/a");
	
	// Menus to be clicked
	By SKILL_COE = By.xpath("html/body/div[1]/ul/li[1]/ul/li[1]/a");
	By SKILLS = By.xpath("html/body/div[1]/ul/li[1]/ul/li[2]/a");
	By ROLE_MAPPING = By.xpath("html/body/div[1]/ul/li[1]/ul/li[3]/a");
	By CLIENTS = By.xpath("html/body/div[1]/ul/li[1]/ul/li[4]/a");
	By PROJECTS = By.xpath("html/body/div[1]/ul/li[1]/ul/li[5]/a");
	By RESOURCE_MAPPING = By.xpath("html/body/div[1]/ul/li[1]/ul/li[6]/a");
	By CONSULTANT_MAPPING = By.xpath("html/body/div[1]/ul/li[1]/ul/li[7]/a");
	By RESOURCE_ALLOC_RM = By.xpath("html/body/div[1]/ul/li[1]/ul/li/a");
	By PAGE_HEADING = By.xpath("html/body/div[1]/div[2]/div[1]/h2");
	
	
	public HeaderMenu(WebDriver webDriver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	public void moveToSetup(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doMoveControl(extentTest, driver, SETUP_MENU, 5, "Setup menu");
	}
	
	public void clickSkillCOE(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SKILL_COE, 5, "Skill COE menu");
	}
	
	public void clickSkills(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SKILLS, 5, "Skills menu");
	}
	
	public void clickRoleMapping(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ROLE_MAPPING, 5, "Skills menu");
	}
	
	public void clickClients(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, CLIENTS, 5, "Skills menu");
	}
	
	public String getPageHeading(ExtentTest extentTest) throws IOException, InterruptedException {
		return st.getText(extentTest, driver, PAGE_HEADING, 5, "Page Heading");
	}
	
}
