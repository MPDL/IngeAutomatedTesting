package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	
	public String getNameOfMostRecentItem() {
		WebElement recentItems = driver.findElement(By.className("gfc-results"));
		WebElement mostRecentItem = recentItems.findElement(By.className("gfc-result"));
		String mostRecentItemTitle = mostRecentItem.findElement(By.className("gf-title")).getText();
		
		return mostRecentItemTitle;
	}
	
}
