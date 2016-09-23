package main.java.pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.submission.SubmissionPage;

public class CombinedHomePage extends HomePage {

	@FindBy(id = "Header:lnkSubmission")
	private WebElement submissionLink;
	
	public CombinedHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public SubmissionPage goToSubmissionPage() {
		submissionLink.click();
		return PageFactory.initElements(driver, SubmissionPage.class);
	}
}
