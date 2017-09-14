package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseTest;

public class ExportSearchRESTTest extends BaseTest {

	private DepositorHomePage depositorHomePage;
	private RestExamplePage restExamplePage;
	
	private String searchQuery = "carbohydrate";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void exportSearchInRest() {
		AdvancedSearchPage advancedSearchPage = depositorHomePage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(searchQuery, "", "");
		saveCurrentHandle();
		restExamplePage = searchResultsPage.insertQueryREST();
	}
	
	@Test(priority = 3)
	public void downloadList() {
		boolean downloadPossible = restExamplePage.exportDownloadPossible();
		Assert.assertTrue(downloadPossible, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void logout() {
		backToBaseHandle();
		
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
