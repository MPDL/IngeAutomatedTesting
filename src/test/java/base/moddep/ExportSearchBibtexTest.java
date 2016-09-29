package test.java.base.moddep;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseTest;

public class ExportSearchBibtexTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	
	private String searchQuery = "lipid";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void exportSimpleSearch() {
		SearchResultsPage searchResults = combinedHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		searchResults.exportResults("BIBTEX", null);
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
