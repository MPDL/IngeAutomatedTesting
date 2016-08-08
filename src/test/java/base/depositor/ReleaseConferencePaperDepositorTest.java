package base.depositor;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import base.Genre;
import pages.LoginPage;
import pages.StartPage;
import pages.homepages.DepositorHomePage;
import pages.submission.FinaliseSubmissionPage;
import pages.submission.FullSubmissionPage;
import pages.submission.MyItemsPage;
import pages.submission.ViewItemPage;

public class ReleaseConferencePaperDepositorTest extends BaseTest {
	
	private DepositorHomePage depositorHomePage;
	
	private String title = "Released conference paper in simple workflow: " + getTimeStamp();
	private String filepath = "res\\SamplePDFFile.pdf";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void goToFullSubmissionPage() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionPage();
		// TODO data-driven testing implementation
		FinaliseSubmissionPage finaliseSubmissionPage = fullSubmissionPage.fullSubmission(Genre.CONFERENCE_PAPER, title, filepath);
		finaliseSubmissionPage.releaseSubmission();
	}
	
	@Test(priority = 3)
	public void viewItem() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage(driver);
		ViewItemPage viewItemPage = myItemsPage.openItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 4)
	public void discardSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage(driver);
		myItemsPage.discardItemByTitle(title);
	}
	
	@AfterClass
	public void tearDown() {
		StartPage startPage = new StartPage(driver).goToStartPage();
		depositorHomePage = (DepositorHomePage) startPage.goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
