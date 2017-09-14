package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.search.SearchResultsPage;

public class SimpleSearchRegisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	private DepositorHomePage depositorHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInAsCombinedUser() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void noSearchCriteriaTest() {
		String expectedHeadline = depositorHomePage.getHeadline();
		SearchResultsPage searchResultsPage = depositorHomePage.quickSearch("");
		String headlineText = searchResultsPage.getHeadline();
		// workaround when start button is not enabled; TODO: find permantent solution
		searchResultsPage.goToAdvancedSearchPage();
		//
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
	}
	
	@Test(priority = 2)
	public void onlyPublishedItems() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		SearchResultsPage searchResultsPage = depositorHomePage.quickSearch(searchQuery);
		boolean allReleased = searchResultsPage.allResultsReleased();
		Assert.assertTrue(allReleased, "An item in search results has not been released.");
	}
	
	@AfterClass
	public void logout() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
