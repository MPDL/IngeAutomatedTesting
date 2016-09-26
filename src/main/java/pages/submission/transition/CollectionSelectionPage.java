package main.java.pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.EasySubmissionPage;
import main.java.pages.submission.FetchSubmissionPage;
import main.java.pages.submission.FullSubmissionPage;
import main.java.pages.submission.MultipleImportPage;

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
	
	public FetchSubmissionPage fetchSubmissionStandard() {
		standardTestContext.click();
		
		return PageFactory.initElements(driver, FetchSubmissionPage.class);
	}
	
	public MultipleImportPage importSubmissionStandard() {
		standardTestContext.click();
			
		return PageFactory.initElements(driver, MultipleImportPage.class);
	}
}
