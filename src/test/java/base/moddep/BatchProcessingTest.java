package test.java.base.moddep;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import main.java.pages.workspaces.BatchWorkspaceProtocolPage;
import main.java.pages.workspaces.BatchWorkspaceViewPage;
import test.java.base.BaseLoggedInUserTest;
import test.java.base.ItemStatus;

/**
 * Test for a (rudimentary example) Batch Processing workflow.
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
	private final String localTagName = "Local tag 1.";

	private final String wrongBatchActionSate = "Wrong Batch Action state for item: ";
	private final String itemHasNoTags = "Item has no tags.";

	@Override
	@BeforeClass
	public void setup() {
		super.setup();
		filepath = new String[] { getFilepath("SamplePDFFile.pdf") };
		this.title1 = "Batch Processing item 1: " + getTimeStamp();
		this.title2 = "Batch Processing item 2: " + getTimeStamp();
		this.title3 = "Batch Processing item 3: " + getTimeStamp();
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
	public void submitItem3() {
		viewItemPage.submitItem();
		ItemStatus itemStatus = viewItemPage.getItemStatus();

		Assert.assertEquals(itemStatus, ItemStatus.SUBMITTED, "Item is not submitted.");
	}

	@Test(priority = 7)
	public void addItemsToBatchProcessingViaMyItemsList() {
		refreshHomePage();
		combinedHomePage.goToMyItemsPage().openActionsView().selectAndAddToBatchProcessing(this.title2, this.title3);
	}

	@Test(priority = 8)
	public void openBatchProcessingWorkspace() {
		refreshHomePage();
		batchProcessingViewPage = combinedHomePage.goToBatchProcessingWorkspace();
	}

	@Test(priority = 9)
	public void submitViaBatchProcessing() {
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().submit();

		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title1), successState,
				wrongBatchActionSate + title1);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title2), successState,
				wrongBatchActionSate + title2);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title3), "ERROR", wrongBatchActionSate + title3);
	}

	@Test(priority = 10)
	public void addTagViaBatchProcessing() {
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().addLocalTag(localTagName);

		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title1), successState,
				wrongBatchActionSate + title1);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title2), successState,
				wrongBatchActionSate + title2);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title3), successState,
				wrongBatchActionSate + title3);
	}

	@Test(priority = 11)
	public void checkBatchProcessingResultOfSubmitAndLocalTag() {
		refreshHomePage();

		ViewItemPage viewItem1Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title1);
		ItemStatus itemStatus1 = viewItem1Page.getItemStatus();
		Assert.assertEquals(itemStatus1, ItemStatus.SUBMITTED, "Item 1 was not submitted.");
		List<String> localTags1 = viewItem1Page.getLocalTags();
		Assert.assertEquals(localTags1.isEmpty(), false, itemHasNoTags);
		Assert.assertEquals(localTags1.get(0), localTagName);

		ViewItemPage viewItem2Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title2);
		ItemStatus itemStatus2 = viewItem2Page.getItemStatus();
		Assert.assertEquals(itemStatus2, ItemStatus.SUBMITTED, "Item 2 was not submitted.");
		List<String> localTags2 = viewItem2Page.getLocalTags();
		Assert.assertEquals(localTags2.isEmpty(), false, itemHasNoTags);
		Assert.assertEquals(localTags2.get(0), localTagName);

		ViewItemPage viewItem3Page = combinedHomePage.goToMyItemsPage().openItemByTitle(title3);
		ItemStatus itemStatus3 = viewItem3Page.getItemStatus();
		Assert.assertEquals(itemStatus3, ItemStatus.SUBMITTED, "Item 3 was not submitted.");
		List<String> localTags3 = viewItem3Page.getLocalTags();
		Assert.assertEquals(localTags3.isEmpty(), false, itemHasNoTags);
		Assert.assertEquals(localTags3.get(0), localTagName);
	}

	@Test(priority = 12)
	public void deleteViaBatchProcessing() {
		refreshHomePage();
		batchProcessingViewPage = combinedHomePage.goToBatchProcessingWorkspace();
		batchProcessingProtocolPage = batchProcessingViewPage.openActionsView().delete();

		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title1), successState,
				wrongBatchActionSate + title1);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title2), successState,
				wrongBatchActionSate + title2);
		Assert.assertEquals(batchProcessingProtocolPage.getLogState(title3), successState,
				wrongBatchActionSate + title3);
	}

	@Test(priority = 13)
	public void checkAllItemsAreDeleted() {
		refreshHomePage();

		SearchResultsPage searchResultsPage = combinedHomePage.quickSearch(title1);
		Assert.assertTrue(searchResultsPage.noItemsFound(), "Discarded item " + title1 + " is still found.");

		searchResultsPage = searchResultsPage.quickSearch(title2);
		Assert.assertTrue(searchResultsPage.noItemsFound(), "Discarded item " + title2 + " is still found.");

		searchResultsPage = searchResultsPage.quickSearch(title3);
		Assert.assertTrue(searchResultsPage.noItemsFound(), "Discarded item " + title3 + " is still found.");
	}

}
