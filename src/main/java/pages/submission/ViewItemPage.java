package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;
import pages.submission.transition.AcceptItemPage;
import pages.submission.transition.DiscardItemPage;
import pages.submission.transition.FinaliseSubmissionPage;
import pages.submission.transition.SubmitItemPage;

public class ViewItemPage extends BasePage {

	@FindBy(css = ".itemHeadline>b")
	private WebElement itemTitle;
	
	@FindBy(className = "statusLabel")
	private WebElement itemStatus;
	
	@FindBy(id = "j_idt107:lnkRelease")
	private WebElement releaseItemLink;
	
	@FindBy(id = "j_idt107:lnkSubmit")
	private WebElement submitItemLink;
	
	@FindBy(id = "j_idt107:lnkAccept")
	private WebElement acceptItemLink;
	
	@FindBy(id = "j_idt107:lnkDelete")
	private WebElement deleteItemLink;
	
	@FindBy(id = "j_idt107:lnkWithdraw")
	private WebElement discardItemLink;
	
	public ViewItemPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public String getItemTitle() {
		return itemTitle.getText();
	}
	
	public String getItemStatus() {
		return itemStatus.getText();
	}
	
	public ViewItemPage releaseItem() {
		releaseItemLink.click();
		
		return new FinaliseSubmissionPage(driver).releaseSubmission();
	}
	
	public ViewItemPage submitItem() {
		submitItemLink.click();
		
		return new SubmitItemPage(driver).submitItem();
	}
	
	public MyItemsPage deleteItem() {
		deleteItemLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	// TODO fix to adhere with POM standards
	public ViewItemPage discardItem() {
		discardItemLink.click();
		
		return new DiscardItemPage(driver).discardItem();
	}
	
	public ViewItemPage acceptItem() {
		acceptItemLink.click();
		
		return new AcceptItemPage(driver).acceptItem();
	}
	
}
