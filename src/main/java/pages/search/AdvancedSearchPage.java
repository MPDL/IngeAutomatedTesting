package main.java.pages.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class AdvancedSearchPage extends BasePage {

	@FindBy(id = "form1:criterions:0:anyFieldSearchStringFirst")
	protected WebElement titleTextBox;
	
	@FindBy(id = "form1:criterions:2:personSearchString")
	protected WebElement personTextBox;
	
	@FindBy(id = "form1:criterions:4:organizationSearchTermEmptyFirst")
	protected WebElement organisationTextBox;
	
	@FindBy(id = "form1:criterions:6:startDateFirst")
	protected WebElement dateFrom;
	
	@FindBy(id = "form1:criterions:6:endDateFirst")
	protected WebElement dateTo;
	
	@FindBy(id = "form1:lnkAdvancedSearchStartSearch")
	protected WebElement startSearchButton;
	
	public AdvancedSearchPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
	
	public SearchResultsPage advancedSearch(String title, String author, String organisation) {
		fillInTitle(title);
		fillInAuthor(author);
		fillInOrganisation(organisation);
		startSearchButton.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	private void fillInTitle(String title) {
		if(title != null) {
			titleTextBox.sendKeys(title);
		}		
	}
	
	private void fillInAuthor(String author) {
		if(author != null) {
			personTextBox.sendKeys(author);	
		}		
	}
	
	private void fillInOrganisation(String organisation) {
		if(organisation != null) {
			organisationTextBox.sendKeys(organisation);
		}		
	}
}
