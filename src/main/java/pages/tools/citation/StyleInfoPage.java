package main.java.pages.tools.citation;

import org.openqa.selenium.By;
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
	
	public boolean canSaveStyle() {
		installStyleButton.click();
		WebElement saveButton = driver.findElement(By.xpath("//object[contains(@id, 'downloadify')]"));
		
		return saveButton.isDisplayed() && saveButton.isEnabled();
	}
	
	public VisualEditor editStyle() {
		editStyleButton.click();
		
		return PageFactory.initElements(driver, VisualEditor.class);
	}
}
