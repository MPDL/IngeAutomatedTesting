package main.java.pages.submission.transition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
	
	// PM-130, PM-133
	public ViewItemPage submitItem() {
		submitCommentBox.sendKeys("Testing item submission.");
		submitItemButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='ITEM ACTIONS']")));
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
