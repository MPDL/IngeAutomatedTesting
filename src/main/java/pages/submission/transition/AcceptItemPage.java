package pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.ViewItemPage;

public class AcceptItemPage extends BasePage {

	@FindBy(id = "j_idt98:acceptanceComment")
	private WebElement acceptanceCommentBox;
	
	@FindBy(id = "j_idt98:lnkSave")
	private WebElement acceptButton;
	
	public AcceptItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage acceptItem() {
		acceptanceCommentBox.sendKeys("Testing item acceptance");
		acceptButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
