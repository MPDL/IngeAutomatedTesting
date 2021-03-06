package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseLoggedInUserTest;

/**
 * Testcase #3 <br>
 * TestLink UC #6 <br>
 * 
 * @author helk
 *
 */
public class FullStandardJournalArticleTest extends BaseLoggedInUserTest {

	private CombinedHomePage combinedHomePage;
	private StartPage startPage;

	private String title;
	private String submittedTitle;
	private String releasedTitle;
	private String author;
	private String newAuthor;
	private String secondAuthor;
	private String thirdAuthor;
	private String[] filepath;

	@Override
	@BeforeClass
	public void setup() {
		super.setup();
		filepath = new String[1];
		filepath[0] = getFilepath("SamplePDFFile.pdf");
		this.title = "Journal Article in standard workflow: " + getTimeStamp();
		this.submittedTitle = "Second modified title of journal article: " + getTimeStamp();
		this.releasedTitle = "Third modified title of journal article: " + getTimeStamp();
		this.author = "Test Testermann";
		this.newAuthor = "Test Testerfrau";
		this.secondAuthor = "Test Testington";
		this.thirdAuthor = "Test Test";
	}

	private void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}

	private void refreshHomePage() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
	}

	@Test(priority = 1)
	public void submitJournalArticle() {
		loginCombined();
		FullSubmissionPage fullSubmission = combinedHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		ViewItemPage viewItem = fullSubmission.fullSubmission("Journal Article", title, author, filepath);
		String actualTitle = viewItem.getItemTitle();

		Assert.assertEquals(actualTitle, title, "Title is not correct.");
	}

	@Test(priority = 2, dependsOnMethods = { "submitJournalArticle" })
	public void searchArticle() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, null,
				null);

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}

	@Test(priority = 3, dependsOnMethods = { "submitJournalArticle" })
	public void editTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItem = viewItem.editItem().editTitle(submittedTitle);
		String actualTitle = viewItem.getItemTitle();

		Assert.assertEquals(actualTitle, submittedTitle, "Title was not changed.");
	}

	@Test(priority = 4, dependsOnMethods = { "submitJournalArticle" })
	public void editAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().editAuthor(newAuthor);
	}

	@Test(priority = 5, dependsOnMethods = { "submitJournalArticle" })
	public void editAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle,
				newAuthor, "");

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");

		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(submittedTitle, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}

	@Test(priority = 6, dependsOnMethods = { "submitJournalArticle" })
	public void addAuthorSubmitted() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().addAuthor(secondAuthor);
	}

	@Test(priority = 7, dependsOnMethods = { "submitJournalArticle" })
	public void secondAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle,
				secondAuthor, "");

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}

	@Test(priority = 8, dependsOnMethods = { "submitJournalArticle" })
	public void releaseItem() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.submitItem();
		viewItem = viewItem.releaseItem();
	}

	@Test(priority = 9, dependsOnMethods = { "submitJournalArticle" })
	public void searchAndOpenReleasedItem() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		String exactSubmittedTitleSearchTerm = "\"" + submittedTitle + "\"";
		SearchResultsPage searchResults = startPage.quickSearch(exactSubmittedTitleSearchTerm);

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");

		ViewItemPage viewItem = searchResults.openFirstResult();
		Assert.assertEquals(viewItem.getPublicationTitle(), submittedTitle);

		loginCombined();
	}

	@Test(priority = 10, dependsOnMethods = { "submitJournalArticle" })
	public void editReleasedTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openReleasedItemByTitle(submittedTitle);
		viewItem = viewItem.editTitle(releasedTitle);
		String actualTitle = viewItem.getItemTitle();

		Assert.assertEquals(actualTitle, releasedTitle, "Title was not changed.");
	}

	@Test(priority = 11, dependsOnMethods = { "submitJournalArticle" })
	public void releaseItemAgain() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}

	@Test(priority = 12, dependsOnMethods = { "submitJournalArticle" })
	public void searchAndOpenReleasedItemNewTitle() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		String exactReleasedTitleSearchTerm = "\"" + releasedTitle + "\"";
		SearchResultsPage searchResults = startPage.quickSearch(exactReleasedTitleSearchTerm);

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");

		ViewItemPage viewItem = searchResults.openFirstResult();
		Assert.assertEquals(viewItem.getPublicationTitle(), releasedTitle);

		loginCombined();
	}

	@Test(priority = 13, dependsOnMethods = { "submitJournalArticle" })
	public void changeReleasedItemAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openReleasedItemByTitle(releasedTitle);
		viewItem.editAuthor(author);
	}

	@Test(priority = 14, dependsOnMethods = { "submitJournalArticle" })
	public void releaseItemAgain2() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}

	@Test(priority = 15, dependsOnMethods = { "submitJournalArticle" })
	public void modifiedAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle,
				author, "");

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");

		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(releasedTitle, newAuthor, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}

	@Test(priority = 16, dependsOnMethods = { "submitJournalArticle" })
	public void addAuthorReleased() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.addAuthor(thirdAuthor);
	}

	@Test(priority = 17, dependsOnMethods = { "submitJournalArticle" })
	public void releaseItemAgain3() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}

	@Test(priority = 18, dependsOnMethods = { "submitJournalArticle" })
	public void thirdAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle,
				thirdAuthor, "");

		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1,
				(resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}

	@Test(priority = 19, dependsOnMethods = { "submitJournalArticle" })
	public void discardItem() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openReleasedItemByTitle(releasedTitle);
		viewItem = viewItem.discardItem();
	}
}
