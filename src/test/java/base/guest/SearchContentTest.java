package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseTest;

/**
 * Small Quick Search Test without being logged in. <br>
 * 
 * Assumes at least one item matching each search query is present
 * 
 * @author helk
 *
 */
public class SearchContentTest extends BaseTest {

	private String quickSearchQuery = "Test";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	//TODO: Before tests: Create matching (Submission) items to search for, if not already existing.
	
	@Test
	public void searchContent() {
		StartPage startPage = new StartPage(driver);
		SearchResultsPage searchResults = startPage.quickSearch(quickSearchQuery);
		
		int resultCount = searchResults.getResultCount();
		Assert.assertNotEquals(resultCount, 0, "At least one result should be present for term '" + quickSearchQuery + "'.");
	}
}
