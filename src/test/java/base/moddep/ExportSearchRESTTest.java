package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseTest;

public class ExportSearchRESTTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private RestExamplePage restExamplePage;
	
	private String searchQuery = "carbohydrate";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void exportSearchInRest() {
		AdvancedSearchPage advancedSearchPage = combinedHomePage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(searchQuery, "", "");
		saveCurrentHandle();
		restExamplePage = searchResultsPage.insertQueryREST();
	}
	
	@Test(priority = 3)
	public void downloadList() {
		restExamplePage.downloadExport();
		
		boolean newFieldsAppear = restExamplePage.newFieldsAppear();
		Assert.assertTrue(newFieldsAppear, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void logout() {
		backToBaseHandle();
		
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
