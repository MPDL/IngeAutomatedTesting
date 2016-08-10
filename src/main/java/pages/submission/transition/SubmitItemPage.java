package pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.ViewItemPage;

public class SubmitItemPage extends BasePage {

	@FindBy(name = "j_idt98:j_idt146")
	private WebElement submitCommentBox;
	
	@FindBy(id = "j_idt98:lnkSave")
	private WebElement submitItemButton;
	
	public SubmitItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage submitItem() {
		submitCommentBox.sendKeys("Testing item submission.");
		submitItemButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
