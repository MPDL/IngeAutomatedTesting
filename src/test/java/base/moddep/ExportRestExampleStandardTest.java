package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.tools.ToolsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseTest;

/**
 * TestLink Use Case #7
 * Export is tested up to the Download button.
 * @author apetrova
 *
 */
public class ExportRestExampleStandardTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void logInModDep() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void exportREST() {
		ToolsPage toolsPage = combinedHomePage.goToToolsPage();
		saveCurrentHandle();
		
		RestExamplePage restExamplePage = toolsPage.goToRestInterface();
		restExamplePage.downloadExport();
		
		boolean newFieldsAppear = restExamplePage.newFieldsAppear();
		Assert.assertTrue(newFieldsAppear, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void tearDown() {
		backToBaseHandle();
		
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
