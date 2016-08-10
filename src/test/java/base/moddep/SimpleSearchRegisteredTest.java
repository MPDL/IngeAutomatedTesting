package base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.StartPage;
import pages.homepages.CombinedHomePage;
import pages.search.SearchResultsPage;

public class SimpleSearchRegisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInAsCombinedUser() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void noSearchCriteriaTest() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		String expectedHeadline = combinedHomePage.getHeadline();
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch("");
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, expectedHeadline, "Empty search query does not lead to error.");
	}
	
	@Test(priority = 2)
	public void simpleSearchTest() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(searchQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
	
	@Test(priority = 2)
	public void onlyPublishedItems() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(searchQuery);
		boolean allReleased = searchResultsPage.areAllResultsReleased();
		Assert.assertTrue(allReleased, "An item in search results has not been released.");
	}
	
	@AfterClass
	public void logout() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
