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
import pages.submission.FullSubmissionPage;
import pages.submission.MyItemsPage;
import pages.submission.ViewItemPage;
import pages.submission.transition.FinaliseSubmissionPage;

public class ReleaseConferencePaperDepositorTest extends BaseTest {
	
	private DepositorHomePage depositorHomePage;
	
	private String title;
	private String filepath;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released conference paper in simple workflow: " + getTimeStamp();
		filepath = "file:" + getClass().getResource("/SamplePDFFile.pdf").getPath();
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void fullSubmissionSimpleWorkflow() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionSimplePage();
		// TODO data-driven testing implementation
		ViewItemPage viewItemPage = fullSubmissionPage.fullSubmission(Genre.CONFERENCE_PAPER, title, filepath);
		String itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, "Pending", "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void depositorReleasesSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		ViewItemPage viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		String itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, "Released", "Item was not released.");
	}
	
	@Test(priority = 4)
	public void viewMostRecentItems() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		StartPage startPage = depositorHomePage.goToStartPage();
		String mostRecentItemTitle = startPage.getNameOfMostRecentItem();
		Assert.assertEquals(mostRecentItemTitle, title, "Item does not show up at the start page.");
	}
	
	@Test(priority = 5)
	public void viewItem() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		ViewItemPage viewItemPage = myItemsPage.openItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 6)
	public void discardSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		myItemsPage.discardItemByTitle(title);
	}
	
	@AfterClass
	public void tearDown() {
		StartPage startPage = new StartPage(driver).goToStartPage();
		depositorHomePage = (DepositorHomePage) startPage.goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
}
