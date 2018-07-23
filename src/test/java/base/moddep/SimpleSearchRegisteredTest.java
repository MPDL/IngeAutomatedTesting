package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
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
	
	private DepositorHomePage depositorHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInAsCombinedUser() {
		//TODO: Login as combined user
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void noSearchCriteriaTest() {
		String expectedHeadline = depositorHomePage.getHeadline();
		SearchResultsPage searchResultsPage = depositorHomePage.quickSearch("");
		String headlineText = searchResultsPage.getHeadline();
		searchResultsPage.goToStartPage();
		
		Assert.assertEquals(headlineText, expectedHeadline, "Empty search query does not lead to error.");
	}
	
	@Test(priority = 2)
	public void simpleSearchTest() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		SearchResultsPage searchResultsPage = depositorHomePage.quickSearch(searchQuery);
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
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		SearchResultsPage searchResultsPage = depositorHomePage.quickSearch(searchQuery);
		boolean allReleased = searchResultsPage.allResultsReleased();
		Assert.assertTrue(allReleased, "An item in search results has not been released.");
	}
}
