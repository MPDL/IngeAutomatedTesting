package main.java.pages.submission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class BatchImportPage extends BasePage {

	public BatchImportPage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
}
