package base.depositor;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import base.Genre;
import pages.LoginPage;
import pages.StartPage;
import pages.homepages.DepositorHomePage;
import pages.homepages.ModeratorHomePage;
import pages.submission.FullSubmissionPage;
import pages.submission.ViewItemPage;
import pages.submission.transition.FinaliseSubmissionPage;

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
		filepath = "file:" + getClass().getResource("/SamplePDFFile.pdf").getPath();
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
		String itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, "Pending", "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		String itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, "Submitted", "Item was not submitted.");
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	
	@Test(priority = 3)
	public void moderatorReleasesSubmission() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openItemByTitle(title);
		viewItemPage = viewItemPage.acceptItem();
		String itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, "Released", "Item was not released.");
	}
	
	@Test(priority = 4)
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
}
