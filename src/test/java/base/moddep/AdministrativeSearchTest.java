package test.java.base.moddep;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.search.AdministrativeSearchPage;
import main.java.pages.search.SearchResultsPage;

public class AdministrativeSearchTest extends BaseTest {

	private String title = "test";
	private DepositorHomePage depositorHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority=1)
	public void logInAsCombinedUser() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority=2)
	public void administrativeSearchTest() {
		AdministrativeSearchPage administrativeSearchPage = depositorHomePage.goToAdministrativeSearchPage();
		SearchResultsPage searchResultsPage = administrativeSearchPage.advancedSearch(title, "", "");
		boolean allResultsReleased = searchResultsPage.allResultsReleased();
		Assert.assertFalse(allResultsReleased);
	}
	
	@AfterClass
	public void tearDown() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
