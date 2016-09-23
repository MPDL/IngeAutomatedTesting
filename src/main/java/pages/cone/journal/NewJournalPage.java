package main.java.pages.cone.journal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.cone.ConeBasePage;

public class NewJournalPage extends ConeBasePage {

	@FindBy(id = "cone_identifier")
	private WebElement coneID;
	
	@FindBy(name = "http___purl_org_dc_elements_1_1_title")
	private WebElement titleBox;
	
	@FindBy(name = "save")
	private WebElement saveButton;
	
	public NewJournalPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewJournalPage createNewJournal(String title) {
		titleBox.sendKeys(title);
		coneID.sendKeys(title.toLowerCase().replace(' ', '-'));
		
		saveButton.click();
		return PageFactory.initElements(driver, ViewJournalPage.class);
	}
}
