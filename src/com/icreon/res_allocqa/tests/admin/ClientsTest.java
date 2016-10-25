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
import com.icreon.res_allocqa.pages.ClientsPage;
import com.icreon.res_allocqa.pages.HeaderMenu;
import com.icreon.res_allocqa.utilities.JavaUtils;
import com.icreon.res_allocqa.utilities.PropertiesUtils;
import com.relevantcodes.extentreports.LogStatus;

import base.testBase;

public class ClientsTest extends testBase {
	static String methodName;
	ClientsPage cp;
	HeaderMenu hm;
	WebDriver webDriver;
	JavaUtils jUtils;
	HashMap<String, String> testData;
	public static String clientName;
	public static String accountOwner;
	public static String country;
	public static String clientNametoADD;
	public static String incrementedclientName;
	public static String clientNameEdit;
	public static String clientsTestDataFile = "ClientsTestData.xls";
	
	public ClientsTest() throws IOException {
		super();
		ExcelHelper eh = new ExcelHelper();
		jUtils = new JavaUtils();
		testData = eh.getTestData(PropertiesUtils.getPropVal("test.data")+clientsTestDataFile);
		clientName = testData.get("ClientName");
		accountOwner = testData.get("AccOwner");
		country = testData.get("Country");
		clientNametoADD = testData.get("ClientNametoAdd");
		
		// Logic for incrementing COE name by 1 to make it dynamic because input field is accepting only unique COE
		String[] clientArr = clientNametoADD.split("_");
		int incrementVal = Integer.parseInt(clientArr[1])+1;
		incrementedclientName = clientArr[0]+"_"+Integer.toString(incrementVal);
		
		// Updating data value in COE test data file
		eh.updateTestDataValue(PropertiesUtils.getPropVal("test.data")+clientsTestDataFile, "ClientNametoAdd", incrementedclientName);
		
		// Getting value for updating COE name
		clientNameEdit = testData.get("ClientNametoEdit");
		clientNameEdit = clientNametoADD+clientNameEdit;
		
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
	public void navigateToClients() throws IOException, InterruptedException {
		setUpExtent("Navigate to Clients", "Testing that user is navigating to 'Clients' page : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		
		// Checking success by comparing page heading
		if(cp.checkClientsTableExist(extentTest)) {
			extentTest.log(LogStatus.PASS, "User successfully navigated to 'Clients' page." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "User not navigated to 'Clients' page.", image);
			extentTest.log(LogStatus.FAIL, "User not navigated to 'Clients' page.");
		}
		tearDownExtent();
	}
	
	@Test(priority=2)
	public void testEditClient() throws IOException, InterruptedException {
		setUpExtent("Add Client", "Testing that user is able to add 'Client' : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickAddClientLink(extentTest);
		cp.enterClientNameAdd(extentTest, clientNametoADD);
		cp.selectAccountOwnerAdd(extentTest, accountOwner);
		cp.selectCountryAdd(extentTest, country);
		cp.clickSaveClientButton(extentTest);
				
		// Checking success		
		if(cp.checkNewClientAdded(extentTest, clientNametoADD)) {
			extentTest.log(LogStatus.PASS, "New client '"+clientNametoADD+"' added successfully." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "New client '"+clientNametoADD+"' not added.", image);
			extentTest.log(LogStatus.FAIL, "New client '"+clientNametoADD+"' not added.");
		}
		tearDownExtent();
	}
	
	@Test(priority=3)
	public void testAddClient() throws IOException, InterruptedException {
		setUpExtent("Edit Client", "Testing that user is able to edit 'Client' : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickEditClientLink(extentTest, clientNametoADD);
		cp.enterClientNameEdit(extentTest, clientNameEdit);
		cp.clickSaveEditClientButton(extentTest);
				
		// Checking success		
		if(cp.checkNewClientAdded(extentTest, clientNameEdit)) {
			extentTest.log(LogStatus.PASS, "Client '"+clientNametoADD+"' edited to '"+clientNameEdit+"' successfully." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR,  "Client '"+clientNametoADD+"' not edited to '"+clientNameEdit+"' successfully.", image);
			extentTest.log(LogStatus.FAIL,  "Client '"+clientNametoADD+"' not edited to '"+clientNameEdit+"' successfully.");
		}
		tearDownExtent();
	}
	
	
	@Test(priority=4, groups= {"All",  "Search"})
	public void testSearchClientByName() throws InterruptedException, IOException {
		setUpExtent("Test Search Clients by Name", "Testing search client by name : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		boolean isStrinfFound = true;
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.enterClientNameInSearch(extentTest, clientName);
		cp.clickSearch(extentTest);
		ArrayList<WebElement> clientRecords = cp.getClientNameFromTable(extentTest);
		for (int i = 0; i < clientRecords.size(); i++) {
			if(!clientRecords.get(i).getText().contains(clientName)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Clients searched by name found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Clients searched by name not found.", image);
			extentTest.log(LogStatus.FAIL, "Clients searched by name not found.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=5, groups= {"All",  "Search"})
	public void testSearchClientByAccOwner() throws InterruptedException, IOException {
		setUpExtent("Test Search Clients by Account Owner", "Testing search client by Account Owner : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		boolean isStrinfFound = true;
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.selectAccountOwner(extentTest, accountOwner);
		cp.clickSearch(extentTest);
		ArrayList<WebElement> accOwnerRecords = cp.getAccOwnerFromTable(extentTest);
		for (int i = 0; i < accOwnerRecords.size(); i++) {
			if(!accOwnerRecords.get(i).getText().contains(accountOwner)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Client searched by Account Owner found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR,  "Client searched by Account Owner not found." , image);
			extentTest.log(LogStatus.FAIL,  "Client searched by Account Owner not found." );
		}	
		tearDownExtent();
	}
	
	@Test(priority=6, groups= {"All",  "Search"})
	public void testSearchClientByCountry() throws InterruptedException, IOException {
		setUpExtent("Test Search Clients by Country", "Testing search client by country : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		boolean isStrinfFound = true;
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.selectCountry(extentTest, country);
		cp.clickSearch(extentTest);
		
		ArrayList<WebElement> countryRecords = cp.getCountryFromTable(extentTest);
		for (int i = 0; i < countryRecords.size(); i++) {
			if(!countryRecords.get(i).getText().contains(country)) {
				isStrinfFound = false;
				break;
			} 
		}

		//Check Success
		if(isStrinfFound) {
			extentTest.log(LogStatus.PASS, "Client searched by country found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR,  "Client searched by country found." , image);
			extentTest.log(LogStatus.FAIL,  "Client searched by country not found." );
		}	
		tearDownExtent();
	}
	
	@Test(priority=7, groups= {"All",  "Search"})
	public void testSearchClientByStatusActive() throws InterruptedException, IOException {
		setUpExtent("Test Search Clients by Status Active", "Testing search client by status active : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.selectActive(extentTest);
		cp.clickSearch(extentTest);
		
		ArrayList<WebElement> clientRecords = cp.getClientNameFromTable(extentTest);
		ArrayList<WebElement> editButtons = cp.getEditButtons(extentTest); // Edit and Activate buttons have same xpath
		
		//Check Success
		if(clientRecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Active' clients found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Active' clients not found.", image);
			extentTest.log(LogStatus.FAIL, "'Active' clients not found.");
		}		
		tearDownExtent();
	}
	
	@Test(priority=8, groups= {"All",  "Search"})
	public void testSearchClientByStatusInactive() throws InterruptedException, IOException {
		setUpExtent("Test Search Clients by Status Inactive", "Testing search client by status inactive : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.selectInactive(extentTest);
		cp.clickSearch(extentTest);
		
		ArrayList<WebElement> clientRecords = cp.getClientNameFromTable(extentTest);
		ArrayList<WebElement> editButtons = cp.getEditButtons(extentTest); // Edit and Activate buttons have same xpath
		
		//Check Success
		if(clientRecords.size() == editButtons.size()) {
			extentTest.log(LogStatus.PASS, "'Inactive' clients found." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "'Inactive' clients not found.", image);
			extentTest.log(LogStatus.FAIL, "'Inactive' clients not found.");
		}		
		tearDownExtent();
	}
	
	@Test(priority=9, groups= {"All",  "Sorting"})
	public void testSortingClientByNameAscending() throws InterruptedException, IOException {
		setUpExtent("Test Client Sorting by Name(Ascending)", "Testing clients sorting by name in ascending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickClientNameHeader(extentTest);
		ArrayList<WebElement> clientRecords = cp.getClientNameFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(clientRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in ascending order of client names." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in ascending order of client names." , image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in ascending order of client names." );
		}	
		tearDownExtent();
	}
	
	@Test(priority=10, groups= {"All",  "Sorting"})
	public void testSortingClientByNameDescending() throws InterruptedException, IOException {
		setUpExtent("Test Client Sorting by Name(Descending)", "Testing clients sorting by name in descending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickClientNameHeader(extentTest);
		cp.clickClientNameHeader(extentTest);
		ArrayList<WebElement> clientRecords = cp.getClientNameFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(clientRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in ascending order of client names." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in descending order of client names." , image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in descending order of client names." );
		}	
		tearDownExtent();
	}
	
	
	@Test(priority=11, groups= {"All",  "Sorting"})
	public void testSortingClientByCountryAscending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Country(Ascending)", "Testing role sorting by country in ascending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickCountryHeader(extentTest);
		ArrayList<WebElement> countryRecords = cp.getCountryFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(countryRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in ascending order of country." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in ascending order of country.", image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in ascending order of country.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=12, groups= {"All",  "Sorting"})
	public void testSortingClientByCountryDescending() throws InterruptedException, IOException {
		setUpExtent("Test Role Sorting by Country(Descending)", "Testing role sorting by country in descending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickCountryHeader(extentTest);
		cp.clickCountryHeader(extentTest);
		ArrayList<WebElement> countryRecords = cp.getCountryFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(countryRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in descending order of country." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in descending order of country.", image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in descending order of country.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=13, groups= {"All",  "Sorting"})
	public void testSortingClientByAccOwnerAscending() throws InterruptedException, IOException {
		setUpExtent("Test Client Sorting by Account Owner(Ascending)", "Testing clients sorting by account Owner in ascending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());		
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickAccOwnerHeader(extentTest);
		ArrayList<WebElement> accOwnerRecords = cp.getAccOwnerFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingAscending(accOwnerRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in ascending order of account Owner." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in ascending order of account Owner.", image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in ascending order of account Owner.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=14, groups= {"All",  "Sorting"})
	public void testSortingClientByAccOwnerDescending() throws InterruptedException, IOException {
		setUpExtent("Test Client Sorting by Account Owner(Descending)", "Testing clients sorting by account Owner in descending order : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.clickAccOwnerHeader(extentTest);
		cp.clickAccOwnerHeader(extentTest);
		ArrayList<WebElement> accOwnerRecords = cp.getAccOwnerFromTable(extentTest);
		boolean isSorted = jUtils.checkStringSortingDescending(accOwnerRecords);
		
		//Check Success
		if(isSorted) {
			extentTest.log(LogStatus.PASS, "Client records sorted in descending order of account Owner." );
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Client records not sorted in descending order of account Owner.", image);
			extentTest.log(LogStatus.FAIL, "Client records not sorted in descending order of account Owner.");
		}	
		tearDownExtent();
	}
	
	@Test(priority=15, groups= {"All", "Pagination"})
	public void testClientPagination() throws InterruptedException, IOException {
		setUpExtent("Test Pagination on 'Manage Clients' Page", "Testing pagination on 'Manage Clients' page : : Admin > Manage Clients");
		extentTest.log(LogStatus.INFO, "Class -> "+this.getClass().getName());	
		extentTest.log(LogStatus.INFO, "Method -> "+Thread.currentThread().getStackTrace()[1].getMethodName());	
		
		cp = new ClientsPage(webDriver);
		hm = new HeaderMenu(webDriver);
		hm.moveToSetup(extentTest);
		hm.clickClients(extentTest);
		cp.selectPagiation(extentTest, Integer.toString(10));
		ArrayList<WebElement> records = cp.getClientNameFromTable(extentTest);
		
		//Check Success
		if(records.size() == 10) {
			extentTest.log(LogStatus.PASS, "Pagination working on 'Manage Clients' page");
		} else {
			String image = extentTest.addScreenCapture(takeScreenShot(webDriver, timeStamp()+".png"));
			extentTest.log(LogStatus.ERROR, "Pagination not working on 'Manage Clients' page", image);
			extentTest.log(LogStatus.FAIL, "Pagination not working on 'Manage Clients' page" );
		}	
		tearDownExtent();
	}
}
