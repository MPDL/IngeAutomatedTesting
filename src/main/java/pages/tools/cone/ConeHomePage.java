package main.java.pages.tools.cone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.tools.cone.journal.NewJournalPage;
import main.java.pages.tools.cone.person.NewPersonPage;

/**
 * Homepage for registered users with the rights
 * 	to create, edit and import entities
 * @author apetrova
 *
 */
public class ConeHomePage extends ConeBasePage {

	@FindBy(css = "#metaMenuSkipLinkAnchor a:nth-of-type(2)")
	private WebElement logoutLink;
	
	@FindBy(css = "#mainMenuSkipLinkAnchor a:nth-of-type(3)")
	private WebElement enterNewEntityLink;
	
	public ConeHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public NewPersonPage enterNewPerson() {
		enterNewEntityLink.click();
		driver.findElement(By.linkText("persons")).click();
		
		return PageFactory.initElements(driver, NewPersonPage.class);
	}
	
	public NewJournalPage enterNewJournal() {
		enterNewEntityLink.click();
		driver.findElement(By.linkText("journals")).click();
		
		return PageFactory.initElements(driver, NewJournalPage.class);
	}
	
	public ConeBasePage logout() {
		logoutLink.click();
		
		return PageFactory.initElements(driver, ConeBasePage.class);
	}
	
	
}
