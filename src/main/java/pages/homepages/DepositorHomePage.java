package pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.submission.MyItemsPage;

public class DepositorHomePage extends HomePage {
	
	@FindBy(id = "Header:lnkDepWorkspace")
	private WebElement myItemsLink;
	
	public DepositorHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public MyItemsPage goToMyItemsPage(WebDriver driver) {
		myItemsLink.click();
		
		return PageFactory.initElements(driver, MyItemsPage.class);
	}
}
