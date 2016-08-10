package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import components.MainMenuComponent;
import components.MetaMenuComponent;
import components.SearchComponent;
import pages.homepages.CombinedHomePage;
import pages.homepages.DepositorHomePage;
import pages.homepages.HomePage;
import pages.homepages.ModeratorHomePage;
import pages.search.AdvancedSearchPage;
import pages.search.SearchResultsPage;
import pages.submission.ViewItemPage;

public abstract class BasePage {

	protected WebDriver driver;
	
	@FindBy(id = "contentSkipLinkAnchor")
	private WebElement headline;
	
	protected MetaMenuComponent metaMenuComponent;
	protected MainMenuComponent mainMenuComponent;
	protected SearchComponent searchComponent;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		
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
		return metaMenuComponent.goToLoginPage();
	}
	
	public SearchResultsPage quickSearch(String searchQuery) {
		return searchComponent.quickSearch(searchQuery);
	}
	
	public AdvancedSearchPage goToAdvancedSearchPage() {
		return searchComponent.goToAdvancedSearchPage();
	}
	
	public ViewItemPage openItemByTitle(String itemTitle) {
		SearchResultsPage searchResultsPage = searchComponent.quickSearch(itemTitle);
		ViewItemPage viewItemPage = searchResultsPage.openFirstResult();
		return viewItemPage;
	}
	
	public HomePage goToHomePage(HomePage homePage) {
		if (homePage instanceof DepositorHomePage)
			return new DepositorHomePage(driver);
		if (homePage instanceof ModeratorHomePage)
			return new ModeratorHomePage(driver);
		return new CombinedHomePage(driver);
	}
	
}
