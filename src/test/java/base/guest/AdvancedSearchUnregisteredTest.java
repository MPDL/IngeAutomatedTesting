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

	private String titleQuery = "test";
	private String authorQuery = "Müller";
	private String organisationQuery = "MPI of Cognitive Neuroscience";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void noSearchCriteriaTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		String expectedHeadline = advancedSearchPage.getHeadline();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch("", "", "");
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, expectedHeadline, "Empty search query does not lead to error.");
	}
	
	@Test
	public void advancedSearchTitleTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(titleQuery, "", "");
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
	
	@Test
	public void advancedSearchAuthorTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch("", authorQuery, "");
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
	
	@Test
	public void advancedSearchOrganisationTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch("", "", organisationQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
	
	@Test
	public void advancedSearchCombinedTest() {
		StartPage startPage = new StartPage(driver);
		AdvancedSearchPage advancedSearchPage = startPage.goToAdvancedSearchPage();
		SearchResultsPage searchResultsPage = advancedSearchPage.advancedSearch(titleQuery, authorQuery, organisationQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
}
