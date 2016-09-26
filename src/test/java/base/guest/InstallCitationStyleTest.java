package test.java.base.guest;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.LoginPage;
import main.java.pages.StartPage;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.tools.citation.CitationSearchName;
import main.java.pages.tools.citation.CitationStyleEditor;
import test.java.base.BaseTest;

public class InstallCitationStyleTest extends BaseTest {

	private CombinedHomePage combinedHomePage;
	private CitationStyleEditor csEditor;
	
	private String styleName = "IEEE";
	
	@BeforeClass
	public void setup() {
		super.setup();
	}
	
	@Test(priority = 1)
	public void loginCombined() {
		LoginPage loginPage = new StartPage(driver).goToLoginPage();
		combinedHomePage = loginPage.loginAsCombinedUser(modDepUsername, modDepPassword);
	}
	
	@Test(priority = 2)
	public void searchCSName() {
		csEditor = combinedHomePage.goToToolsPage().goToCitationStyleEditor();
		CitationSearchName searchName = csEditor.searchStyle(styleName);
		WebElement firstResult = searchName.getFirstResult();
		searchName.openStylePage(firstResult).installStyle();
	}
}
