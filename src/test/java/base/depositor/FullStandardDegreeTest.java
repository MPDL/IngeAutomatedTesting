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
import test.java.base.TestHelper;

/**
 * Testcase #2 <br>
 * TestLink UC #5 <br>
 * 
 * @author helk
 *
 */
public class FullStandardDegreeTest extends BaseLoggedInUserTest {

	private String title;

	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;

	private TableHelper table = new TableHelper();

	@Override
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
	public void submitDegree() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		viewItemPage = fullSubmissionPage.fullSubmissionDegree(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}

	/**
	 * Not all data is completely checked here.
	 */
	@Test(priority = 3, dependsOnMethods = { "submitDegree" })
	public void checkDataCorrectness() {
		Map<String, String> values = table.getMap();
		title = values.get("[title]");

		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		Assert.assertEquals(viewItemPage.getValue("Genre"), values.get("DEGREE"));
		Assert.assertEquals(viewItemPage.getValue("Name"), values.get("[upload file]").trim());
		Assert.assertEquals(viewItemPage.getValue("Description"), values.get("[description file]").trim());
		Assert.assertEquals(viewItemPage.getValue("Visibility"), values.get("[Visibility]"));
		Assert.assertEquals(viewItemPage.getValue("Copyright Date"), values.get("[Copyright Date]").trim());
		Assert.assertEquals(viewItemPage.getValue("Copyright Info"), values.get("[Copyright statement]").trim());
		Assert.assertEquals(viewItemPage.getValue("License"), values.get("[license URL]").trim());
		Assert.assertEquals(viewItemPage.getValue("Free keywords"), values.get("[free keywords]").trim());
		Assert.assertEquals(viewItemPage.getValue("Abstract"), values.get("[abstract]").trim());
		Assert.assertEquals(viewItemPage.getValue("Pages"), values.get("[total no of pages]").trim());
		Assert.assertEquals(viewItemPage.getValue("Degree"), values.get("[degree type]"));
		Assert.assertEquals(viewItemPage.getValue("Project name"), values.get("[Project name]").trim());
		TestHelper.compareIdentifiers(viewItemPage.getValue("Identifiers"),
				values.get("[identifier create item]").trim(), values.get("[identifier value]").trim());
		Assert.assertEquals(viewItemPage.getValue("Grant ID"), values.get("[Grant ID]").trim());
		Assert.assertEquals(viewItemPage.getValue("Funding program"), values.get("[Funding program]").trim());
		Assert.assertEquals(viewItemPage.getValue("Title"), values.get("[title source]").trim());
		Assert.assertEquals(viewItemPage.getValue("Source Genre"), values.get("[genre source]"));
		Assert.assertEquals(viewItemPage.getValue("Publ. Info"), values.get("[Place source]").trim() + " : "
				+ values.get("[Publisher source]").trim() + ", " + values.get("[Edition]").trim());
		Assert.assertEquals(viewItemPage.getValue("Volume / Issue"), values.get("[Volume source]").trim());
		TestHelper.compareIdentifiers(viewItemPage.getValue("Identifier"),
				values.get("[identifier source create item]").trim(), values.get("[identifier source value]").trim());
	}

	@Test(priority = 4, dependsOnMethods = { "submitDegree" })
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}

	@Test(priority = 5, dependsOnMethods = { "submitDegree" })
	public void logoutDepositor() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}

	@Test(priority = 6, dependsOnMethods = { "submitDegree" })
	public void loginModerator() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}

	@Test(priority = 7, dependsOnMethods = { "submitDegree" })
	public void moderatorSendsBackSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
		viewItemPage = viewItemPage.sendBackForRework();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.IN_REWORK, "Item was not sent for rework.");
	}

	@Test(priority = 8, dependsOnMethods = { "submitDegree" })
	public void logoutModerator() {
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}

	@Test(priority = 9, dependsOnMethods = { "submitDegree" })
	public void loginDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
	}

	@Test(priority = 10, dependsOnMethods = { "submitDegree" })
	public void depositorRevisesItem() {
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}

	@Test(priority = 11, dependsOnMethods = { "submitDegree" })
	public void logoutDepositor2() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}

	@Test(priority = 12, dependsOnMethods = { "submitDegree" })
	public void loginModerator2() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}

	@Test(priority = 13, dependsOnMethods = { "submitDegree" })
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}

	@Test(priority = 14, dependsOnMethods = { "submitDegree" })
	public void moderatorEditsRelease() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
	}

	@Test(priority = 15, dependsOnMethods = { "submitDegree" })
	public void moderatorReleasesSubmissionAgain() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}

	@Test(priority = 16, dependsOnMethods = { "submitDegree" })
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
