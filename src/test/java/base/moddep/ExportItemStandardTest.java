package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.submission.FetchSubmissionPage;
import main.java.pages.submission.MyItemsPage;
import test.java.base.BaseTest;

public class ExportItemStandardTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private MyItemsPage myItemsPage;
	
	private String itemTitle = "Exported item";
	private String bibtexValue = "BIBTEX";
	private String endNoteValue = "ENDNOTE";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void selectItem() {
		myItemsPage = combinedHomePage.goToMyItemsPage();
		if (!myItemsPage.hasItems())
			uploadItem();
	}
	
	private void uploadItem() {
		String identifier = "arXiv:1304.2685";
		
		combinedHomePage = (CombinedHomePage) myItemsPage.goToHomePage(combinedHomePage);
		FetchSubmissionPage fetchPage = combinedHomePage.goToSubmissionPage().goToFetchSubmissionStandardPage();
		fetchPage.fetchSubmission(identifier);
	}
	
	@Test(priority = 3)
	public void exportItemBibtex() {
		combinedHomePage = (CombinedHomePage) myItemsPage.goToHomePage(combinedHomePage);
		myItemsPage = combinedHomePage.goToMyItemsPage();
		boolean itemIsExported = myItemsPage.exportItem(itemTitle, bibtexValue);
		Assert.assertTrue(itemIsExported, "Item was not exported.");
	}
	
	@Test(priority = 4)
	public void exportItemEndNote() {
		combinedHomePage = (CombinedHomePage) myItemsPage.goToHomePage(combinedHomePage);
		myItemsPage = combinedHomePage.goToMyItemsPage();
		boolean itemIsExported = myItemsPage.exportItem(itemTitle, endNoteValue);
		Assert.assertTrue(itemIsExported, "Item was not exported.");
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) myItemsPage.goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
