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

/**
 * Testcase #1 <br>
 * Test Link UC #4 <br>
 * 
 * @author helk
 *
 */
public class FullSimpleEventDependentTest extends BaseLoggedInUserTest {

	private String title;
	
	private DepositorHomePage depositorHomePage;
	private ViewItemPage viewItemPage;
	
	private TableHelper table = new TableHelper();
	private Map<String, String> values;
	
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
	public void fullSubmissionSimpleEventDependent() {
		FullSubmissionPage fullSubmissionPage = depositorHomePage.goToSubmissionPage().goToFullSubmissionSimplePage();
		viewItemPage = fullSubmissionPage.fullSubmissionEventDepSimple(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3, dependsOnMethods = { "fullSubmissionSimpleEventDependent" })
	public void checkDataCorrectness() {
		values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		compare("Genre", "DEGREE");
		compare("Name", "[upload file]");
		compare("Description", "[description file]");
		compare("Visibility", "[Visibility]");
		compare("Copyright Date", "[Copyright Date]");
		compare("Copyright Info", "[Copyright statement]");
		compare("License", "[license URL]");
		compare("Free keywords", "[free keywords]");
		compare("Abstract", "[abstract]");
		compare("Pages", "[Total no of pages source]");
		compare("Degree", "[degree type]");
		compare("Project name", "[Project name]");
		compare("Grant ID", "[Grant ID]");
		compare("Funding program", "[Funding program]");
		compare("Title", "[title source]");
		compare("Source Genre", "[genre source]");
		compare("Volume / Issue", "[Volume source]");
		if (values.containsKey("[identifier create item]") && values.get("[identifier create item]") != null
		  && values.containsKey("[identifier value]") && values.get("[identifier value]") != null)
        {
		  Assert.assertEquals(viewItemPage.getValue("Identifiers"), values.get("[identifier create item]").trim() + ": " + values.get("[identifier value]").trim());
        }
		if (values.containsKey("[Publisher source]") && values.get("[Publisher source]") != null)
        {
		  Assert.assertEquals(viewItemPage.getValue("Publ. Info"), values.get("[Place source]").trim() + " : " + values.get("[Publisher source]").trim());
        }
		if (values.containsKey("[identifier source create item]") && values.get("[identifier source create item]") != null
	      && values.containsKey("[identifier source value]") && values.get("[identifier source value]") != null)
	    {
		  Assert.assertEquals(viewItemPage.getValue("Identifier"), values.get("[identifier source create item]").trim() + " : " + values.get("[identifier source value]").trim());
        }
	}
	
	private void compare(String label, String expected) {
	  if (values.get(expected) == null)
      {
        log4j.info("Expected Value empty. Won't compare");
        return;
      }
		Assert.assertEquals(viewItemPage.getValue(label), values.get(expected).trim());
	}
	
	@Test(priority = 4, dependsOnMethods = { "fullSubmissionSimpleEventDependent" })
	public void depositorReleasesSubmission() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		viewItemPage = depositorHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "fullSubmissionSimpleEventDependent" })
	public void viewMostRecentItems() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		String mostRecentItemTitle = depositorHomePage.goToStartPage().getNameOfMostRecentItem();
		Assert.assertEquals(mostRecentItemTitle, title, "Item does not show up at the start page.");
	}
	
	@Test(priority = 6, dependsOnMethods = { "fullSubmissionSimpleEventDependent" })
	public void viewItem() {
		MyItemsPage myItemsPage = depositorHomePage.goToMyItemsPage();
		viewItemPage = myItemsPage.openReleasedItemByTitle(title);
		String actualTitle = viewItemPage.getItemTitle();
		Assert.assertEquals(actualTitle, title, "Expected and actual title do not match.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "fullSubmissionSimpleEventDependent" })
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
