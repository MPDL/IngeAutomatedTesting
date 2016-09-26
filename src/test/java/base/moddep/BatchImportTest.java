package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.submission.ImportWorkspacePage;
import main.java.pages.submission.MultipleImportOptionsPage;
import main.java.pages.submission.MultipleImportPage;
import test.java.base.BaseTest;

public class BatchImportTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	
	private String filepath;
	private String title = "Import BibTeX: " + getTimeStamp();
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginSetFilepath() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
		
		filepath = getFilepath("bibtexExport.txt");
	}
	
	@Test(priority = 2)
	public void batchImport() {
		MultipleImportPage multipleImportPage = combinedHomePage.goToSubmissionPage().goToMultipleImportStandardPage();
		MultipleImportOptionsPage multipleImportOptionsPage = multipleImportPage.batchImportItems(filepath, "BibTeX");
		ImportWorkspacePage importWorkspace = multipleImportOptionsPage.setUploadOptions(title);
		importWorkspace.waitForUploadToFinish();
		
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		importWorkspace = combinedHomePage.goToImportWorkspace();
		boolean importIsPresent = importWorkspace.isImportPresent(title);
		Assert.assertTrue(importIsPresent, "File was not imported.");
	}
	
	@Test(priority = 3)
	public void releaseImport() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ImportWorkspacePage importWorkspace = combinedHomePage.goToImportWorkspace();
		importWorkspace = importWorkspace.releaseImport(title);
	}
	
	@Test(priority = 4)
	public void deleteImport() {
		// delete import
		
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ImportWorkspacePage importWorkspace = combinedHomePage.goToImportWorkspace();
		boolean importIsPresent = importWorkspace.isImportPresent(title);
		Assert.assertTrue(importIsPresent, "File was not deleted.");
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
