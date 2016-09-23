package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import test.java.base.ItemStatus;
import main.java.pages.BasePage;
import main.java.pages.submission.transition.AcceptItemPage;
import main.java.pages.submission.transition.DiscardItemPage;
import main.java.pages.submission.transition.FinaliseSubmissionPage;
import main.java.pages.submission.transition.ReworkItemPage;
import main.java.pages.submission.transition.SubmitItemPage;

public class ViewItemPage extends BasePage {

	@FindBy(css = ".itemHeadline>b")
	private WebElement itemTitle;
	
	@FindBy(className = "statusIcon")
	private WebElement itemStatus;
	
	@FindBy(id = "j_idt107:lnkRelease")
	private WebElement releaseItemLink;
	
	@FindBy(id = "j_idt107:lnkSubmit")
	private WebElement submitItemLink;
	
	@FindBy(id = "j_idt107:lnkAccept")
	private WebElement acceptItemLink;
	
	@FindBy(id = "j_idt107:lnkEdit")
	private WebElement editItemLink;
	
	@FindBy(id = "j_idt107:lnkRevise")
	private WebElement reviseItemLink;
	
	@FindBy(id = "j_idt107:lnkModify")
	private WebElement modifyItemLink;
	
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
	
	public ItemStatus getItemStatus() {
		String classNames = itemStatus.getAttribute("class");
		if (classNames.contains("pendingItem"))
			return ItemStatus.PENDING;
		if (classNames.contains("submittedItem"))
			return ItemStatus.SUBMITTED;
		if (classNames.contains("releasedItem"))
			return ItemStatus.RELEASED;
		if (classNames.contains("inRevisionItem"))
			return ItemStatus.INREWORK;
		return ItemStatus.DISCARDED;
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
	
	public ViewItemPage discardItem() {
		discardItemLink.click();
		
		return new DiscardItemPage(driver).discardItem();
	}
	
	public ViewItemPage acceptItem() {
		acceptItemLink.click();
		
		return new AcceptItemPage(driver).acceptItem();
	}
	
	public ViewItemPage editItem() {
		editItemLink.click();
		
		return new EditItemPage(driver).editItem();
	}
	
	public ViewItemPage sendBackForRework() {
		reviseItemLink.click();
		
		return new ReworkItemPage(driver).sendBackForRework();
	}
	
	public ViewItemPage modifyItem() {
		modifyItemLink.click();
		
		return new EditItemPage(driver).modifyItem();
	}
	
}
