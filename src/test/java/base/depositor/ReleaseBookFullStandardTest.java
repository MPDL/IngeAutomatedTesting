package test.java.base.depositor;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test.java.base.BaseTest;
import test.java.base.Genre;
import test.java.base.ItemStatus;
import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;

/**
 * TestLink UC #5
 * Tests full submission of a book in standard workflow.
 * @author apetrova
 *
 */
public class ReleaseBookFullStandardTest extends BaseTest {
	
	private String title;
	private String filepath;
	
	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released book in standard workflow: " + getTimeStamp();
		filepath = getFilepath("SamplePDFFile.pdf");
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void fullSubmissionStandardWorkflow() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		// TODO data-driven testing implementation
		viewItemPage = fullSubmissionPage.fullSubmission(Genre.BOOK, title, filepath);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 4)
	public void moderatorSendsBackSubmission() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
		viewItemPage = viewItemPage.sendBackForRework();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.IN_REWORK, "Item was not sent for rework.");
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
	
	@Test(priority = 5)
	public void depositorRevisesItem() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	
	@Test(priority = 6)
	public void moderatorReleasesSubmission() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openItemByTitle(title);
		viewItemPage = viewItemPage.acceptItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 7)
	public void moderatorModifiesRelease() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openItemByTitle(title);
		viewItemPage = viewItemPage.modifyItem();
	}
	
	@Test(priority = 8)
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.DISCARDED, "Item was not discarded.");
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
	
}
