package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class MultipleImportOptionsPage extends BasePage {

	@FindBy(xpath = "//input[contains(@id, 'selRollback')]")
	private WebElement undoErrorCheckbox;
	
	@FindBy(xpath = "//input[contains(@id, 'selDuplicateStrategy:0')]")
	private WebElement doNotCheckDuplicateRadioButton;
	
	@FindBy(xpath = "//input[contains(@id, 'inpMultipleImportName')]")
	private WebElement importNameBox;
	
	@FindBy(xpath = "//a[contains(@id, 'lnkStartImport')]")
	private WebElement importButton;
	
	public MultipleImportOptionsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ImportWorkspacePage setUploadOptions(String importName) {
		undoErrorCheckbox.click();
		doNotCheckDuplicateRadioButton.click();
		importNameBox.sendKeys(importName);
		
		importButton.click();
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
}
