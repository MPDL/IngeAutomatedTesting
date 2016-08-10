package pages.submission.transition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.EasySubmissionPage;
import pages.submission.FullSubmissionPage;

public class CollectionSelectionPage extends BasePage {
	
	public CollectionSelectionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public FullSubmissionPage fullSubmissionSimple() {
		WebElement simpleTestContext = driver.findElement(By.linkText("Test_Context_Simple"));
		simpleTestContext.click();
		
		return PageFactory.initElements(driver, FullSubmissionPage.class);
	}
	
	public EasySubmissionPage easySubmissionStandard() {
		WebElement standardTestContext = driver.findElement(By.linkText("PubMan Test Collection"));
		standardTestContext.click();
		
		return PageFactory.initElements(driver, EasySubmissionPage.class);
	}
}
