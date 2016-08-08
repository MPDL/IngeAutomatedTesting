package pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ModeratorHomePage extends HomePage {

	public ModeratorHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
}
