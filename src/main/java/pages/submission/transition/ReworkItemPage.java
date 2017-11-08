package main.java.pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;

public class ReworkItemPage extends BasePage {

	@FindBy(className = "inputTxtArea")
	private WebElement reworkCommentBox;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkSave')]")
	private WebElement saveButton;
	
	public ReworkItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage sendBackForRework() {
		reworkCommentBox.sendKeys("Testing item revision.");
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
