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
 * Search for non-published items. <br>
 * 
 * Assumes at least one item matching each search query is present
 * 
 * @author helk
 *
 */
public class AdministrativeSearchTest extends BaseLoggedInUserTest {

	private String titleQuery = "Test";
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
	public void administrativeSearchTest() {
		AdministrativeSearchPage administrativeSearchPage = combinedHomePage.goToAdministrativeSearchPage();
		SearchResultsPage searchResultsPage = administrativeSearchPage.advancedSearch(titleQuery, "", "");
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
