package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.tools.RestExamplePage;
import test.java.base.BaseTest;

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
		// TODO better coverage
		RestExamplePage restExamplePage = combinedHomePage.goToToolsPage().goToRestInterface();
		boolean itemsWereExported = restExamplePage.exportableItemAvailable();
		Assert.assertTrue(itemsWereExported, "No items were exported.");
	}
	
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
