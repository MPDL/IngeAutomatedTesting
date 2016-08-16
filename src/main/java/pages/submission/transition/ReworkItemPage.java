package pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.ViewItemPage;

public class ReworkItemPage extends BasePage {

	@FindBy(name = "j_idt98:j_idt130")
	private WebElement reworkCommentBox;
	
	@FindBy(id = "j_idt98:lnkSave")
	private WebElement saveButton;
	
	public ReworkItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage sendBackForRework() {
		reworkCommentBox.sendKeys("Testing item revision.");
		saveButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
