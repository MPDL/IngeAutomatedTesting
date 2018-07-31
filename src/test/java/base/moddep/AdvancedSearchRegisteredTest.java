package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseLoggedInUserTest;

/**
 * Testcase #10
 * TestLink UC #12 
 *
 * Small Advanced Search Test. <br>
 *
 * Assumes at least one item matching each search query is present
 */
public class AdvancedSearchRegisteredTest extends BaseLoggedInUserTest {
	
	private String titleQuery = "Submission";
	private String authorQuery = "Test";
	private String organisationQuery = "MPDL";
	
	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	//TODO: Before tests: Create matching (Submission) items to search for, if not already existing.
	
	@Test(priority=1)
	public void logInAsCombinedUser() {
		combinedHomePage = new StartPage(driver).loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority=2)
	public void advancedSearchTitleTest() {
		searchTest(titleQuery, "", "");
	}
	
	@Test(priority=2)
	public void advancedSearchAuthorTest() {
		searchTest("", authorQuery, "");
	}
	
	@Test(priority=2)
	public void advancedSearchOrganisationTest() {
		searchTest("", "", organisationQuery);
	}
	
	@Test(priority=2)
	public void advancedSearchCombinedTest() {
		searchTest(titleQuery, authorQuery, organisationQuery);
	}
	
	private void searchTest(String titleQuery, String authorQuery, String organisationQuery) {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		AdvancedSearchPage advancedSearchPage = combinedHomePage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(titleQuery, authorQuery, organisationQuery);
		testSearchHeadline(searchResultsPage);
		testResultsPresence(searchResultsPage);
		testReleasedResults(searchResultsPage);
	}
	
	private void testSearchHeadline(SearchResultsPage searchResultsPage) {
		String headlineText = searchResultsPage.getHeadline();
		try {
			Assert.assertEquals(headlineText, "Search Results");
		}
		catch (AssertionError exc) {
			Assert.assertEquals(headlineText, "Suchergebnisse", "Search results page was not displayed.");
		}
	}
	
	private void testResultsPresence(SearchResultsPage searchResultsPage) {
		Assert.assertNotEquals(0, searchResultsPage.getResultCount(), "Search should return at least one result: title - '" + titleQuery
				+ "', person - '" + authorQuery + "', org - '" + organisationQuery + "'.");
	}
	
	private void testReleasedResults(SearchResultsPage searchResultsPage) {
		Assert.assertTrue(searchResultsPage.allResultsReleased(), "An unreleased item is present in advanced search results.");
	}
}
