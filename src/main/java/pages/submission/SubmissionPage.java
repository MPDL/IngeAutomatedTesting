package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class SubmissionPage extends BasePage {
	
	@FindBy(id = "j_idt98:lnkNewSubmission")
	private WebElement fullSubmissionLink;
	
	@FindBy(id = "j_idt98:lnkSubmission_lnkMultipleImport")
	private WebElement multipleImportLink;

	public SubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public FullSubmissionPage goToFullSubmissionPage() {
		fullSubmissionLink.click();
		
		CollectionSelectionPage collectionSelectionPage = PageFactory.initElements(driver, CollectionSelectionPage.class);
		
		return collectionSelectionPage.fullSubmissionSimple();
	}
	
	public MultipleImportPage goToMultipleImportPage() {
		multipleImportLink.click();
		
		return PageFactory.initElements(driver, MultipleImportPage.class);
	}
}
