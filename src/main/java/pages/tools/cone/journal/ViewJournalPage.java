package main.java.pages.tools.cone.journal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.tools.cone.ConeBasePage;

public class ViewJournalPage extends ConeBasePage {

	@FindBy(className = "sub")
	private WebElement editButton;
	
	@FindBy(className = "singleItem")
	private WebElement title;
	
	public ViewJournalPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public EditJournalPage editJournal() {
		editButton.click();
		
		return PageFactory.initElements(driver, EditJournalPage.class);
	}
	
	public String getTitle() {
		return title.getText();
	}
}
