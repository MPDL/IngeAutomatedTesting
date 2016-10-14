package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.ToolsPage;
import main.java.pages.tools.rest.RestExamplePage;
import test.java.base.BaseTest;

public class ExportRestExampleStandardTest extends BaseTest {

	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void exportRest() {
		StartPage startPage = new StartPage(driver);
		ToolsPage toolsPage = startPage.goToToolsPage();
		saveCurrentHandle();
		
		RestExamplePage restExamplePage = toolsPage.goToRestInterface();
		boolean downloadPossible = restExamplePage.exportDownloadPossible();
		Assert.assertTrue(downloadPossible, "Search URI and feeds fields were not displayed.");
	}
	
	@AfterClass
	public void tearDown() {
		backToBaseHandle();
	}
}
