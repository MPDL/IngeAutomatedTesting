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

public class FullStandardEventTest extends BaseLoggedInUserTest {

	private String title;
	
	private DepositorHomePage depositorHomePage;
	private ModeratorHomePage moderatorHomePage;
	private ViewItemPage viewItemPage;
	
	private TableHelper table = new TableHelper();
	
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
	public void submitEvent() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().depositorGoToFullSubmissionPage();
		viewItemPage = fullSubmissionPage.fullSubmissionEvent(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3, dependsOnMethods = { "submitEvent" })
	public void checkDataCorrectness() {
		Map<String, String> values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		Assert.assertEquals(viewItemPage.getLabel("Genre"), values.get("EVENT"));
		Assert.assertEquals(viewItemPage.getLabel("Name"), values.get("[upload file]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Description"), values.get("[description file]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Visibility"), values.get("[Visibility]"));
		Assert.assertEquals(viewItemPage.getLabel("Copyright Date"), values.get("[Copyright Date]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Copyright Info"), values.get("[Copyright statement]").trim());
		Assert.assertEquals(viewItemPage.getLabel("License"), values.get("[license URL]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Free keywords"), values.get("[free keywords]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Abstract"), values.get("[abstract]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Pages"), values.get("[Total no of pages source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Identifiers"), values.get("[identifier create item]").trim() + ": " + 
																	values.get("[identifier value]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Project name"), values.get("[Project name]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Grant ID"), values.get("[Grant ID]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Funding program"), values.get("[Funding program]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Title"), values.get("[title source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Source Genre"), values.get("[genre source]"));
		Assert.assertEquals(viewItemPage.getLabel("Publ. Info"), values.get("[Place source]").trim() + " : " + 
																	values.get("[Publisher source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Volume / Issue"), values.get("[Volume source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Identifier"), values.get("[identifier source create item]").trim() + " : " + 
																	values.get("[identifier source value]").trim());
	}
	
	@Test(priority = 4, dependsOnMethods = { "submitEvent" })
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "submitEvent" })
	public void logoutDepositor() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 6, dependsOnMethods = { "submitEvent" })
	public void loginModerator() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}
	
	@Test(priority = 7, dependsOnMethods = { "submitEvent" })
	public void moderatorSendsBackSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
		viewItemPage = viewItemPage.sendBackForRework();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.IN_REWORK, "Item was not sent for rework.");
	}
	
	@Test(priority = 8, dependsOnMethods = { "submitEvent" })
	public void logoutModerator() {
		moderatorHomePage = (ModeratorHomePage) viewItemPage.goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
	
	@Test(priority = 9, dependsOnMethods = { "submitEvent" })
	public void loginDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
	}
	
	@Test(priority = 10, dependsOnMethods = { "submitEvent" })
	public void depositorRevisesItem() {
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 11, dependsOnMethods = { "submitEvent" })
	public void logoutDepositor2() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 12, dependsOnMethods = { "submitEvent" })
	public void loginModerator2() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		moderatorHomePage = loginPage.loginAsModerator(moderatorUsername, moderatorPassword);
	}
	
	@Test(priority = 13, dependsOnMethods = { "submitEvent" })
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 14, dependsOnMethods = { "submitEvent" })
	public void moderatorModifiesRelease() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.editItem();
	}
	
	@Test(priority = 15, dependsOnMethods = { "submitEvent" })
	public void moderatorReleasesSubmissionAgain() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 16, dependsOnMethods = { "submitEvent" })
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
