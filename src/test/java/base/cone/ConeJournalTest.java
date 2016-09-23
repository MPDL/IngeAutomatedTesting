package test.java.base.cone;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;
import main.java.pages.StartPage;
import main.java.pages.cone.ConeBasePage;
import main.java.pages.cone.ConeHomePage;
import main.java.pages.cone.journal.EditJournalPage;
import main.java.pages.cone.journal.NewJournalPage;
import main.java.pages.cone.journal.ViewJournalPage;
import test.java.base.BaseTest;

public class ConeJournalTest extends BaseTest {

	private ConeHomePage coneHomePage;
	private ViewJournalPage viewJournalPage;
	
	private String title = "New Journal";
	private String newTitle = "Modified Journal";
	
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
	public void createJournal() {
		NewJournalPage newJournalPage = coneHomePage.enterNewJournal();
		viewJournalPage = newJournalPage.createNewJournal(title);

		String actualHeadline = viewJournalPage.getHeadline();
		Assert.assertEquals("View journals", actualHeadline, "Page headlines do not match.");
		
		String actual = viewJournalPage.getTitle();
		Assert.assertEquals(title, actual, "Titles do not match.");
	}
	
	@Test(priority = 3)
	public void editJournal() {
		EditJournalPage editJournalPage = viewJournalPage.editJournal();
		viewJournalPage = editJournalPage.changeTitle(newTitle);
		
		String actual = viewJournalPage.getTitle();
		Assert.assertEquals(newTitle, actual, "Title was not changed.");
	}
	
	@Test(priority = 4)
	public void deleteJournal() {
		viewJournalPage.editJournal().deleteJournal();
	}
	
	@AfterClass 
	public void tearDown() {
		coneHomePage = new ConeBasePage(driver).goToHomePage(coneHomePage);
		coneHomePage.logout();
	}
}
