package main.java.pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class MyItemsPage extends BasePage {

	public MyItemsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
	
	public ViewItemPage openItemByTitle(String itemTitle) {
		WebElement itemLink = driver.findElement(By.cssSelector("a[title='" + itemTitle + "']"));
		itemLink.click();
			
		return PageFactory.initElements(driver, ViewItemPage.class);
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
