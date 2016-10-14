package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseTest;

public class ExportSearchRESTTest extends BaseTest {

private RestExamplePage restExamplePage;
	
	private String searchQuery = "protein";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void exportSearchInRest() {
		AdvancedSearchPage advancedSearchPage = new StartPage(driver).goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(searchQuery, "", "");
		saveCurrentHandle();
		restExamplePage = searchResultsPage.insertQueryREST();
	}
	
	@Test(priority = 2)
	public void downloadList() {
		boolean downloadPossible = restExamplePage.exportDownloadPossible();
		Assert.assertTrue(downloadPossible, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void tearDown() {
		backToBaseHandle();
	}
	
}
