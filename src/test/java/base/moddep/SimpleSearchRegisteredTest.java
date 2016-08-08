package base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.StartPage;
import pages.homepages.CombinedHomePage;
import pages.homepages.DepositorHomePage;
import pages.search.SearchResultsPage;

public class SimpleSearchRegisteredTest extends BaseTest {

	private String searchQuery = "test";
	
	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority=1)
	public void logInAsCombinedUser() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority=2)
	public void simpleSearchTest() {
		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(searchQuery);
		String headlineText = searchResultsPage.getHeadline();
		Assert.assertEquals(headlineText, "Search Results", "Search results page was not displayed.");
	}
	
	@AfterClass
	public void logout() {
		StartPage startPage = new StartPage(driver).goToStartPage();
		combinedHomePage = (CombinedHomePage) startPage.goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
