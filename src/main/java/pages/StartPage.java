package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.homepages.CombinedHomePage;
import pages.homepages.DepositorHomePage;
import pages.homepages.HomePage;
import pages.homepages.ModeratorHomePage;

public class StartPage extends BasePage {

	public StartPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
}
