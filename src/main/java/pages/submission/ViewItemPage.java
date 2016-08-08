package pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class ViewItemPage extends BasePage {

	@FindBy(css = ".itemHeadline>b")
	private WebElement itemTitle;
	
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
	
	public MyItemsPage deleteItem() {
		deleteItemLink.click();
		driver.switchTo().alert().accept();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
	
	// TODO fix to adhere with POM standards
	public ViewItemPage discardItem() {
		discardItemLink.click();
		
		DiscardItemPage discardItemPage = PageFactory.initElements(driver, DiscardItemPage.class);
		return discardItemPage.discardItem();
	}
	
}
