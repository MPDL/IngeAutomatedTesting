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
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.GenreGroup;
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

/**
 * Testcase #1 <br>
 * Test Link UC #4 <br>
 * 
 * @author helk
 *
 */
public class FullSimpleSourceDependentTest extends BaseLoggedInUserTest {

	private DepositorHomePage depositorHomePage;
	private ViewItemPage viewItemPage;
	
	private String title;
	
	private TableHelper table = new TableHelper();
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Released conference paper in simple workflow: " + getTimeStamp();
	}
	
	@Test(priority = 1)
	public void loginAsDepositor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		depositorHomePage = loginPage.loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void fullSubmissionSimpleSourceDependent() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionSimplePage();
		viewItemPage = fullSubmissionPage.fullSubmissionSrcDep(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void checkDataCorrectness() {
		Map<String, String> values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		Assert.assertEquals(viewItemPage.getValue("Genre"), values.get(GenreGroup.SOURCE_DEP.toString()));
		Assert.assertEquals(viewItemPage.getValue("Name"), values.get("[upload file]").trim());
		Assert.assertEquals(viewItemPage.getValue("Description"), values.get("[description file]").trim());
		Assert.assertEquals(viewItemPage.getValue("Visibility"), values.get("[Visibility]"));
		Assert.assertEquals(viewItemPage.getValue("Copyright Date"), values.get("[Copyright Date]").trim());
		Assert.assertEquals(viewItemPage.getValue("Copyright Info"), values.get("[Copyright statement]").trim());
		Assert.assertEquals(viewItemPage.getValue("License"), values.get("[license URL]").trim());
		Assert.assertEquals(viewItemPage.getValue("Free keywords"), values.get("[free keywords]").trim());
		Assert.assertEquals(viewItemPage.getValue("Abstract"), values.get("[abstract]").trim());
		Assert.assertEquals(viewItemPage.getValue("Pages"), values.get("[total no of pages]").trim());
		Assert.assertEquals(viewItemPage.getValue("Degree"), "-");
		Assert.assertEquals(viewItemPage.getValue("Project name"), values.get("[Project name]").trim());
		Assert.assertEquals(viewItemPage.getValue("Identifiers"), values.get("[identifier create item]").trim() + ": " + 
																	values.get("[identifier value]").trim());
		Assert.assertEquals(viewItemPage.getValue("Grant ID"), values.get("[Grant ID]").trim());
		Assert.assertEquals(viewItemPage.getValue("Funding program"), values.get("[Funding program]").trim());
		Assert.assertEquals(viewItemPage.getValue("Title"), values.get("[title source]").trim());
		Assert.assertEquals(viewItemPage.getValue("Source Genre"), values.get("[genre source]"));
		Assert.assertEquals(viewItemPage.getValue("Publ. Info"), values.get("[Publisher source]").trim());
		Assert.assertEquals(viewItemPage.getValue("Volume / Issue"), values.get("[Volume source]").trim());
		Assert.assertEquals(viewItemPage.getValue("Identifier"), values.get("[identifier source create item]").trim() + ": " + 
																	values.get("[identifier source value]").trim());
	}
	
	@Test(priority = 4, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void depositorReleasesSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void viewMostRecentItems() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		String mostRecentItemTitle = depositorHomePage.goToStartPage().getNameOfMostRecentItem();
		Assert.assertEquals(mostRecentItemTitle, title, "Item does not show up at the start page.");
	}
	
	@Test(priority = 6, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void viewItem() {
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		viewItemPage = myItemsPage.openReleasedItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void discardSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		myItemsPage.discardItemByTitle(title);
	}
	
	@AfterClass
	public void saveDataAfterFailure(ITestContext context) {
		if (context.getFailedTests().size() > 0) {
			String testOutputPath = "./target/" + this.getClass().getSimpleName() + ".txt";
			table.writeContentsToFile(testOutputPath);
		}
	}
}
