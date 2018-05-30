package test.java.base.moddep;

import java.util.Map;

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
import test.java.base.ItemStatus;
import test.java.base.TableHelper;

public class FullStandardSourceDependentBookTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private StartPage startPage;
	private ViewItemPage viewItemPage;
	
	private String title;
	private String submittedTitle = "Submitted title " + getTimeStamp();
	private String releasedTitle = "Released title " + getTimeStamp();
	private String author;
	private String newAuthor = "Author Edited";
	private String secondAuthor = "Author TheSecond";
	private String thirdAuthor = "Author TheThird";
	
	private TableHelper table = new TableHelper();
	private Map<String, String> values;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	private void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	private void refreshHomePage() {
		combinedHomePage = (CombinedHomePage) new StartPage(driver).goToHomePage(combinedHomePage);
	}
	
	@Test(priority = 1)
	public void submitSourceDependentBook() {
		loginCombined();
		FullSubmissionPage fullSubmission = combinedHomePage.goToSubmissionPage().depositorGoToFullSubmissionPage();
		viewItemPage = fullSubmission.fullSubmissionSrcDepBook(table);
		ItemStatus itemStatus = viewItemPage.getItemStatus();
		Assert.assertEquals(itemStatus, ItemStatus.PENDING, "Item was not uploaded.");
	}
	
	@Test(priority = 2, dependsOnMethods = { "submitSourceDependentBook" })
	public void checkDataCorrectness() {
		values = table.getMap();
		title = values.get("[title]");
		
		Assert.assertEquals(viewItemPage.getItemTitle(), title.trim());
		compare("Genre", "SOURCE_DEPENDENT");
		compare("Name", "[upload file]");
		compare("Description", "[description file]");
		compare("Visibility", "[Visibility]");
		compare("Copyright Date", "[Copyright Date]");
		compare("Copyright Info", "[Copyright statement]");
		compare("License", "[license URL]");
		compare("Free keywords", "[free keywords]");
		compare("Abstract", "[abstract]");
	}
	
	private void compare(String label, String expected) {
		Assert.assertEquals(viewItemPage.getLabel(label), values.get(expected).trim());
	}
	
	@Test(priority = 3, dependsOnMethods = { "submitSourceDependentBook" })
	public void searchArticle() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(title, null, null);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + title + "'");
	}
	
	@Test(priority = 4, dependsOnMethods = { "submitSourceDependentBook" })
	public void editTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(title);
		viewItem = viewItem.editItem().editTitle(submittedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, submittedTitle, "Title was not changed.");
	}
	
	@Test(priority = 5, dependsOnMethods = { "submitSourceDependentBook" })
	public void editAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().editAuthor(newAuthor);
	}
	
	@Test(priority = 6, dependsOnMethods = { "submitSourceDependentBook" })
	public void editAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, newAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + submittedTitle + "'");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(submittedTitle, author, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 7, dependsOnMethods = { "submitSourceDependentBook" })
	public void addAuthorSubmitted() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.editItem().addAuthor(secondAuthor);
	}
	
	@Test(priority = 8, dependsOnMethods = { "submitSourceDependentBook" })
	public void secondAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(submittedTitle, secondAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + submittedTitle + "'");
	}
	
	@Test(priority = 9, dependsOnMethods = { "submitSourceDependentBook" })
	public void releaseItem() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(submittedTitle);
		viewItem = viewItem.submitItem();
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 10, dependsOnMethods = { "submitSourceDependentBook" })
	public void searchReleasedItem() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(submittedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + submittedTitle + "'");
	}
	
	@Test(priority = 11, dependsOnMethods = { "submitSourceDependentBook" })
	public void modifyReleasedTitle() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openSubmittedItemByTitle(submittedTitle);
		viewItem = viewItem.modifyTitle(releasedTitle);
		String actualTitle = viewItem.getItemTitle();
		
		Assert.assertEquals(actualTitle, releasedTitle, "Title was not changed.");
	}
	
	@Test(priority = 12, dependsOnMethods = { "submitSourceDependentBook" })
	public void releaseItemAgain() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 13, dependsOnMethods = { "submitSourceDependentBook" })
	public void searchReleasedItemNewTitle() {
		refreshHomePage();
		startPage = combinedHomePage.logout();
		SearchResultsPage searchResults = startPage.quickSearch(releasedTitle);
		
		int resultCount = searchResults.getResultCount();
		loginCombined();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + releasedTitle + "'");
	}
	
	@Test(priority = 14, dependsOnMethods = { "submitSourceDependentBook" })
	public void changeReleasedItemAuthor() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.openSubmittedItemByTitle(releasedTitle);
		viewItem.modifyAuthor(author);
	}
	
	@Test(priority = 15, dependsOnMethods = { "submitSourceDependentBook" })
	public void releaseItemAgain2() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 16, dependsOnMethods = { "submitSourceDependentBook" })
	public void modifiedAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, author, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + releasedTitle + "'");
		
		searchResults = searchResults.goToAdvancedSearchPage().advancedSearch(releasedTitle, newAuthor, "");
		resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 0, "Item is still found with old author name.");
	}
	
	@Test(priority = 17, dependsOnMethods = { "submitSourceDependentBook" })
	public void addAuthorReleased() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.modifyAddAuthor(thirdAuthor);
	}
	
	@Test(priority = 18, dependsOnMethods = { "submitSourceDependentBook" })
	public void releaseItemAgain3() {
		refreshHomePage();
		ViewItemPage viewItem = combinedHomePage.goToMyItemsPage().openItemByTitle(releasedTitle);
		viewItem = viewItem.releaseItem();
	}
	
	@Test(priority = 19, dependsOnMethods = { "submitSourceDependentBook" })
	public void thirdAuthorSearch() {
		refreshHomePage();
		SearchResultsPage searchResults = combinedHomePage.goToAdministrativeSearchPage().advancedSearch(releasedTitle, thirdAuthor, "");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "No results found for title: '" + releasedTitle + "'");
	}
	
	@Test(priority = 20, dependsOnMethods = { "submitSourceDependentBook" })
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