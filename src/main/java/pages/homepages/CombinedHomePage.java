package pages.homepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CombinedHomePage extends HomePage {

	public CombinedHomePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
}
