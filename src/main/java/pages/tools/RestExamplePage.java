package main.java.pages.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class RestExamplePage extends BasePage {

	@FindBy(className = "activeButton")
	private WebElement buttonArea;
	
	public RestExamplePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean exportableItemAvailable() {
		boolean itemIsAvailable;
		itemIsAvailable = buttonArea.isDisplayed() && buttonArea.isEnabled();
		
		return itemIsAvailable;
	}
}
