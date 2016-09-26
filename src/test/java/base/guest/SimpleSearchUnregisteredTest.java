package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.search.SearchResultsPage;

public class SimpleSearchUnregisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void noSearchCriteriaTest() {
		StartPage startPage = new StartPage(driver);
		String expectedHeadline = startPage.getHeadline();
		SearchResultsPage searchResultsPage = startPage.quickSearch("");
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, expectedHeadline, "Empty search query does not lead to error.");
	}
	
	@Test
	public void simpleSearchTest() {
		StartPage startPage = new StartPage(driver);
		SearchResultsPage searchResultsPage = startPage.quickSearch(searchQuery);
		String headlineText = searchResultsPage.getHeadline();
		try {
			Assert.assertEquals(headlineText, "Search Results");
		}
		catch (AssertionError exc) {
			Assert.assertEquals(headlineText, "Suchergebnisse", "Search results page was not displayed.");
		}
	}
	
	@Test
	public void onlyPublishedItems() {
		StartPage startPage = new StartPage(driver);
		SearchResultsPage searchResultsPage = startPage.quickSearch(searchQuery);
		boolean allReleased = searchResultsPage.allResultsReleased();
		Assert.assertTrue(allReleased, "An item in search results has not been released.");
	}
}
