package main.java.pages.cone.journal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.cone.ConeBasePage;

public class EditJournalPage extends ConeBasePage {

	@FindBy(name = "http___purl_org_dc_elements_1_1_title")
	private WebElement titleBox;
	
	@FindBy(name = "save")
	private WebElement saveButton;
	
	@FindBy(className = "cancelButton")
	private WebElement deleteButton;
	
	public EditJournalPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewJournalPage changeTitle(String newTitle) {
		titleBox.sendKeys(newTitle);
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewJournalPage.class);
	}
	
	public NewJournalPage deleteJournal() {
		deleteButton.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, NewJournalPage.class);
	}
}
