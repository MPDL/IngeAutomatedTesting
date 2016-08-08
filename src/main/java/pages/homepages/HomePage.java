package pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.StartPage;
import pages.search.AdministrativeSearchPage;
import pages.submission.SubmissionPage;

public class HomePage extends BasePage {

	@FindBy(id = "Header:j_idt39:lnkLoginLogout")
	private WebElement logoutLink;
	
	@FindBy(id = "Header:j_idt39:lnkAccountUserName")
	private WebElement loggedInUsername;
	
	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	@FindBy(id = "Header:j_idt67:lnkAdminSearch")
	private WebElement administrativeSearchLink;
	
	public HomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public StartPage logout() {
		logoutLink.click();
		return PageFactory.initElements(driver, StartPage.class);
	}
	
	public String getUsername() {
		return loggedInUsername.getText();
	}
	
	public SubmissionPage goToSubmissionPage() {
		submissionLink.click();
		return PageFactory.initElements(driver, SubmissionPage.class);
	}
	
	public AdministrativeSearchPage goToAdministrativeSearchPage() {
		administrativeSearchLink.click();
		return PageFactory.initElements(driver, AdministrativeSearchPage.class);
	}
}
