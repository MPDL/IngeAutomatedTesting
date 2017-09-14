package main.java.pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.submission.ImportWorkspacePage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.SubmissionPage;

public class DepositorHomePage extends HomePage {
	
	@FindBy(id = "Header:lnkDepWorkspace")
	private WebElement myItemsLink;

	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	public DepositorHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public MyItemsPage goToMyItemsPage() {
		myItemsLink.click();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	public SubmissionPage goToSubmissionPage() {
		submissionLink.click();
		return PageFactory.initElements(driver, SubmissionPage.class);
	}
	
	public ImportWorkspacePage goToImportWorkspace() {
		return mainMenuComponent.goToImportWorkspacePage();
	}
}
