package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.search.SearchResultsPage;

public class MyItemsPage extends BasePage {

	public MyItemsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
	
	public ViewItemPage openItemByTitle(String itemTitle) {
		SearchResultsPage searchResultsPage = searchComponent.quickSearch(itemTitle);
		ViewItemPage viewItemPage = searchResultsPage.openFirstResult();
		return viewItemPage;
	}
	
	public MyItemsPage deleteItemByTitle(String itemTitle) {
		ViewItemPage viewItemPage = openItemByTitle(itemTitle);
		return viewItemPage.deleteItem();
	}
	
	public ViewItemPage discardItemByTitle(String itemTitle) {
		ViewItemPage viewItemPage = openItemByTitle(itemTitle);
		return viewItemPage.discardItem();
	}
}
