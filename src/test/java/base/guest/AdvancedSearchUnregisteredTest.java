package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;

/*
 * Assumes at least one item matching each search query is present
 */
public class AdvancedSearchUnregisteredTest extends BaseTest {

	private String titleQuery = "Submission";
	private String authorQuery = "Testermann";
	private String organisationQuery = "MPDL";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void noSearchCriteriaTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch("", "", "");
		boolean errorDisplayed = searchResultsPage.errorMessageDisplayed();
		Assert.assertTrue(errorDisplayed, "Error message is not displayed when searching without criteria.");
	}
	
	@Test
	public void advancedSearchTitleTest() {
		searchTest(titleQuery, "", "");
	}
	
	@Test
	public void advancedSearchAuthorTest() {
		searchTest("", authorQuery, "");
	}
	
	@Test
	public void advancedSearchOrganisationTest() {
		searchTest("", "", organisationQuery);
	}
	
	@Test
	public void advancedSearchCombinedTest() {
		searchTest(titleQuery, authorQuery, organisationQuery);
	}
	
	private void searchTest(String titleQuery, String authorQuery, String organisationQuery) {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(titleQuery, authorQuery, organisationQuery);
		testSearchHeadline(searchResultsPage);
		testResultsPresence(searchResultsPage);
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
}
