package test.java.base.moddep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
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
	private String submittedTitle;
	private String releasedTitle;
	private String author;
	private String secondAuthor;
	private String thirdAuthor;
	private String filepath;
	
	private static class ReindexerDataIterator implements Iterator<Object[]> {
		private int index = 0;
		List<Object[]> allData = new ArrayList<>();
		
		public ReindexerDataIterator() {
			
			try (BufferedReader br = new BufferedReader(new FileReader("res/reindexer_data.csv"))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] data = line.split(",");
					allData.add(data);
				}
			}
			catch (IOException exc) {}
		}
		
		@Override
		public boolean hasNext() {
			return index < allData.size() - 1;
		}
		
		@Override
		public Object[] next() {
			return allData.get(index++);
		}
		
		@Override
		public void remove() {
			throw new RuntimeException();
		}
	}
	
	@DataProvider(name = "reindexer_data")
	public static Iterator<Object[]> reindexerDataProvider() {
		return new ReindexerDataIterator();
	}
	
	@BeforeClass
	public void setup() {
		super.setup();
		filepath = getFilepath("SamplePDFFile.pdf");
	}
	
	@BeforeMethod
	private void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Factory(dataProvider = "reindexer_data")
	public SubmitJournalArticleTest(String id, String title, String submittedTitle, String releasedTitle, String author, String newAuthor) {
		this.title = id + " " + title + ": " + getTimeStamp();
		this.submittedTitle = id + " " + submittedTitle + ": " + getTimeStamp();
		this.releasedTitle = id + " " + releasedTitle + ": " + getTimeStamp();
		this.author = author;
		this.secondAuthor = newAuthor;
	}
	
	@Test(priority = 1)
	public void submitJournalArticle() {
		FullSubmissionPage fullSubmission = combinedHomePage.goToSubmissionPage().goToFullSubmissionStandardPage();
		ViewItemPage viewItem = fullSubmission.fullSubmission(Genre.ARTICLE, title, author, filepath);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, title, "Title is not correct.");
	}
	
	@Test(priority = 2)
	public void searchArticle() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, null, null);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 3)
	public void editTitle() {
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItem = viewItem.editItem().editTitle(submittedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, submittedTitle, "Title was not changed.");
	}
	
	@Test(priority = 4)
	public void editAuthor() {
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().editAuthor(secondAuthor);
	}
	
	@Test(priority = 5)
	public void editAuthorSearch() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, secondAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(submittedTitle, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 6)
	public void addAuthorSubmitted() {
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().addAuthor(secondAuthor);
	}
	
	@Test(priority = 7)
	public void secondAuthorSearch() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, secondAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 8)
	public void releaseItem() {
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.submitItem();
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 9)
	public void searchReleasedItem() {
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(submittedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 10)
	public void modifyReleasedTitle() {
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(submittedTitle);
		viewItem = viewItem.modifyTitle(releasedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, releasedTitle, "Title was not changed.");
	}
	
	@Test(priority = 11)
	public void searchReleasedItemNewTitle() {
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(releasedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 12)
	public void changeAuthor() {
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(releasedTitle);
		viewItem.modifyAuthor(secondAuthor);
	}
	
	@Test(priority = 13)
	public void adminAuthorSearch() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, secondAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(releasedTitle, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 14)
	public void changeReleasedItemAuthor() {
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(releasedTitle);
		viewItem.modifyAuthor(author);
	}
	
	@Test(priority = 15)
	public void modifiedAuthorSearch() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, author, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(releasedTitle, secondAuthor, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 16)
	public void addAuthorReleased() {
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.modifyAddAuthor(thirdAuthor);
	}
	
	@Test(priority = 17)
	public void thirdAuthorSearch() {
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, thirdAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, (resultCount == 0) ? "No results were found," : "There are more results with this title.");
	}
	
	@Test(priority = 18)
	public void discardItem() {
		ViewItemPage viewItem = combinedHomePage.openPublishedItemByTitle(releasedTitle);
		viewItem = viewItem.discardItem();
	}
	
	@AfterMethod
	private void logout() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
		combinedHomePage.logout();
	}
}
