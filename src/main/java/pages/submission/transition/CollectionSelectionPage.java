package pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.EasySubmissionPage;
import pages.submission.FullSubmissionPage;

public class CollectionSelectionPage extends BasePage {
	
	@FindBy(linkText = "Test_Context_Simple")
	private WebElement simpleTestContext;
	
	@FindBy(linkText = "PubMan Test Collection")
	private WebElement standardTestContext;
	
	public CollectionSelectionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public FullSubmissionPage fullSubmissionSimple() {
		simpleTestContext.click();
		
		return PageFactory.initElements(driver, FullSubmissionPage.class);
	}
	
	public FullSubmissionPage fullSubmissionStandard() {
		standardTestContext.click();
		
		return PageFactory.initElements(driver, FullSubmissionPage.class);
	}
	
	public EasySubmissionPage easySubmissionStandard() {
		standardTestContext.click();
		
		return PageFactory.initElements(driver, EasySubmissionPage.class);
	}
}