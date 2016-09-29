package main.java.pages.tools.rest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.java.pages.BasePage;

public class RestExamplePage extends BasePage {

	@FindBy(className = "activeButton")
	private WebElement buttonArea;
	
	@FindBy(id = "result")
	private WebElement searchURI;
	
	@FindBy(id = "feeds")
	private WebElement searchFeeds;
	
	public RestExamplePage(WebDriver driver) {
		super(driver);
		
		PageFactory.initElements(driver, this);
	}
	
	public void downloadExport() {
		buttonArea.click();
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean newFieldsAppear() {
		return searchURI.isDisplayed() && searchFeeds.isDisplayed();
	}
}
