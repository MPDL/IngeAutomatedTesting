package main.java.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.components.MainMenuComponent;
import main.java.components.MetaMenuComponent;
import main.java.components.SearchComponent;
import main.java.pages.homepages.CombinedHomePage;
import main.java.pages.homepages.DepositorHomePage;
import main.java.pages.homepages.HomePage;
import main.java.pages.homepages.ModeratorHomePage;
import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.SearchResultsPage;
import main.java.pages.submission.ViewItemPage;
import main.java.pages.tools.ToolsPage;

/**
 * Components and actions common to every QA-PubMan page.
 * @author apetrova
 *
 */
public abstract class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(id = "contentSkipLinkAnchor")
	private WebElement headline;
	
	protected MetaMenuComponent metaMenuComponent;
	protected MainMenuComponent mainMenuComponent;
	protected SearchComponent searchComponent;
	
	private final int EXPLICIT_TIMEOUT = 200;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
		
		metaMenuComponent = new MetaMenuComponent(driver);
		mainMenuComponent = new MainMenuComponent(driver);
		searchComponent = new SearchComponent(driver);
	}
	
	public String getHeadline() {
		return headline.getText();
	}
	
	public StartPage goToStartPage() {
		return mainMenuComponent.goToStartPage();
	}
	
	public LoginPage goToLoginPage() {
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public SearchResultsPage quickSearch(String searchQuery) {
		return searchComponent.quickSearch(searchQuery);
	}
	
	public ToolsPage goToToolsPage() {
		return mainMenuComponent.goToToolsPage();
	}
	
	public AdvancedSearchPage goToAdvancedSearchPage() {
		return searchComponent.goToAdvancedSearchPage();
	}
	
	public ViewItemPage openSubmittedItemByTitle(String itemTitle) {
		SearchResultsPage searchResultsPage = searchComponent.quickSearch(itemTitle);
		ViewItemPage viewItemPage = searchResultsPage.openFirstResult();
		return viewItemPage;
	}
	
	public HomePage goToHomePage(HomePage homePage) {
		goToStartPage();
		if (homePage instanceof DepositorHomePage)
			return new DepositorHomePage(driver);
		if (homePage instanceof ModeratorHomePage)
			return new ModeratorHomePage(driver);
		return new CombinedHomePage(driver);
	}
	
	public Object openLinkNewWindow(WebElement link, Object PageType) {
		String firstHandle = driver.getWindowHandle();
		link.click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles.remove(firstHandle);
		driver.switchTo().window(windowHandles.iterator().next());
		
		return PageFactory.initElements(driver, PageType.getClass());
	}
	
}
