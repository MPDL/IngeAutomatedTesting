package main.java.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.pages.search.AdvancedSearchPage;
import main.java.pages.search.BrowsePage;
import main.java.pages.search.SearchResultsPage;

public class SearchComponent {

	protected WebDriver driver;
	private WebDriverWait wait;
	
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
		
		wait = new WebDriverWait(driver, 20);
		
		PageFactory.initElements(driver, this);
	}
	
	public AdvancedSearchPage goToAdvancedSearchPage() {
		advancedSearchButton.click();
		
		return PageFactory.initElements(driver, AdvancedSearchPage.class);
	}
	
	// PM-102
	public SearchResultsPage quickSearch(String searchQuery) {
		quickSearchBox.sendKeys(searchQuery);
		includeFilesCheckBox.click();		
		quickSearchButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Search Results')]")));
		
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	public BrowsePage goToBrowsePage() {
		browseLink.click();
		
		return PageFactory.initElements(driver, BrowsePage.class);
	}
}
