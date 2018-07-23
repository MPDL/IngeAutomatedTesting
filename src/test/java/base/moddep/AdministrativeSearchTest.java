package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.AdministrativeSearchPage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseLoggedInUserTest;

/**
 * Testcase #6
 * TestLink UC #8
 * 
 * @author helk
 *
 */
public class AdministrativeSearchTest extends BaseLoggedInUserTest {

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
}
