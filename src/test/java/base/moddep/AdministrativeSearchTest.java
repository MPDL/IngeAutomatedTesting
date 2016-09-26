package test.java.base.moddep;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import main.java.pages.LoginPage;
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
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority=2)
	public void administrativeSearchTest() {
		AdministrativeSearchPage administrativeSearchPage = combinedHomePage.goToAdministrativeSearchPage();
		SearchResultsPage searchResultsPage = administrativeSearchPage.advancedSearch(title, "", "");
		boolean allResultsReleased = searchResultsPage.allResultsReleased();
		Assert.assertFalse(allResultsReleased);
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
