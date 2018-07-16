package test.java.base.depositor;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;

public class EasyArticleDeterministicTest extends BaseLoggedInUserTest {

	private String title;
	private String filepath;
	
	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released journal article in standard workflow: " + getTimeStamp();
		filepath = "SamplePDFFile.pdf";
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void easySubmissionStandardWorkflow() {
		EasySubmissionPage easySubmissionPage = depositorHomePage.goToSubmissionPage().goToEasySubmissionStandardPage();
		viewItemPage = easySubmissionPage.easySubmission("Journal Article", title, filepath);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}	
	
	@Test(priority = 3, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 4, dependsOnMethods = { "easySubmissionStandardWorkflow"})
	public void logoutDepositor() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 5, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void loginAsModerator() {
		moderatorHomePage = new StartPage(driver).loginAsModerator(moderatorUsername, moderatorPassword);
		Assert.assertEquals(moderatorHomePage.getUsername(), moderatorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 6, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		try {
          Thread.sleep(250);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
		viewItemPage = viewItemPage.discardItem();
	}
}
