package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.submission.FetchSubmissionPage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;

/**
 * TestLink Use Case #15
 * Export up to Submit button.
 * @author apetrova
 *
 */
public class ExportItemStandardTest extends BaseLoggedInUserTest {

	private DepositorHomePage depositorHomePage;
	private MyItemsPage myItemsPage;
	
	private String itemTitle = "Exported item";
	private String bibtexValue = "BibTeX";
	private String endNoteValue = "EndNote";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginModDep() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 2)
	public void selectItem() {
		myItemsPage = depositorHomePage.goToMyItemsPage();
		if (!myItemsPage.hasItems())
			uploadItem();
	}
	
	private void uploadItem() {
		String identifier = "arXiv:1304.2685";
		
		depositorHomePage = (DepositorHomePage) myItemsPage.goToHomePage(depositorHomePage);
		FetchSubmissionPage fetchPage = depositorHomePage.goToSubmissionPage().goToFetchSubmissionStandardPage();
		ViewItemPage viewResultsPage = fetchPage.fetchSubmission(identifier);
		depositorHomePage = (DepositorHomePage) viewResultsPage.goToHomePage(depositorHomePage);
		myItemsPage = depositorHomePage.goToMyItemsPage();
	}
	
	@Test(priority = 3)
	public void exportItemBibtex() {
		boolean itemIsExported = myItemsPage.exportItem(itemTitle, bibtexValue);
		Assert.assertTrue(itemIsExported, "Item was not exported.");
	}
	
	@Test(priority = 4)
	public void exportItemEndNote() {
		depositorHomePage = (DepositorHomePage) myItemsPage.goToHomePage(depositorHomePage);
		myItemsPage = ((DepositorHomePage)(depositorHomePage.goToSubmissionPage().goToHomePage(depositorHomePage))).goToMyItemsPage();
		boolean itemIsExported = myItemsPage.exportItem(itemTitle, endNoteValue);
		Assert.assertTrue(itemIsExported, "Item was not exported.");
	}
}
