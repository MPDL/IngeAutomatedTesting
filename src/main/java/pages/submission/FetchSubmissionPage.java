package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.transition.FetchMetadataPage;

public class FetchSubmissionPage extends BasePage {

	@FindBy(id = "formFetchMd:easySubmission:Import:inpServiceID")
	private WebElement identifierBox;
	
	@FindBy(id = "formFetchMd:easySubmission:Import:lnkImportFetch")
	private WebElement fetchButton;
	
	public FetchSubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage fetchSubmission(String identifier) {
		identifierBox.sendKeys(identifier);
		fetchButton.click();
		return new FetchMetadataPage(driver).submit();
	}
}
