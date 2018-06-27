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
import main.java.pages.tools.cone.journal.EditJournalPage;
import main.java.pages.tools.cone.journal.NewJournalPage;
import main.java.pages.tools.cone.journal.ViewJournalPage;
import test.java.base.BaseLoggedInUserTest;

public class ConeJournalTest extends BaseLoggedInUserTest {

	private ConeHomePage coneHomePage;
	private ViewJournalPage viewJournalPage;
	
	private String title = "New Journal for testing purposes";
	private String newTitle = "Modified Journal";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginCone() {
		saveCurrentHandle();
		ConeBasePage coneStartPage = new StartPage(driver).goToAdvancedSearchPage().goToToolsPage().goToCoNE();
		coneHomePage = coneStartPage.login(getPropertyValue("coneUsername"), getPropertyValue("conePassword"));
	}
	
	@Test(priority = 2)
	public void createJournal() {
		NewJournalPage newJournalPage = coneHomePage.enterNewJournal();
		viewJournalPage = newJournalPage.createNewJournal(title);
		
		String actual = viewJournalPage.getTitle();
		Assert.assertEquals(title, actual, "Titles do not match.");
	}
	
	@Test(priority = 3)
	public void searchJournal() {
		ConeSearchPage coneSearch = viewJournalPage.goToSearch();
		ViewJournalPage viewJournal = (ViewJournalPage) coneSearch.searchFirstResult(title, EntityType.JOURNAL);
		
		String actual = viewJournal.getTitle();
		Assert.assertEquals(title, actual, "Titles do not match.");
	}
	
	@Test(priority = 4)
	public void editJournal() {
		EditJournalPage editJournalPage = viewJournalPage.editJournal();
		viewJournalPage = editJournalPage.changeTitle(newTitle);
		
		String actual = viewJournalPage.getTitle();
		Assert.assertEquals(newTitle, actual, "Title was not changed.");
	}
	
	@Test(priority = 5)
	public void deleteJournal() {
		NewJournalPage newJournalPage = viewJournalPage.editJournal().deleteJournal();
		ConeSearchPage coneSearch = newJournalPage.goToSearch();
		
		boolean itemWasDeleted;
		try {
			coneSearch.searchFirstResult(newTitle, EntityType.JOURNAL);
			itemWasDeleted = false;
		}
		catch (NoSuchElementException exc) {
			itemWasDeleted = true;
		}
		
		Assert.assertTrue(itemWasDeleted, "Journal was not deleted.");
	}
	
	@AfterClass 
	public void tearDown() {
		backToBaseHandle();
	}
}
