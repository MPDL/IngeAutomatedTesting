package main.java.pages.tools.citation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StyleInfoPage {

	private WebDriver driver;
	
	@FindBy(className = "installStyle")
	private WebElement installStyleButton;
	
	@FindBy(className = "editStyle")
	private WebElement editStyleButton;
	
	public StyleInfoPage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public void installStyle() {
		installStyleButton.click();
	}
	
	public VisualEditor editStyle() {
		editStyleButton.click();
		
		return PageFactory.initElements(driver, VisualEditor.class);
	}
}
