package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;

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
		String headlineText = searchResultsPage.getHeadline();
		try {
			Assert.assertEquals(headlineText, "Search Results");
		}
		catch (AssertionError exc) {
			Assert.assertEquals(headlineText, "Suchergebnisse");
		}
	}
}
