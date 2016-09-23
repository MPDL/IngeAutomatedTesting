package test.java.base.cone;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.cone.ConeBasePage;
import main.java.pages.cone.ConeHomePage;
import main.java.pages.cone.person.NewPersonPage;
import main.java.pages.cone.person.ViewPersonPage;
import test.java.base.BaseTest;

public class ConeAuthorTest extends BaseTest {

	private ConeHomePage coneHomePage;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginCone() {
		ConeBasePage coneStartPage = new StartPage(driver).goToToolsPage().goToCoNE();
		coneHomePage = coneStartPage.login(getPropertyValue("coneUsername"), getPropertyValue("conePassword"));
	}
	
	@Test(priority = 2)
	public void createNewAuthor() {
		NewPersonPage newPersonPage = coneHomePage.enterNewPerson();
		ViewPersonPage viewPersonPage = newPersonPage.createNewAuthor("Test Testerfrau", "Testerfrau");
	}
	
	@Test(priority = 3)
	public void editAuthor() {
		// search for author
		// edit information
	}
	
	@Test(priority = 4)
	public void deleteAuthor() {
		// search for author
		// delete author
	}
	
	@AfterClass
	public void tearDown() {
		coneHomePage = new ConeBasePage(driver).goToHomePage(coneHomePage);
		coneHomePage.logout();
	}
}
