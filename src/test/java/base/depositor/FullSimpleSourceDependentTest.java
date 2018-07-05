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
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

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
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().depositorGoToFullSubmissionPage();
		viewItemPage = fullSubmissionPage.fullSubmissionSrcDep(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3, dependsOnMethods = { "fullSubmissionSimpleSourceDependent" })
	public void checkDataCorrectness() {
		Map<String, String> values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		Assert.assertEquals(viewItemPage.getLabel("Genre"), values.get("DEGREE"));
		Assert.assertEquals(viewItemPage.getLabel("Name"), values.get("[upload file]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Description"), values.get("[description file]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Visibility"), values.get("[Visibility]"));
		Assert.assertEquals(viewItemPage.getLabel("Copyright Date"), values.get("[Copyright Date]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Copyright Info"), values.get("[Copyright statement]").trim());
		Assert.assertEquals(viewItemPage.getLabel("License"), values.get("[license URL]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Free keywords"), values.get("[free keywords]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Abstract"), values.get("[abstract]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Pages"), values.get("[Total no of pages source]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Degree"), values.get("[degree type]"));
		Assert.assertEquals(viewItemPage.getLabel("Project name"), values.get("[Project name]").trim());
		Assert.assertEquals(viewItemPage.getLabel("Identifiers"), values.get("[identifier create item]").trim() + ": " + 
																	values.get("[identifier value]").trim());
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
