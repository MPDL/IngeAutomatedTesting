package main.java.pages.tools.rest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RestDescriptionPage {

	private WebDriver driver;
	
	private static RestDescriptionPage instance;
	
	@FindBy(id = "mainMenuSkipLinkAnchor")
	private WebElement mainMenu;
	
	public RestDescriptionPage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public static RestDescriptionPage getInstance(WebDriver driver) {
		if (instance == null)
			instance = new RestDescriptionPage(driver);
		return instance;
	}
	
	public RestExamplePage goToRestExample() {
		mainMenu.findElement(By.tagName("a")).click();
		
		return PageFactory.initElements(driver, RestExamplePage.class);
	}
}
