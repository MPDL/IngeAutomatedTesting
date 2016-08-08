package pages.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.BasePage;

public class BrowsePage extends BasePage {

	public BrowsePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver,  this);
	}
}
