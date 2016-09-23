package main.java.pages.cone.person;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.cone.ConeBasePage;

public class EditPersonPage extends ConeBasePage {

	@FindBy(className = "cancelButton")
	private WebElement deleteLink;
	
	public EditPersonPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public NewPersonPage deletePerson() {
		deleteLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, NewPersonPage.class);
	}
}
