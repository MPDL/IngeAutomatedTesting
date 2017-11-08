package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.submission.ImportWorkspacePage;
import main.java.pages.submission.MultipleImportOptionsPage;
import main.java.pages.submission.MultipleImportPage;
import test.java.base.BaseTest;

public class BatchImportTest extends BaseTest {

	private DepositorHomePage depositorHomePage;
//	private CombinedHomePage combinedHomePage;
	
	private String filepath;
	private String title = "Import BibTeX: " + getTimeStamp();
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginSetFilepath() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
		
		filepath = getFilepath("bibtexExport.txt");
	}
	
	@Test(priority = 2)
	public void batchImport() {
		MultipleImportPage multipleImportPage = depositorHomePage.goToSubmissionPage().goToMultipleImportStandardPage();
		MultipleImportOptionsPage multipleImportOptionsPage = multipleImportPage.batchImportItems(filepath, "BibTeX");
		ImportWorkspacePage importWorkspace = multipleImportOptionsPage.setUploadOptions(title);
		importWorkspace.waitForActionToFinish(title);
		
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		importWorkspace = depositorHomePage.goToImportWorkspace();
		boolean importIsPresent = importWorkspace.isImportPresent(title);
		Assert.assertTrue(importIsPresent, "File was not imported.");
	}
	
	@Test(priority = 3)
	public void releaseImport() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		ImportWorkspacePage importWorkspace = depositorHomePage.goToImportWorkspace();
		importWorkspace = importWorkspace.submitImport(title);
	}
	
	@Test(priority = 4)
	public void deleteImport() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		ImportWorkspacePage importWorkspace = depositorHomePage.goToImportWorkspace();
		importWorkspace.deleteImport(title);
		
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		importWorkspace = depositorHomePage.goToImportWorkspace();
		boolean importIsPresent = importWorkspace.isImportPresent(title);
		Assert.assertTrue(importIsPresent, "File was not deleted.");
	}
	
	@AfterClass
	public void tearDown() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
