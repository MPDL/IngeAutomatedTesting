package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseLoggedInUserTest;

/**
 * Testcase #8
 * TestLink UC #10
 * 
 * @author helk
 *
 */
public class SimpleSearchRegisteredTest extends BaseLoggedInUserTest {

	private String searchQuery = "test";
	
	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInAsCombinedUser() {
		combinedHomePage = new StartPage(driver).loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void noSearchCriteriaTest() {
		String expectedHeadline = combinedHomePage.getHeadline();
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch("");
		String headlineText = searchResultsPage.getHeadline();
		searchResultsPage.goToStartPage();
		
		Assert.assertEquals(headlineText, expectedHeadline, "Empty search query does not lead to error.");
	}
	
	@Test(priority = 2)
	public void simpleSearchTest() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(searchQuery);
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
	
	@Test(priority = 2)
	public void onlyPublishedItems() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(searchQuery);
		boolean allReleased = searchResultsPage.allResultsReleased();
		Assert.assertTrue(allReleased, "An item in search results has not been released.");
	}
}
