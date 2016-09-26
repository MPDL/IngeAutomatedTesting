package main.java.pages.tools.cone.person;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.tools.cone.ConeBasePage;

public class ViewPersonPage extends ConeBasePage {

	@FindBy(className = "sub")
	private WebElement editButton;
	
	@FindBy(css = ".itemLine:nth-of-type(2) .xDouble_area0")
	private WebElement completeName;
	
	@FindBy(css = ".itemLine:nth-of-type(3) .xDouble_area0")
	private WebElement familyName;
	
	public ViewPersonPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public EditPersonPage editPerson() {
		editButton.click();
		
		return PageFactory.initElements(driver, EditPersonPage.class);
	}
	
	public String getCompleteName() {
		return completeName.getText();
	}
	
	public String getFamilyName() {
		return familyName.getText();
	}
}
