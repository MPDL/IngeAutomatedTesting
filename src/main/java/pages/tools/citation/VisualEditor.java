package main.java.pages.tools.citation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VisualEditor {

	private WebDriver driver;
	
	@FindBy(id = "styleMenu")
	private WebElement styleMenu;
	
	@FindBy(css = "#elementProperties input")
	private WebElement titleBox;
	
	public VisualEditor(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public void createNewStyle(String title) {
		styleMenu.click();
		styleMenu.findElement(By.id("menuNewStyle")).click();
		PageFactory.initElements(driver, VisualEditor.class);
		
		titleBox.sendKeys(title);
		styleMenu.click();
		styleMenu.findElement(By.cssSelector("ul>li:nth-of-type(3)")).click();
	}
	
	public void editStyle(String newTitle) {
		titleBox.clear();
		titleBox.sendKeys(newTitle);
		
		saveStyle();
	}
	
	private void saveStyle() {
		styleMenu.click();
		styleMenu.findElement(By.cssSelector("ul>li:nth-of-type(3)")).click();
		driver.findElement(By.id("downloadify_1474893774335")).click();
		driver.findElement(By.className("ui-icon-closethick")).click();
	}
}
