package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.transition.CollectionSelectionPage;

public class SubmissionPage extends BasePage {
	
	@FindBy(id = "j_idt98:lnkSubmission_lnkEasySubmission")
	private WebElement easySubmissionLink;
	
	@FindBy(id = "j_idt98:lnkNewSubmission")
	private WebElement fullSubmissionLink;
	
	@FindBy(id = "j_idt98:lnkSubmission_lnkMultipleImport")
	private WebElement multipleImportLink;

	public SubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public EasySubmissionPage goToEasySubmissionStandardPage() {
		easySubmissionLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.easySubmissionStandard();
	}
	
	public FullSubmissionPage goToFullSubmissionStandardPage() {
		fullSubmissionLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fullSubmissionStandard();
	}
	
	public FullSubmissionPage goToFullSubmissionSimplePage() {
		fullSubmissionLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fullSubmissionSimple();
	}
	
	public MultipleImportPage goToMultipleImportPage() {
		multipleImportLink.click();
		
		return PageFactory.initElements(driver, MultipleImportPage.class);
	}
}
