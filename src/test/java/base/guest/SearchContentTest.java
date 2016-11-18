package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.search.SearchResultsPage;
import test.java.base.BaseTest;

public class SearchContentTest extends BaseTest {

	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void searchContent() {
		StartPage startPage = new StartPage(driver);
		SearchResultsPage searchResults = startPage.quickSearch("dskfidndfkisdfnidnfidfkdnjkf");
		
		int resultCount = searchResults.getResultCount();
		Assert.assertEquals(resultCount, 1, resultCount == 0 ? "Matching result is not displayed." : "Non-matching results were displayed.");
	}
}
