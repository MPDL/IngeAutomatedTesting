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
import test.java.base.BaseTest;
import test.java.base.Genre;

public class SubmitJournalArticleTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private StartPage startPage;
	
	private String title;
	private String newTitle;
	private String author;
	private String newAuthor;
	private String filepath;
	
	@BeforeClass
	public void setup() {
		super.setup();
		title = "Full submission of journal article in standard workflow: " + getTimeStamp();
		newTitle = "Modified journal article title: " + getTimeStamp();
		author = "Testermann, Testo";
		newAuthor = "Tester, Peter";
		filepath = getFilepath("SamplePDFFile.pdf");
	}
	
	@Test(priority = 1)
	public void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void submitJournalArticle() {
		FullSubmissionPage fullSubmission = combinedHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		ViewItemPage viewItem = fullSubmission.fullSubmission(Genre.ARTICLE, title, filepath);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, title, "Title is not correct.");
	}
	
	@Test(priority = 3)
	public void searchArticle() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, null, null);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 4)
	public void editTitle() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItem = viewItem.editItem().editTitle(newTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, newTitle, "Title was not changed.");
	}
	
	@Test(priority = 5)
	public void releaseItem() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(newTitle);
		viewItem = viewItem.submitItem();
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 6)
	public void searchReleasedItem() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(newTitle);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 7)
	public void modifyReleasedTitle() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
		
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(newTitle);
		viewItem = viewItem.modifyTitle(title);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, title, "Title was not changed.");
	}
	
	@Test(priority = 8)
	public void searchReleasedItemNewTitle() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(title);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 9)
	public void changeAuthor() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
		
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(title);
		viewItem.modifyAuthor(newAuthor);
	}
	
	@Test(priority = 10)
	public void adminAuthorSearch() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, newAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(title, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 11)
	public void discardItem() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(title);
		viewItem = viewItem.discardItem();
		viewItem.goToHomePage(combinedHomePage).logout();
	}
}
