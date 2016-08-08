package base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.StartPage;
import pages.search.SearchResultsPage;

public class SimpleSearchUnregisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void simpleSearchTest() {
		StartPage startPage = new StartPage(driver);
		SearchResultsPage searchResultsPage = startPage.quickSearch(searchQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
}
