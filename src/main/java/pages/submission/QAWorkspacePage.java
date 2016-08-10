package pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class QAWorkspacePage extends BasePage {

	public QAWorkspacePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage openItemByTitle(String itemTitle) {
		WebElement itemLink = driver.findElement(By.cssSelector("a[title='" + itemTitle + "']"));
		itemLink.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
