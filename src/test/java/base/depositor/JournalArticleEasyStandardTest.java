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
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.ViewItemPage;

public class JournalArticleEasyStandardTest extends BaseTest {

	private String title;
	private String filepath;
	
	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released journal article in standard workflow: " + getTimeStamp();
		filepath = getFilepath("SamplePDFFile.pdf");
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void easySubmissionStandardWorkflow() {
		EasySubmissionPage easySubmissionPage = depositorHomePage.goToSubmissionPage().goToEasySubmissionStandardPage();
		viewItemPage = easySubmissionPage.easySubmission(Genre.ARTICLE, title, filepath);
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
	public void loginAsModerator() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
		Assert.assertEquals(moderatorHomePage.getUsername(), moderatorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 5)
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openPublishedItemByTitle(title);
		viewItemPage = viewItemPage.acceptItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 6)
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openPublishedItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
	}
	
	@Test(priority = 7)
	public void moderatorLogout() {
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
}
