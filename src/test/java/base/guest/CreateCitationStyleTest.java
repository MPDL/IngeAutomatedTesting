package test.java.base.guest;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.citation.CitationSearchExample;
import main.java.pages.tools.citation.CitationStyleEditor;
import main.java.pages.tools.citation.VisualEditor;
import test.java.base.BaseTest;

public class CreateCitationStyleTest extends BaseTest {

	private CitationStyleEditor csEditor;
	private CitationSearchExample searchExample;
	
	private List<WebElement> searchResults;
	
	private String expected = "Find and edit CSL citation styles";
	private String title = "New citation style: " + getTimeStamp();
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void openCSEditor() {
		StartPage startPage = new StartPage(driver);
		saveCurrentHandle();
		csEditor = startPage.goToToolsPage().goToCitationStyleEditor();
		
		String actual = csEditor.getHeadline().trim();
		Assert.assertEquals(expected, actual, "Headlines do not match.");
	}
	
	@Test(priority = 2)
	public void searchByExample() {
		searchExample = csEditor.searchByExample();
		searchResults = searchExample.getSearchResults();
		Assert.assertTrue(searchResults.size() > 0, "No results were displayed.");
	}
	
	@Test(priority = 3)
	public void createCitationStyle() {
		VisualEditor editor = searchExample.editFirstResult();
		editor.createNewStyle(title);
		boolean canSaveStyle = editor.canSaveStyle();
		Assert.assertTrue(canSaveStyle, "Save button is not enabled.");
		
		backToBaseHandle();
	}
}
