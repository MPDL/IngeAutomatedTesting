package test.java.base.depositor;

import java.util.Map;
import java.util.Map.Entry;

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
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

/**
 * Testcase #4 <br>
 * TestLink UC #7 <br>
 * 
 * @author helk
 *
 */
public class EasyEventDependentTest extends BaseLoggedInUserTest {
  
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
		depositorHomePage = new StartPage(driver).loginAsDepositor(depositorUsername, depositorPassword);
		Assert.assertEquals(depositorHomePage.getUsername(), depositorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 2)
	public void easySubmissionStandardWorkflow() {
		EasySubmissionPage easySubmissionPage = depositorHomePage.goToSubmissionPage().goToEasySubmissionStandardPage();
		log4j.debug("Table: " + table);
        for(Entry<String, String> entry : table.getMap().entrySet()) {
          log4j.debug("Table-Key: " + entry.getKey() + " - Table-Value: " + entry.getValue());
        }
		viewItemPage = easySubmissionPage.easySubmissionEventDependent(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 3)
	public void checkDataCorrectness() {
		values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		compare("Genre", "EVENT_DEPENDENT");
		compare("Name", "[upload file]");
		compare("Description", "[description file]");
		compare("Visibility", "[Visibility]");
		compare("Copyright Date", "[Copyright Date]");
		compare("Copyright Info", "[Copyright statement]");
		compare("License", "[license URL]");
		compare("Free keywords", "[free keywords]");
		compare("Abstract", "[abstract]");
		compare("Pages", "[Total no of pages source]");
		compare("Place of Event", "[Place of event]");
		compare("Source Genre", "[genre source]");
	}
	
	private void compare(String label, String expected) {
  	    if (!values.containsKey(expected) || values.get(expected) == null)
        {
          log4j.info("Expected Value empty. Won't compare");
          return;
        }
		Assert.assertEquals(viewItemPage.getValue(label), values.get(expected).trim());
	}
	
	@Test(priority = 4, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void depositorSubmitsItem() {
		viewItemPage = viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item was not submitted.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "easySubmissionStandardWorkflow"})
	public void logoutDepositor() {
		depositorHomePage = (DepositorHomePage) new StartPage(driver).goToHomePage(depositorHomePage);
		depositorHomePage.logout();
	}
	
	@Test(priority = 6, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void loginAsModerator() {
		moderatorHomePage = new StartPage(driver).loginAsModerator(moderatorUsername, moderatorPassword);
		Assert.assertEquals(moderatorHomePage.getUsername(), moderatorName, "Expected and actual name don't match.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void moderatorReleasesSubmission() {
		viewItemPage = moderatorHomePage.goToQAWorkspacePage().openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.releaseItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.RELEASED, "Item was not released.");
	}
	
	@Test(priority = 8, dependsOnMethods = { "easySubmissionStandardWorkflow" })
	public void moderatorDiscardsSubmission() {
		moderatorHomePage = (ModeratorHomePage) new StartPage(driver).goToHomePage(moderatorHomePage);
		viewItemPage = moderatorHomePage.openReleasedItemByTitle(title);
		viewItemPage = viewItemPage.discardItem();
	}
	
	/**
	 * If any method fails, save the randomly generated data for reproducibility.
	 */
	@AfterClass
	public void saveDataAfterFailure(ITestContext context) {
		if (context.getFailedTests().size() > 0) {
			String testOutputPath = "./target/" + this.getClass().getSimpleName() + ".txt";
			table.writeContentsToFile(testOutputPath);
		}
	}
}
