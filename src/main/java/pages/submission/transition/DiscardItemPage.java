package main.java.pages.submission.transition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;

public class DiscardItemPage extends BasePage {

	@FindBy(xpath = "//a[contains(@id, ':lnkSave')]")
	private WebElement discardButton;
	
	@FindBy(className = "inputTxtArea")
	private WebElement discardTextBox;
	
	public DiscardItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage discardItem() {
		discardTextBox.sendKeys("Discarding for testing purposes.");
		discardButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='ITEM ACTIONS']")));
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
