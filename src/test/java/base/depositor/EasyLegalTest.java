package test.java.base.depositor;

import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseTest;
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

public class EasyLegalTest extends BaseTest {

	private String title;
	private Map<String, String> values;
	
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
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void easySubmissionStandardWorkflow() {
		EasySubmissionPage easySubmissionPage = depositorHomePage.goToSubmissionPage().depositorGoToEasySubmissionPage();
		viewItemPage = easySubmissionPage.easySubmissionLegal(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}	
	
	@Test(priority = 3)
	public void checkDataCorrectness() {
		Map<String, String> values = table.getMap();
		title = values.get("[title]");
		
		values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		compare("Genre", "LEGAL");
		compare("Name", "[upload file]");
		compare("Description", "[description file]");
		compare("Visibility", "[Visibility]");
		compare("Copyright Date", "[Copyright Date]");
		compare("Copyright Info", "[Copyright statement]");
		compare("License", "[license URL]");
		compare("Free keywords", "[free keywords]");
		compare("Abstract", "[abstract]");
		compare("Pages", "[Total no of pages source]");
		compare("Title", "[title source]");
		compare("Source Genre", "[genre source]");
		Assert.assertEquals(viewItemPage.getLabel("Identifiers"), values.get("[identifier create item]").trim() + ": " + values.get("[identifier value]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Volume / Issue"), values.get("[Volume source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Identifier"), values.get("[identifier source create item]").trim() + " : " + 
																	values.get("[identifier source value]").trim());
	}
	
	private void compare(String label, String expected) {
		Assert.assertEquals(viewItemPage.getLabel(label), values.get(expected).trim());
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
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openSubmittedItemByTitle(title);
		viewItemPage = viewItemPage.acceptItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openSubmittedItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
	}
	
	@AfterClass
	public void moderatorLogout() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		moderatorHomePage.logout();
	}
	
	@AfterClass
	public void saveDataAfterFailure(ITestContext context) {
		if (context.getFailedTests().size() > 0) {
			String testOutputPath = "./target/" + this.getClass().getSimpleName() + ".txt";
			table.writeContentsToFile(testOutputPath);
		}
	}
	
}
