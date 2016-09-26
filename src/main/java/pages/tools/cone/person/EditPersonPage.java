package main.java.pages.tools.cone.person;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.tools.cone.ConeBasePage;

public class EditPersonPage extends ConeBasePage {

	@FindBy(name = "http___purl_org_dc_elements_1_1_title")
	private WebElement completeNameBox;
	
	@FindBy(className = "cancelButton")
	private WebElement deleteLink;
	
	@FindBy(className = "activeButton")
	private WebElement saveButton;
	
	public EditPersonPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public NewPersonPage deletePerson() {
		deleteLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, NewPersonPage.class);
	}
	
	public ViewPersonPage changeName(String newName) {
		completeNameBox.clear();
		completeNameBox.sendKeys(newName);
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewPersonPage.class);
	}
}
