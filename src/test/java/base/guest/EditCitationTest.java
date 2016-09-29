package test.java.base.guest;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.ToolsPage;
import main.java.pages.tools.citation.CitationSearchName;
import main.java.pages.tools.citation.CitationStyleEditor;
import main.java.pages.tools.citation.StyleInfoPage;
import main.java.pages.tools.citation.VisualEditor;
import test.java.base.BaseTest;

public class EditCitationTest extends BaseTest {

	private String title = "IEEE";
	private String newTitle = "IEEE Test: " + getTimeStamp();
	
	private CitationSearchName csName;
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void openCitationEditor() {
		StartPage startPage = new StartPage(driver);
		ToolsPage toolsPage = startPage.goToToolsPage();
		saveCurrentHandle();
		
		CitationStyleEditor csEditor = toolsPage.goToCitationStyleEditor();
		csName = csEditor.searchStyle(title);
	}
	
	@Test(priority = 2)
	public void editStyle() {
		WebElement bestMatch = csName.getFirstResult();
		StyleInfoPage stylePage = csName.openStylePage(bestMatch);
		VisualEditor editor = stylePage.editStyle();
		editor.editStyle(newTitle);
		boolean canSaveStyle = editor.canSaveStyle();
		Assert.assertTrue(canSaveStyle, "Style was not accepted.");
	}
	
	@AfterClass
	public void tearDown() {
		backToBaseHandle();
	}
}
