package main.java.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.StartPage;
import main.java.pages.submission.ImportWorkspacePage;
import main.java.pages.submission.MyItemsPage;
import main.java.pages.submission.QAWorkspacePage;
import main.java.pages.submission.SubmissionPage;

/**
 * Menu containing item, workspace, and tool management
 * @author apetrova
 * 
 */
public class MainMenuComponent {

	protected WebDriver driver;
	
	@FindBy(id = "Header:lnkHome")
	private WebElement startPageLink;
	
	@FindBy(id = "Header:lnkDepWorkspace")
	private WebElement myItemsLink;
	
	@FindBy(id = "Header:lnkWorkspaces")
	private WebElement workspaceLink;
	
	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	public MainMenuComponent(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver,  this);
	}
	
	public StartPage goToStartPage() {
		startPageLink.click();
		
		return PageFactory.initElements(driver, StartPage.class);
	}
	
	public MyItemsPage goToItemsPage() {
		myItemsLink.click();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	public ImportWorkspacePage goToImportWorkspacePage() {
		workspaceLink.click();
		WebElement importWorkspaceLink = driver.findElement(By.id("j_idt108:lnkImportWorkspace"));
		importWorkspaceLink.click();
		
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
	
	public QAWorkspacePage goToQAWorkspacePage() {
		workspaceLink.click();
		WebElement qaWorkspaceLink = driver.findElement(By.id("j_idt108:lnkQAWorkspace"));
		qaWorkspaceLink.click();
		
		return PageFactory.initElements(driver, QAWorkspacePage.class);
	}
	
	public SubmissionPage goToSubmissionPage() {
		submissionLink.click();
		
		return PageFactory.initElements(driver, SubmissionPage.class);
	}
}
