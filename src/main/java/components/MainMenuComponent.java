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
import main.java.pages.tools.ToolsPage;
import test.java.base.TestSuiteInitialisation;

/**
 * Menu containing item, workspace, and tool management
 * @author apetrova
 * 
 */
public class MainMenuComponent {

	protected WebDriver driver;
	
	@FindBy(id = "Header:lnkStartPage")
	private WebElement startPageLink;
	
	@FindBy(id = "Header:lnkDepWorkspace")
	private WebElement myItemsLink;
	
	@FindBy(id = "Header:lnkWorkspaces")
	private WebElement workspaceLink;
	
	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	@FindBy(id = "Header:lnkTools")
	private WebElement toolsLink;
	
	public MainMenuComponent(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver,  this);
	}
	
	public StartPage goToStartPage() {
		driver.navigate().to(TestSuiteInitialisation.getStartPageURL());
		
		return PageFactory.initElements(driver, StartPage.class);
	}
	
	public MyItemsPage goToItemsPage() {
		myItemsLink.click();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	public ImportWorkspacePage goToImportWorkspacePage() {
		workspaceLink.click();
		WebElement importWorkspaceLink = driver.findElement(By.xpath("//a[contains(@id, 'lnkImportWorkspace')]"));
		importWorkspaceLink.click();
		
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
	
	public QAWorkspacePage goToQAWorkspacePage() {
		workspaceLink.click();
		WebElement qaWorkspaceLink = driver.findElement(By.xpath("//a[contains(@id, 'lnkQAWorkspace')]"));
		qaWorkspaceLink.click();
		
		return PageFactory.initElements(driver, QAWorkspacePage.class);
	}
	
	// PM-17, (PM-18)
	public SubmissionPage goToSubmissionPage() {
		submissionLink.click();
		
		return PageFactory.initElements(driver, SubmissionPage.class);
	}
	
	public ToolsPage goToToolsPage() {
		toolsLink.click();
		
		return PageFactory.initElements(driver, ToolsPage.class);
	}
}
