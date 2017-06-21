package test.java.base.depositor;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import test.java.base.Genre;
import test.java.base.ItemStatus;
import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.ViewItemPage;

/**
 * Test Link UC #4
 * Tests the publication of a conference paper through full submission in simple workflow.
 * @author apetrova
 *
 */
public class ReleaseConferencePaperFullSimpleTest extends BaseTest {
	
	private CombinedHomePage combinedHomePage;
	
	private String title;
	private String filepath;
	private String author;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released conference paper in simple workflow: " + getTimeStamp();
		filepath = getFilepath("SamplePDFFile.pdf");
		author = "Testermeier, Testo (MPI for Social Anthropology)";
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
		Assert.assertEquals(combinedHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void fullSubmissionSimpleWorkflow() {
		FullSubmissionPage fullSubmissionPage = combinedHomePage.goToSubmissionPage().goToFullSubmissionSimplePage();
		ViewItemPage viewItemPage = fullSubmissionPage.fullSubmission(Genre.CONFERENCE_PAPER, title, author, filepath);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void depositorReleasesSubmission() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ViewItemPage viewItemPage = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 4)
	public void viewMostRecentItems() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		String mostRecentItemTitle = combinedHomePage.goToStartPage().getNameOfMostRecentItem();
		Assert.assertEquals(mostRecentItemTitle, title, "Item does not show up at the start page.");
	}
	
	@Test(priority = 5)
	public void viewItem() {
		MyItemsPage myItemsPage = combinedHomePage.goToMyItemsPage();
		ViewItemPage viewItemPage = myItemsPage.openSubmittedItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 6)
	public void discardSubmission() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		MyItemsPage myItemsPage = combinedHomePage.goToMyItemsPage();
		myItemsPage.discardItemByTitle(title);
	}
	
	@AfterClass
	public void tearDown() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
