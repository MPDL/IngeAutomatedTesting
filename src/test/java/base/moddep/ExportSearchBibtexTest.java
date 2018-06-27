package test.java.base.moddep;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseLoggedInUserTest;

public class ExportSearchBibtexTest extends BaseLoggedInUserTest {

	private DepositorHomePage depositorHomePage;
	
	private String searchQuery = "lipid";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void exportSimpleSearch() {
		SearchResultsPage searchResults = depositorHomePage.quickSearch(searchQuery);
		searchResults = searchResults.goToExport();
		searchResults.resultExportPossible("BIBTEX", null);
	}
}
