package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.search.AdvancedSearchPage;
import pages.search.BrowsePage;
import pages.search.SearchResultsPage;

public class SearchComponent {

	protected WebDriver driver;
	
	@FindBy(xpath = "//input[contains(@id, 'quickSearchString')]")
	private WebElement quickSearchBox;
	
	@FindBy(xpath = "//input[contains(@id, 'quickSearchCheckBox')]")
	private WebElement includeFilesCheckBox;
	
	@FindBy(xpath = "//input[contains(@id, 'btnQuickSearchStart')]")
	private WebElement quickSearchButton;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkAdvancedSearch')]")
	private WebElement advancedSearchButton;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkBrowseBy')]")
	private WebElement browseLink;
	
	public SearchComponent(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public AdvancedSearchPage goToAdvancedSearchPage() {
		advancedSearchButton.click();
		
		return PageFactory.initElements(driver, AdvancedSearchPage.class);
	}
	
	public SearchResultsPage quickSearch(String searchQuery) {
		quickSearchBox.sendKeys(searchQuery);
		includeFilesCheckBox.click();
		quickSearchButton.click();
		
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	public BrowsePage goToBrowsePage() {
		browseLink.click();
		
		return PageFactory.initElements(driver, BrowsePage.class);
	}
}
