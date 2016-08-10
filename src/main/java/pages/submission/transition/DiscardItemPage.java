package pages.submission.transition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.ViewItemPage;

public class DiscardItemPage extends BasePage {

	@FindBy(id = "j_idt98:lnkSave")
	private WebElement discardButton;
	
	@FindBy(name = "j_idt98:j_idt140")
	private WebElement discardTextBox;
	
	public DiscardItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public ViewItemPage discardItem() {
		discardTextBox.sendKeys("Discarding for testing purposes.");
		discardButton.click();
		
		return PageFactory.initElements(driver, ViewItemPage.class);
	}
}
