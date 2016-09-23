package main.java.pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.ViewItemPage;

public class FetchMetadataPage extends BasePage {

	@FindBy(id = "form1:j_idt545:0:inporganizations_organization_name")
	private WebElement organisationBox;
	
	@FindBy(id = "form1:j_idt497:0:inppersons_person_ous_optional")
	private WebElement orgNrBox;
	
	@FindBy(id = "form1:lnkSaveAndSubmit")
	private WebElement submitButton;
	
	public FetchMetadataPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage submit() {
		fillInAdditionalData();
		submitButton.click();
		
		return new SubmitItemPage(driver).submitItem();
	}
	
	private void fillInAdditionalData() {
		organisationBox.sendKeys("Institute for Theoretical Physics II ");
		orgNrBox.sendKeys("1");
	}
}
