package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseTest;

public class ExportCitationStyleTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	
	private String searchQuery = "cellular";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@BeforeMethod
	public void loginModDep() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 1)
	public void exportSearchPDF() {
		SearchResultsPage searchResults = combinedHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean resultsExported = searchResults.allResultsExported("APA", "pdf");
		Assert.assertTrue(resultsExported, "Results were not exported.");
	}
	
	@Test(priority = 2)
	public void exportSearchHTML() {
		SearchResultsPage searchResults = combinedHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean resultsExported = searchResults.allResultsExported("APA", "html_plain");
		Assert.assertTrue(resultsExported, "Results were not exported.");
	}
	
	@Test(priority = 3)
	public void exportSearchDOC() {
		SearchResultsPage searchResults = combinedHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean resultsExported = searchResults.allResultsExported("APA", "docx");
		Assert.assertTrue(resultsExported, "Results were not exported.");
	}
	
	@AfterMethod
	public void logout() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
