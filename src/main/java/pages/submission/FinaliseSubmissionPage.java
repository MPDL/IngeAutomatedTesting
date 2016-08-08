package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class FinaliseSubmissionPage extends BasePage {

	@FindBy(id = "j_idt98:lnkRelease")
	private WebElement releaseSubmissionButton;
	
	public FinaliseSubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage releaseSubmission() {
		releaseSubmissionButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
