package test.java.base.guest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.StartPage;
import main.java.pages.tools.RestExamplePage;
import test.java.base.BaseTest;

public class ExportRestExampleStandardTest extends BaseTest {

	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test
	public void exportRest() {
		StartPage startPage = new StartPage(driver);
		RestExamplePage restExamplePage = startPage.goToToolsPage().goToRestInterface();
		boolean itemsAreExported = restExamplePage.exportableItemAvailable();
		Assert.assertTrue(itemsAreExported, "Items were not exported");
	}
}
