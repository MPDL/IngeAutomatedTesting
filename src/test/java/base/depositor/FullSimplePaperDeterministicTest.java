package test.java.base.depositor;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;

/**
 * Test Link UC #4
 * Tests the publication of a conference paper through full submission in simple workflow.
 * @author apetrova
 *
 */
public class FullSimplePaperDeterministicTest extends BaseLoggedInUserTest {
	
	private DepositorHomePage depositorHomePage;
	
	private String title;
	private String[] filepath;
	private String author;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released conference paper in simple workflow: " + getTimeStamp();
		filepath = new String[1];
		filepath[0] = getFilepath("SamplePDFFile.pdf");
		author = "Testermeier, Testo (MPI for Social Anthropology)";
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void fullSubmissionSimpleWorkflow() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().depositorGoToFullSubmissionPage();
		ViewItemPage viewItemPage = fullSubmissionPage.fullSubmission("Conference Paper", title, author, filepath);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void depositorReleasesSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		ViewItemPage viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 4)
	public void viewMostRecentItems() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		String mostRecentItemTitle = depositorHomePage.goToStartPage().getNameOfMostRecentItem();
		Assert.assertEquals(mostRecentItemTitle, title, "Item does not show up at the start page.");
	}
	
	@Test(priority = 5)
	public void viewItem() {
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		ViewItemPage viewItemPage = myItemsPage.openSubmittedItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 6)
	public void discardSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		myItemsPage.discardItemByTitle(title);
	}
}
