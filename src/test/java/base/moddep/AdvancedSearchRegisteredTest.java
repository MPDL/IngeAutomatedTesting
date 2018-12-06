package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;

/**
 * Testcase #10
 * TestLink UC #12 
 *
 * Small Advanced Search Test. <br>
 *
 * @author helk
 *
 */
public class AdvancedSearchRegisteredTest extends BaseLoggedInUserTest {
	
	private String titleQuery = "Test Submission to Search for";
	private String authorQuery = "Test author name";
	private String organisationQuery = "Test organisation";
	
	private CombinedHomePage combinedHomePage;
	private ViewItemPage viewItemPage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInAsCombinedUser() {
		combinedHomePage = new StartPage(driver).loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void fullSubmissionMinimal() {
		this.createItem();
		this.submitItem();
		this.releasesItem();
	}
	
	private void createItem() {
		FullSubmissionPage fullSubmissionPage = combinedHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		viewItemPage = fullSubmissionPage.fullSubmissionMinimal(titleQuery, authorQuery, organisationQuery);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	private void submitItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	private void releasesItem() {
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 3)
	public void advancedSearchTitleTest() {
		searchTest(titleQuery, "", "");
	}
	
	@Test(priority = 4)
	public void advancedSearchAuthorTest() {
		searchTest("", authorQuery, "");
	}
	
	@Test(priority = 5)
	public void advancedSearchOrganisationTest() {
		searchTest("", "", organisationQuery);
	}
	
	@Test(priority = 6)
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
	
	@Test(priority = 7)
	public void discardItem() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		viewItemPage = combinedHomePage.openReleasedItemByTitle(titleQuery);
		viewItemPage = viewItemPage.discardItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.DISCARDED, "Item was not discarded.");
	}
	
}
