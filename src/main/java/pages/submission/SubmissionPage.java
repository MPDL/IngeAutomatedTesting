package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.transition.CollectionSelectionPage;

public class SubmissionPage extends BasePage {
	
	@FindBy(xpath = "//a[contains(@id, 'lnkSubmission_lnkEasySubmission')]")
	private WebElement easySubmissionLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkNewSubmission')]")
	private WebElement fullSubmissionLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkSubmission_lnkImport')]")
	private WebElement fetchSubmissionLink;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkSubmission_lnkMultipleImport')]")
	private WebElement multipleImportLink;

	public SubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}

	// PM-18, PM-19
	public EasySubmissionPage goToEasySubmissionStandardPage() {
		easySubmissionLink.click();
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.easySubmissionStandard();
	}
	
	public EasySubmissionPage goToEasySubmissionSimplePage() {
		easySubmissionLink.click();
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.easySubmissionSimple();
	}
	
	// PM-22
	public FullSubmissionPage goToFullSubmissionStandardPage() {
		fullSubmissionLink.click();
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fullSubmissionStandard();
	}
	
	// PM-22
	public FullSubmissionPage goToFullSubmissionSimplePage() {
		fullSubmissionLink.click();
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fullSubmissionSimple();
	}
	
	public FetchSubmissionPage goToFetchSubmissionStandardPage() {
		fetchSubmissionLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fetchSubmissionStandard();
	}
	
	public MultipleImportPage goToMultipleImportStandardPage() {
		multipleImportLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.importSubmissionStandard();
	}

	// The depositor cannot choose between workflows, the moderator can.
	// PM-22
	public FullSubmissionPage depositorGoToFullSubmissionPage() {
		fullSubmissionLink.click();
		
		return PageFactory.initElements(driver, FullSubmissionPage.class);
	}
	
	// The depositor cannot choose between workflows, the moderator can.
	public EasySubmissionPage depositorGoToEasySubmissionPage() {
		easySubmissionLink.click();
		
		return PageFactory.initElements(driver, EasySubmissionPage.class);
	}
}
