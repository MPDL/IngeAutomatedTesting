package main.java.pages.submission.transition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;

public class FinaliseSubmissionPage extends BasePage {

	@FindBy(tagName = "textarea")
	private WebElement confirmationTextArea;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkSave')]")
	private WebElement releaseSubmissionButton;
	
	public FinaliseSubmissionPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	// PM-60, PM-134, PM-135
	public ViewItemPage releaseSubmission() {
		confirmationTextArea.sendKeys("Testing release.");
		releaseSubmissionButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='ITEM ACTIONS']")));
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
