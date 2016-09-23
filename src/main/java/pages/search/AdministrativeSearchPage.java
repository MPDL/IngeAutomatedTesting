package main.java.pages.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AdministrativeSearchPage extends AdvancedSearchPage {

	public AdministrativeSearchPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
}
