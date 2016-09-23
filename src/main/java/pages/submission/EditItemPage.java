package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;
import main.java.pages.submission.transition.AcceptItemPage;

public class EditItemPage extends BasePage {

	@FindBy(id = "form1:fileUploads:0:inpExtraFileDescription")
	private WebElement descriptionBox;
	
	@FindBy(id = "form1:lnkSave")
	private WebElement saveButton;
	
	@FindBy(id = "form1:lnkAccept")
	private WebElement acceptButton;
	
	public EditItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage editItem() {
		editDescription(" (revised by moderator)");
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
	
	public ViewItemPage modifyItem() {
		editDescription(" (modified by moderator)");
		acceptButton.click();
		
		return new AcceptItemPage(driver).acceptItem();
	}
	
	private void editDescription(String descriptionEdit) {
		descriptionBox.sendKeys(descriptionEdit);
	}
	
	
}
