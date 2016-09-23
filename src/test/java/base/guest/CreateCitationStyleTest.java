package test.java.base.guest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.CitationStyleEditor;
import test.java.base.BaseTest;

public class CreateCitationStyleTest extends BaseTest {

	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void createCitationStyle() {
		StartPage startPage = new StartPage(driver);
		CitationStyleEditor citationStyleEditor = startPage.goToToolsPage().goToCitationStyleEditor();
	}
}
