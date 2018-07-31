package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseTest;

/**
 * Testcase #7
 * TestLink UC #9
 * 
 * Small Quick Search Test without being logged in. <br>
 * 
 * Assumes at least one item matching each search query is present
 * 
 * @author helk
 *
 */
public class SimpleSearchUnregisteredTest extends BaseTest {

	private String searchQuery = "Test";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	//TODO: Before tests: Create matching (Submission) items to search for, if not already existing.
	
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
		Assert.assertNotEquals(searchResultsPage.getResultCount(), 0,
				"At least one result should be present for term '" + searchQuery + "'.");
	}
}
