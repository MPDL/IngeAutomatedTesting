package main.java.pages.tools.cone.person;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.tools.cone.ConeBasePage;

public class NewPersonPage extends ConeBasePage {

	@FindBy(name = "http___purl_org_dc_elements_1_1_title")
	private WebElement completeNameBox;
	
	@FindBy(name = "http___xmlns_com_foaf_0_1_family_name")
	private WebElement familyNameBox;
	
	@FindBy(className = "activeButton")
	private WebElement submitButton;
	
	public NewPersonPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewPersonPage createNewAuthor(String completeName, String familyName) {
		completeNameBox.sendKeys(completeName);
		familyNameBox.sendKeys(familyName);
		submitButton.click();
		
		return PageFactory.initElements(driver, ViewPersonPage.class);
	}
}
