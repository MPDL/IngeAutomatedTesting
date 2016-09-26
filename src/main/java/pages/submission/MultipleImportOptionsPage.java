package main.java.pages.submission;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class MultipleImportOptionsPage extends BasePage {

	@FindBy(id = "j_idt98:selRollback")
	private WebElement undoOnClickCheckbox;
	
	@FindBy(id = "j_idt98:selDuplicateStrategy:0")
	private WebElement doNotCheckDuplicateRadioButton;
	
	@FindBy(id = "j_idt98:inpMultipleImportName")
	private WebElement importNameBox;
	
	@FindBy(id = "j_idt98:lnkStartImport")
	private WebElement importButton;
	
	public MultipleImportOptionsPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ImportWorkspacePage setUploadOptions(String importName) {
		//undoOnClickCheckbox.clear();
		undoOnClickCheckbox.sendKeys(Keys.DELETE);
		doNotCheckDuplicateRadioButton.click();
		importNameBox.sendKeys(importName);
		
		importButton.click();
		return PageFactory.initElements(driver, ImportWorkspacePage.class);
	}
}
