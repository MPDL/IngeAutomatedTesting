package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseLoggedInUserTest;

public class ExportCitationStyleTest extends BaseLoggedInUserTest {

	private DepositorHomePage depositorHomePage;
	
	private String searchQuery = "cellular";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@BeforeMethod
	public void loginDepositor() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 1)
	public void exportSearchPDF() {
		SearchResultsPage searchResults = depositorHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean exportPossible = searchResults.resultExportPossible("APA", "pdf");
		Assert.assertTrue(exportPossible, "Export is not possible: download button is disabled.");
	}
	
	@Test(priority = 2)
	public void exportSearchHTML() {
		SearchResultsPage searchResults = depositorHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean exportPossible = searchResults.resultExportPossible("APA", "html_plain");
		Assert.assertTrue(exportPossible, "Export is not possible: download button is disabled.");
	}
	
	@Test(priority = 3)
	public void exportSearchDOC() {
		SearchResultsPage searchResults = depositorHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		boolean exportPossible = searchResults.resultExportPossible("APA", "docx");
		Assert.assertTrue(exportPossible, "Export is not possible: download button is disabled.");
	}
}
