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

public class ClientsPage {
	SeleniumUtils st;
	WebDriver driver;
	
	By CLIENTS_TABLE = By.xpath(".//*[@id='clients-tbl']");
	
	By ADD_CLIENT = By.xpath("html/body/div[1]/div[2]/div[2]/div[1]/a");
	By ADD_CLIENT_NAME_INP = By.id("newClientName_1");
	By ADD_CLIENT_Country_DROP = By.id("newCountryName_1");
	By ADD_CLIENT_OWNER_DROP = By.id("newAccOwnerName_1");
	By ADD_CLIENT_SAVE_BUT = By.xpath(".//*[@id='newClienttr_1']/td[4]/div/a[1]");
	By EDIT_CLIENT_NAME_INP = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td/input");
	By EDIT_CLIENT_SAVE_BUT = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td/input/parent::td/following-sibling::td[3]/div/a[1]");
	
	By SEARCH_CLIENT_NAME_INP = By.xpath(".//*[@id='search_client']/div[1]/input");
	By SEARCH_ACC_OWNER_DROP = By.xpath(".//*[@id='search_client']/div[2]/select");
	By SEARCH_COUNTRY_DROP = By.xpath(".//*[@id='search_client']/div[3]/select");
	By SEARCH_STATUS_DROP = By.xpath(".//*[@id='status']");
	By SEARCH_BUTTON = By.xpath(".//*[@id='search_client']/div[5]/input");
	
	By TABLE_CLIENT_NAME = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[1]");
	By TABLE_ACCOUNT_OWNER = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[3]/span/select/option[@selected='selected']");
	By TABLE_COUNTRY = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[2]/span/select/option[@selected='selected']");
	By TABLE_EDIT_BUTTON = By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[last()]/div/a[1]");
	
	By THEAD_CLIENT_NAME = By.xpath(".//*[@id='skill-name-header']/a");
	By THEAD_COUNTRY = By.xpath(".//*[@id='clients-tbl']/thead/tr/th[2]/a");
	By THEAD_ACCOUNT_OWNER = By.xpath(".//*[@id='clients-tbl']/thead/tr/th[3]/a");
	
	By PAGINATION = By.xpath(".//*[@id='record-per-page-frm']/select");
	
	
	public ClientsPage(WebDriver webDriver) {
		st = new SeleniumUtils();
		this.driver = WebDriverFactory.getWebDriver();
	}
	
	// For adding client
	public void clickAddClientLink(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ADD_CLIENT, 5, "Add client link");
	}
	
	public void enterClientNameAdd(ExtentTest extentTest, String clientName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, ADD_CLIENT_NAME_INP, 5, "Client Name field in add client form", clientName);
	}
	
	public void selectAccountOwnerAdd(ExtentTest extentTest, String accOwner) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, ADD_CLIENT_OWNER_DROP, 5, "Account Owner drop down in add client form");
		drop.selectByVisibleText(accOwner);
	}
	
	public void selectCountryAdd(ExtentTest extentTest, String country) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, ADD_CLIENT_Country_DROP, 5, "Country drop down in add client form");
		drop.selectByVisibleText(country);
	}
	
	public void clickSaveClientButton(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, ADD_CLIENT_SAVE_BUT, 5, "Add client save button");
	}
	
	public boolean checkNewClientAdded(ExtentTest extentTest, String newClient) throws IOException {
		return st.checkElementExist(extentTest, driver, By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[text()='"+newClient+"']"), 4, "New client");		
	}
	
	public void clickEditClientLink(ExtentTest extentTest, String clientName) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, By.xpath(".//*[@id='clients-tbl']/tbody/tr/td[text()='"+clientName+"']/following-sibling::td[3]/div/a[1]"), 5, "Edit client link");
	}
	
	public void enterClientNameEdit(ExtentTest extentTest, String clientName) throws IOException, InterruptedException {
		st.doClearInput(extentTest, driver, EDIT_CLIENT_NAME_INP, 5, "Client Name field in edit client form");
		st.enterText(extentTest, driver, EDIT_CLIENT_NAME_INP, 5, "Client Name field in edit client form", clientName);
	}
	
	public void clickSaveEditClientButton(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, EDIT_CLIENT_SAVE_BUT, 5, "Edit client save button");
	}
	
	
	
	
	public boolean checkClientsTableExist(ExtentTest extentTest) throws IOException {
		return st.checkElementExist(extentTest, driver, CLIENTS_TABLE, 4, "Clients table");		
	}
	
	public void enterClientNameInSearch(ExtentTest extentTest, String clientName) throws IOException, InterruptedException {
		st.enterText(extentTest, driver, SEARCH_CLIENT_NAME_INP, 5, "Client Name field in search form", clientName);
	}
	
	public void clickSearch(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, SEARCH_BUTTON, 5, "Search Button");
	}
	
	public ArrayList<WebElement> getClientNameFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, TABLE_CLIENT_NAME, 5, "Client name");
	}
	
	public ArrayList<WebElement> getAccOwnerFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, TABLE_ACCOUNT_OWNER, 5, "Account owner");
	}
	
	public ArrayList<WebElement> getCountryFromTable(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, TABLE_COUNTRY, 5, "Country");
	}
	
	public void selectAccountOwner(ExtentTest extentTest, String accOwner) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_ACC_OWNER_DROP, 5, "Account Owner drop down in search form");
		drop.selectByVisibleText(accOwner);
	}
	
	public void selectCountry(ExtentTest extentTest, String country) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_COUNTRY_DROP, 5, "Country drop down in search form");
		drop.selectByVisibleText(country);
	}
	
	public void selectActive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_STATUS_DROP, 5, "Status drop down in search form");
		drop.selectByValue("ACTIVE");
	}
	
	public void selectInactive(ExtentTest extentTest) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, SEARCH_STATUS_DROP, 5, "Status drop down in search form");
		drop.selectByValue("INACTIVE");
	}
	
	public ArrayList<WebElement> getEditButtons(ExtentTest extentTest) throws IOException {
		return st.getRecoprdsFromTable(extentTest, driver, TABLE_EDIT_BUTTON, 5, "Edit button");
	}
	
	public void clickClientNameHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, THEAD_CLIENT_NAME, 5, "Client Name column heading");
	}
	
	public void clickCountryHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, THEAD_COUNTRY, 5, "Country column heading");
	}
	
	public void clickAccOwnerHeader(ExtentTest extentTest) throws IOException, InterruptedException {
		st.doClick(extentTest, driver, THEAD_ACCOUNT_OWNER, 5, "Account Owner column heading");
	}
	
	public void selectPagiation(ExtentTest extentTest, String n) throws IOException {
		Select drop = st.handleSelect(extentTest, driver, PAGINATION, 5, "Pagination drop down");
		drop.selectByValue(n);
	}
}
