package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.tools.ToolsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseLoggedInUserTest;

/**
 * TestLink Use Case #7
 * Export is tested up to the Download button.
 * @author apetrova
 *
 */
public class ExportRestExampleStandardTest extends BaseLoggedInUserTest {

	private DepositorHomePage depositorHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInModDep() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void exportREST() {
		ToolsPage toolsPage = depositorHomePage.goToToolsPage();
		saveCurrentHandle();
		
		RestExamplePage restExamplePage = toolsPage.goToRestInterface();
		boolean downloadPossible = restExamplePage.exportDownloadPossible();
		Assert.assertTrue(downloadPossible, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void tearDown() {
		backToBaseHandle();
	}
}
