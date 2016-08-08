package pages.submission;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class CollectionSelectionPage extends BasePage {
	
	public CollectionSelectionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public FullSubmissionPage fullSubmissionSimple() {
		WebElement simpleTestContent = driver.findElement(By.linkText("Test_Context_Simple"));
		simpleTestContent.click();
		
		return PageFactory.initElements(driver, FullSubmissionPage.class);
	}
}
