package main.java.pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.submission.ImportWorkspacePage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.SubmissionPage;
import main.java.pages.workspaces.BatchWorkspaceViewPage;

public class CombinedHomePage extends HomePage {

	@FindBy(id = "Header:lnkDepWorkspace")
	private WebElement myItemsLink;
	
	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	@FindBy(id = "Header:lnkWorkspaces")
	private WebElement workspaceLink;
	
	public CombinedHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public MyItemsPage goToMyItemsPage() {
		return mainMenuComponent.goToItemsPage();
	}
	
	// PM-17, (PM-18)
	public SubmissionPage goToSubmissionPage() {
		return mainMenuComponent.goToSubmissionPage();
	}
	
	public ImportWorkspacePage goToImportWorkspace() {
		return mainMenuComponent.goToImportWorkspacePage();
	}
	
	public BatchWorkspaceViewPage goToBatchProcessingWorkspace() {
		return mainMenuComponent.goToBatchProcessingPage();
	}
}
