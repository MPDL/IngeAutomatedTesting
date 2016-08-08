package base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.StartPage;
import pages.search.AdvancedSearchPage;
import pages.search.SearchResultsPage;

/*
 * TODO improve test coverage
 */
public class AdvancedSearchUnregisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void advancedSearchTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(searchQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
}
