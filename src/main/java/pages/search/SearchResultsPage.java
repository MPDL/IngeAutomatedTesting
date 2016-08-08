package pages.search;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.ViewItemPage;

public class SearchResultsPage extends BasePage {

	private List<WebElement> searchResults;
	
	public SearchResultsPage(WebDriver driver) {
		super(driver);
		
		searchResults = driver.findElements(By.className("listItem"));
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage openFirstResult() {
		WebElement titleLink = searchResults.get(0).findElement(By.className("itemHeadline"));
		titleLink.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
