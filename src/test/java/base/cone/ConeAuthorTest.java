package test.java.base.cone;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.cone.ConeBasePage;
import main.java.pages.tools.cone.ConeHomePage;
import main.java.pages.tools.cone.ConeSearchPage;
import main.java.pages.tools.cone.EntityType;
import main.java.pages.tools.cone.person.EditPersonPage;
import main.java.pages.tools.cone.person.NewPersonPage;
import main.java.pages.tools.cone.person.ViewPersonPage;
import test.java.base.BaseTest;

public class ConeAuthorTest extends BaseTest {

	private String name = "Test Testerfrau";
	private String familyName = name.split(" ")[1];
	private String newName = "Tester Testerfrau";
	
	private ConeHomePage coneHomePage;
	private ViewPersonPage viewPersonPage;
	
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
	public void createAuthor() {
		NewPersonPage newPersonPage = coneHomePage.enterNewPerson();
		viewPersonPage = newPersonPage.createNewAuthor(name, familyName);
		
		String actualName = viewPersonPage.getCompleteName();
		Assert.assertEquals(name, actualName, "Complete names do not match.");
		String actualFamilyName = viewPersonPage.getFamilyName();
		Assert.assertEquals(familyName, actualFamilyName, "Family names do not match.");
	}
	
	@Test(priority = 3)
	public void searchAuthor() {
		ConeSearchPage coneSearchPage = viewPersonPage.goToSearch();
		ViewPersonPage viewPersonPage = (ViewPersonPage) coneSearchPage.searchFirstResult(name, EntityType.PERSON);
		
		String actual = viewPersonPage.getCompleteName();
		Assert.assertEquals(name, actual, "Names do not match.");
	}
	
	@Test(priority = 4)
	public void editAuthor() {
		EditPersonPage editPersonPage = viewPersonPage.editPerson();
		viewPersonPage = editPersonPage.changeName(newName);
		
		String actual = viewPersonPage.getCompleteName();
		Assert.assertEquals(newName, actual, "Names do not match.");
	}
	
	@Test(priority = 5)
	public void deleteAuthor() {
		NewPersonPage newPersonPage = viewPersonPage.editPerson().deletePerson();
		ConeSearchPage coneSearchPage = newPersonPage.goToSearch();
		
		boolean itemWasDeleted;
		try {
			coneSearchPage.searchFirstResult(newName, EntityType.PERSON);
			itemWasDeleted = false;
		}
		catch (NoSuchElementException exc) {
			itemWasDeleted = true;
		}
		
		Assert.assertTrue(itemWasDeleted, "Person was not deleted.");
	}
	
	@AfterClass
	public void tearDown() {
		coneHomePage = new ConeBasePage(driver).goToHomePage(coneHomePage);
		coneHomePage.logout();
	}
}
