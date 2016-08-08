package pages.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class AdvancedSearchPage extends BasePage {

	@FindBy(id = "form1:criterions:0:anyFieldSearchStringFirst")
	protected WebElement titleTextBox;
	
	@FindBy(id = "form1:criterions:2:personSearchString")
	protected WebElement personTextBox;
	
	@FindBy(id = "form1:criterions:4:organizationSearchTermEmptyFirst")
	protected WebElement organizaionTestBox;
	
	@FindBy(id = "form1:lnkAdvancedSearchStartSearch")
	protected WebElement startSearchButton;
	
	public AdvancedSearchPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
	
	public SearchResultsPage advancedSearch(String searchQuery) {
		fillInData(searchQuery);
		startSearchButton.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	private void fillInData(String title) {
		titleTextBox.sendKeys(title);
	}
}
