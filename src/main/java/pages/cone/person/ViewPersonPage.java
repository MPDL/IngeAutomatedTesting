package main.java.pages.cone.person;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.cone.ConeBasePage;

public class ViewPersonPage extends ConeBasePage {

	@FindBy(className = "sub")
	private WebElement editButton;
	
	public ViewPersonPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public EditPersonPage editPerson() {
		editButton.click();
		
		return PageFactory.initElements(driver, EditPersonPage.class);
	}
}
