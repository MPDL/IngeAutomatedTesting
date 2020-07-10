package test.java.base.moddep;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import main.java.pages.workspaces.BatchWorkspaceProtocolPage;
import main.java.pages.workspaces.BatchWorkspaceViewPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;

/**
 * Test the Batch Processing workflow.
 * 
 * @author helk
 *
 */
public class BatchProcessingTest extends BaseLoggedInUserTest {

	private CombinedHomePage combinedHomePage;
	private ViewItemPage viewItemPage;
	private BatchWorkspaceViewPage batchProcessingViewPage;
	private BatchWorkspaceProtocolPage batchProcessingProtocolPage;

	private String title1;
	private String title2;
	private String title3;
	private String author;
	private String[] filepath;

	private final String successState = "SUCCESS";
	private final String wrongBatchActionSate = "Wrong Batch Action state.";
	private final String localTagName = "Local tag 1.";

	@Override
	@BeforeClass
	public void setup() {
		super.setup();
		filepath = new String[] { getFilepath("SamplePDFFile.pdf") };
		this.title1 = "Batch Processing 1: " + getTimeStamp();
		this.title2 = "Batch Processing 2: " + getTimeStamp();
		this.title3 = "Batch Processing 3: " + getTimeStamp();
		this.author = "Test Testermann";
	}

	private void refreshHomePage() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
	}

	@Test(priority = 1)
	public void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}

	@Test(priority = 2)
	public void saveItem1ByFullSubmission() {
		FullSubmissionPage fullSubmission = combinedHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		viewItemPage = fullSubmission.fullSubmission("Journal Article", title1, author, filepath);
		ItemStatus itemStatus = viewItemPage.getItemStatus();

		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item is not pending.");
	}

	@Test(priority = 3)
	public void addItem1ToBatchProcessingViaItemView() {
		viewItemPage.addToBatchProcessing();
	}

	@Test(priority = 4)
	public void saveItem2ByEasySubmission() {
		refreshHomePage();
		EasySubmissionPage easySubmissionPage = combinedHomePage.goToSubmissionPage().goToEasySubmissionStandardPage();
		viewItemPage = easySubmissionPage.easySubmission("Journal Article", title2, "SamplePDFFile.pdf");
		ItemStatus itemStatus = viewItemPage.getItemStatus();

		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item is not pending.");
	}

	@Test(priority = 5)
	public void saveItem3ByEasySubmission() {
		refreshHomePage();
		EasySubmissionPage easySubmissionPage = combinedHomePage.goToSubmissionPage().goToEasySubmissionStandardPage();
		viewItemPage = easySubmissionPage.easySubmission("Journal Article", title3, "SamplePDFFile.pdf");
		ItemStatus itemStatus = viewItemPage.getItemStatus();

		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item is not pending.");
	}

	@Test(priority = 6)
	public void addItemsToBatchProcessingViaMyItemsList() {
		refreshHomePage();
		combinedHomePage.goToMyItemsPage().openActionsView().selectAndAddToBatchProcessing(this.title2, this.title3);
	}

	@Test(priority = 7)
	public void openBatchProcessingWorkspace() {
		refreshHomePage();
		batchProcessingViewPage = combinedHomePage.goToBatchProcessingWorkspace();
	}

	@Test(priority = 8)
	public void submitViaBatchProcessing() {
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().submit();

		List<String> logStates = batchProcessingProtocolPage.getLogStates();
		// TODO: get Log-State by itemTitle!
		Assert.assertEquals(logStates.get(0), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(1), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(2), successState, wrongBatchActionSate);
	}

	@Test(priority = 9)
	public void addTagViaBatchProcessing() {
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().addLocalTag(localTagName);

		List<String> logStates = batchProcessingProtocolPage.getLogStates();
		Assert.assertEquals(logStates.get(0), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(1), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(2), successState, wrongBatchActionSate);
	}

	@Test(priority = 10)
	public void checkBatchProcessingResultOfSubmitAndLocalTag() {
		refreshHomePage();

		ViewItemPage viewItem1Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title1);
		ItemStatus itemStatus1 = viewItem1Page.getItemStatus();
		Assert.assertEquals(itemStatus1, ItemStatus.SUBMITTED, "Item 1 was not submitted.");
		List<String> localTags1 = viewItem1Page.getLocalTags();
		Assert.assertEquals(localTags1.isEmpty(), false, "Item has no tags.");
		Assert.assertEquals(localTags1.get(0), localTagName, "First tag not equals " + localTagName);

		ViewItemPage viewItem2Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title2);
		ItemStatus itemStatus2 = viewItem2Page.getItemStatus();
		Assert.assertEquals(itemStatus2, ItemStatus.SUBMITTED, "Item 2 was not submitted.");
		List<String> localTags2 = viewItem2Page.getLocalTags();
		Assert.assertEquals(localTags2.isEmpty(), false, "Item has no tags.");
		Assert.assertEquals(localTags2.get(0), localTagName, "First tag not equals " + localTagName);

		ViewItemPage viewItem3Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title3);
		ItemStatus itemStatus3 = viewItem3Page.getItemStatus();
		Assert.assertEquals(itemStatus3, ItemStatus.SUBMITTED, "Item 3 was not submitted.");
		List<String> localTags3 = viewItem3Page.getLocalTags();
		Assert.assertEquals(localTags3.isEmpty(), false, "Item has no tags.");
		Assert.assertEquals(localTags3.get(0), localTagName, "First tag not equals " + localTagName);
	}

	@Test(priority = 11)
	public void deleteViaBatchProcessing() {
		refreshHomePage();
		batchProcessingViewPage = combinedHomePage.goToBatchProcessingWorkspace();
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().delete();

		List<String> logStates = batchProcessingProtocolPage.getLogStates();
		Assert.assertEquals(logStates.get(0), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(1), successState, wrongBatchActionSate);
		Assert.assertEquals(logStates.get(2), successState, wrongBatchActionSate);
	}

	@Test(priority = 12)
	public void checkAllItemsAreDeleted() {
		refreshHomePage();

		boolean myItemsIsNotEmpty = combinedHomePage.goToMyItemsPage().hasItems();
		Assert.assertEquals(myItemsIsNotEmpty, false, "My Items is not empty. Deletion of the items did not work.");
	}

}
