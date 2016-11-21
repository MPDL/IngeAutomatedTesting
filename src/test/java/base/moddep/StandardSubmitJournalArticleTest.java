package test.java.base.moddep;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.ViewItemPage;
import test.java.base.BaseTest;
import test.java.base.Genre;

public class StandardSubmitJournalArticleTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private StartPage startPage;
	
	private String title;
	private String submittedTitle;
	private String releasedTitle;
	private String author;
	private String newAuthor;
	private String secondAuthor;
	private String thirdAuthor;
	private String filepath;
	
	
	@BeforeClass
	public void setup() {
		super.setup();
		filepath = getFilepath("SamplePDFFile.pdf");
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
		ViewItemPage viewItem = fullSubmission.fullSubmission(Genre.ARTICLE, title, author, filepath);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, title, "Title is not correct.");
	}
	
	@Test(priority = 2)
	public void searchArticle() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, null, null);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 3)
	public void editTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItem = viewItem.editItem().editTitle(submittedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, submittedTitle, "Title was not changed.");
	}
	
	@Test(priority = 4)
	public void editAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().editAuthor(newAuthor);
	}
	
	@Test(priority = 5)
	public void editAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, newAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(submittedTitle, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 6)
	public void addAuthorSubmitted() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().addAuthor(secondAuthor);
	}
	
	@Test(priority = 7)
	public void secondAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, secondAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 8)
	public void releaseItem() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.submitItem();
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 9)
	public void searchReleasedItem() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(submittedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 10)
	public void modifyReleasedTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openSubmittedItemByTitle(submittedTitle);
		viewItem = viewItem.modifyTitle(releasedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, releasedTitle, "Title was not changed.");
	}
	
	@Test(priority = 11)
	public void searchReleasedItemNewTitle() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(releasedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 12)
	public void changeReleasedItemAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openSubmittedItemByTitle(releasedTitle);
		viewItem.modifyAuthor(author);
	}
	
	@Test(priority = 13)
	public void modifiedAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, author, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(releasedTitle, newAuthor, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 14)
	public void addAuthorReleased() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.modifyAddAuthor(thirdAuthor);
	}
	
	@Test(priority = 15)
	public void thirdAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, thirdAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 16)
	public void discardItem() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openSubmittedItemByTitle(releasedTitle);
		viewItem = viewItem.discardItem();
	}
	
	@AfterClass
	public void tearDown() {
		refreshHomePage();
		combinedHomePage.logout();
	}
}
