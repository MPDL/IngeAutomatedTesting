package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdministrativeSearchPage;
import main.java.pages.search.SearchResultsPage;

public class AdministrativeSearchTest extends BaseTest {

	private String title = "test";
	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority=1)
	public void logInAsCombinedUser() {
		combinedHomePage = new StartPage(driver).loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority=2)
	public void administrativeSearchTest() {
		AdministrativeSearchPage administrativeSearchPage = combinedHomePage.goToAdministrativeSearchPage();
		SearchResultsPage searchResultsPage = administrativeSearchPage.advancedSearch(title, "", "");
		String headlineText = searchResultsPage.getHeadline();
		try {
			Assert.assertEquals(headlineText, "Search Results");
		}
		catch (AssertionError exc) {
			Assert.assertEquals(headlineText, "Suchergebnisse", "Search results page was not displayed.");
		}
		boolean allResultsReleased = searchResultsPage.allResultsReleased();
		Assert.assertFalse(allResultsReleased, "All results are released.");
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
