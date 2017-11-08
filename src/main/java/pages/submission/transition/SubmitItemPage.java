package main.java.pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;

public class SubmitItemPage extends BasePage {

	@FindBy(className = "inputTxtArea")
	private WebElement submitCommentBox;
	
	@FindBy(xpath = "//a[contains(@id, ':lnkSave')]")
	private WebElement submitItemButton;
	
	public SubmitItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage submitItem() {
		submitCommentBox.sendKeys("Testing item submission.");
		submitItemButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
