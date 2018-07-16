package test.java.base.depositor;

import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

public class FullStandardSourceIndependentTest extends BaseLoggedInUserTest {

	private String title;
	
	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;
	
	private TableHelper table = new TableHelper();
	Map<String, String> values;
	
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
	public void submitSourceIndependent() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		viewItemPage = fullSubmissionPage.fullSubmissionSrcIndep(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3, dependsOnMethods = { "submitSourceIndependent" })
	public void checkDataCorrectness() {
		values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		compare("Genre", "SOURCE_INDEP");
		compare("Name", "[upload file]");
		compare("Description", "[description file]");
		compare("Visibility", "[Visibility]");
		compare("Copyright Date", "[Copyright Date]");
		compare("Copyright Info", "[Copyright statement]");
		compare("License", "[license URL]");
		compare("Free keywords", "[free keywords]");
		compare("Abstract", "[abstract]");
	}
	
	private void compare(String label, String expected) {
		Assert.assertEquals(viewItemPage.getValue(label), values.get(expected).trim());
	}
	
	@Test(priority = 4, dependsOnMethods = { "submitSourceIndependent" })
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "submitSourceIndependent" })
	public void logoutDepositor() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 6, dependsOnMethods = { "submitSourceIndependent" })
	public void loginModerator() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}
	
	@Test(priority = 7, dependsOnMethods = { "submitSourceIndependent" })
	public void moderatorSendsBackSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
		viewItemPage = viewItemPage.sendBackForRework();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.IN_REWORK, "Item was not sent for rework.");
	}
	
	@Test(priority = 8, dependsOnMethods = { "submitSourceIndependent" })
	public void logoutModerator() {
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
	
	@Test(priority = 9, dependsOnMethods = { "submitSourceIndependent" })
	public void loginDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 10, dependsOnMethods = { "submitSourceIndependent" })
	public void depositorRevisesItem() {
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 11, dependsOnMethods = { "submitSourceIndependent" })
	public void logoutDepositor2() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 12, dependsOnMethods = { "submitSourceIndependent" })
	public void loginModerator2() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}
	
	@Test(priority = 13, dependsOnMethods = { "submitSourceIndependent" })
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 14, dependsOnMethods = { "submitSourceIndependent" })
	public void moderatorEditsRelease() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
	}
	
	@Test(priority = 15, dependsOnMethods = { "submitSourceIndependent" })
	public void moderatorReleasesSubmissionAgain() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 16, dependsOnMethods = { "submitSourceIndependent" })
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.DISCARDED, "Item was not discarded.");
	}
	
	@AfterClass
	public void saveDataAfterFailure(ITestContext context) {
		if (context.getFailedTests().size() > 0) {
			String testOutputPath = "./target/" + this.getClass().getSimpleName() + ".txt";
			table.writeContentsToFile(testOutputPath);
		}
	}
}
